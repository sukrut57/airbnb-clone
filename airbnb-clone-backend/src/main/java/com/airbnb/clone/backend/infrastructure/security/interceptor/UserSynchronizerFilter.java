package com.airbnb.clone.backend.infrastructure.security.interceptor;

import com.airbnb.clone.backend.shared.exception.UserSynchronizationException;
import com.airbnb.clone.backend.user.application.port.input.UserSynchronizerUseCase;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.List;

@Component
public class UserSynchronizerFilter extends OncePerRequestFilter {

    private final UserSynchronizerUseCase userSynchronizer;

    private static final List<String> excludedPaths = List.of(
          "/actuator");

    public UserSynchronizerFilter(UserSynchronizerUseCase userSynchronizer) {
        this.userSynchronizer = userSynchronizer;
    }

    private boolean isExcludedPath(String path) {
        return excludedPaths.stream().anyMatch(path::startsWith);
    }


    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {

        try{
            if (isExcludedPath(request.getRequestURI())) {
                filterChain.doFilter(request, response); // ⬅ continue filter chain for excluded paths
                return;
            }

            var authentication = SecurityContextHolder.getContext().getAuthentication();

            if (authentication == null || !authentication.isAuthenticated() ||
                    authentication instanceof AnonymousAuthenticationToken) {
                filterChain.doFilter(request, response); // ⬅ continue even for anonymous to avoid blocking
                return;
            }

            JwtAuthenticationToken token = (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
            userSynchronizer.synchronizeWithIdp(token.getToken());

            filterChain.doFilter(request, response); // ⬅ continue filter chain
        }
        catch (UserSynchronizationException ex){
            throw new ServletException(ex);

        }

    }




}
