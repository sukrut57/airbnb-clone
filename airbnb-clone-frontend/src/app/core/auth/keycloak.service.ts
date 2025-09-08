import {Injectable} from '@angular/core';
import Keycloak from 'keycloak-js';
import {BehaviorSubject} from 'rxjs';
import {AuthenticationStates} from './authentication.states';

@Injectable({
  providedIn: 'root'
})
export class KeycloakService {

  private _keycloak: Keycloak | undefined;

  private isAuthenticatedSub$ = new BehaviorSubject<AuthenticationStates>(AuthenticationStates.FirstVisit);
  isAuthenticatedObs = this.isAuthenticatedSub$.asObservable();

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
      if(authenticated){
        this.isAuthenticatedSub$.next(AuthenticationStates.LoggedIn);
        sessionStorage.setItem('lastAction',AuthenticationStates.LoggedIn);
      }
      else{
        const lastAction = sessionStorage.getItem('lastAction');

        if(!lastAction){
          //new user session
          this.isAuthenticatedSub$.next(AuthenticationStates.FirstVisit);
          sessionStorage.setItem('lastAction', AuthenticationStates.FirstVisit);

        }
        else if(lastAction === AuthenticationStates.LoggedIn){
          //user was logged in but now logged out
          this.isAuthenticatedSub$.next(AuthenticationStates.LoggedOut);
          sessionStorage.removeItem('lastAction'); // 🔑 don’t persist logout
        }
        else{
          // already logged out or first visit handled → do nothing
          this.isAuthenticatedSub$.next(lastAction as AuthenticationStates);
        }
      }
      return authenticated;
    }).catch(err => {
      console.error('Keycloak init error:', err);
      return false;
    });
  }

  login():void{
    this.keycloak.login({ redirectUri: window.location.origin }).catch(err => {
      console.error('Keycloak login error:', err);
      //revert to new user if login failed
      this.isAuthenticatedSub$.next(AuthenticationStates.FirstVisit);
      sessionStorage.setItem('lastAction',AuthenticationStates.FirstVisit);
    });
  }

  logout(): void {
    this.keycloak.logout({ redirectUri: window.location.origin }).catch(err => {
      console.error("Keycloak logout error: ", err);
      //revert to login if logout failed
      this.isAuthenticatedSub$.next(AuthenticationStates.LoggedIn);
      sessionStorage.setItem('lastAction',AuthenticationStates.LoggedIn);
    });
  }

  getToken(): string | undefined {
    return this.keycloak.token;
  }

  isTokenValid(): boolean {
    return !this.keycloak.isTokenExpired() ;
  }


}
