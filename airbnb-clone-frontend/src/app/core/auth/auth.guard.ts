import {CanActivateFn, Router} from '@angular/router';
import {inject} from '@angular/core';
import {KeycloakService} from './keycloak.service';

export const authGuard: CanActivateFn = async (route, state) => {
  const router = inject(Router);
  const keycloakService = inject(KeycloakService);

  if(keycloakService.isTokenValid()){
    return true;
  }
  else{
    return router.createUrlTree(['/']);
  }
};
