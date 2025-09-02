import { Injectable } from '@angular/core';
import Keycloak from 'keycloak-js';

@Injectable({
  providedIn: 'root'
})
export class KeycloakService {

  private _keycloak: Keycloak | undefined;

  constructor() { }

  get keycloak(){
    if(!this._keycloak){
      this._keycloak = new Keycloak({
        url: 'http://localhost:9090',
        realm: 'airbnb_clone',
        clientId: 'airbnb-client-app'
      });
    }
    return this._keycloak;
  }

  // Initialize Keycloak (called at app startup)
  init(): Promise<boolean> {
    return this.keycloak.init({
      onLoad: 'check-sso',
      pkceMethod: 'S256',
      flow: 'standard',
      silentCheckSsoRedirectUri: window.location.origin + '/assets/silent-check-sso.html',
      checkLoginIframe: false   // auto refresh login state every few seconds
    }).then(authenticated => {
      console.log('Keycloak init success, authenticated:', authenticated);
      return authenticated;
    }).catch(err => {
      console.error('Keycloak init error:', err);
      return false;
    });
  }

  login():void{
    this.keycloak.login({ redirectUri: window.location.origin });
  }

  logout():void{
    this.keycloak.logout({redirectUri: window.location.origin});
  }

  isAuthenticated(): boolean {
    return this.keycloak.authenticated || false;
  }

  getToken(): string | undefined {
    return this.keycloak.token;
  }

  isTokenValid(): boolean {
    return !this.keycloak.isTokenExpired() ;
  }


}
