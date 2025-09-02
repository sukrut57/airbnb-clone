import { HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { KeycloakService } from '../auth/keycloak.service';

export const keycloakHttpInterceptor: HttpInterceptorFn = (req, next) => {
  const keycloakService = inject(KeycloakService);
  const token = keycloakService.getToken();

  if (token) {
    const authReq = req.clone({
      setHeaders: {
        Authorization: `Bearer ${token}`
      }
    });
    return next(authReq);
  }

  return next(req);
};
