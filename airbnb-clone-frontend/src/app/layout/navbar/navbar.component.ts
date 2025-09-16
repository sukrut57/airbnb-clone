import {Component, HostListener, inject, input, OnInit, signal} from '@angular/core';
import {FaIconComponent} from '@fortawesome/angular-fontawesome';
import {ToolbarModule} from 'primeng/toolbar';
import {ButtonModule} from 'primeng/button';
import {MenuModule} from 'primeng/menu';
import {OverlayPanelModule} from 'primeng/overlaypanel';
import {AvatarComponent} from './avatar/avatar.component';
import {CategoryComponent} from './category/category.component';
import {KeycloakService} from '../../core/auth/keycloak.service';
import {UserService} from '../../core/user/user.service';
import {ToastService} from '../../core/toast/toast.service';
import {AuthenticationStates} from '../../core/auth/authentication.states';
import {Router} from '@angular/router';
import {ProfileService} from '../profile/profile.service';
import {toSignal} from '@angular/core/rxjs-interop';


@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [
    FaIconComponent,
    ToolbarModule,
    ButtonModule,
    MenuModule,
    OverlayPanelModule,
    AvatarComponent,
    CategoryComponent
  ],
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.scss'
})
export class NavbarComponent implements OnInit{


  keycloakService = inject(KeycloakService);

  username = '';

  // profileStatus: boolean | undefined;

  isAuthenticated: AuthenticationStates = AuthenticationStates.FirstVisit;


  userService = inject(UserService);
  toastService = inject(ToastService);
  profileService = inject(ProfileService);

  router = inject(Router);

  ngOnInit() {
   this.listenToAuthenticationStates();
   //this.listenToProfileView();
  }

  login() {
    this.keycloakService.login();

  }

  logout() {
    this.keycloakService.logout();
}
  routeToMyGitHub() {
    window.open('https://github.com/sukrut57/airbnb-clone');
  }

  private listenToAuthenticationStates() {
    this.keycloakService.isAuthenticatedObs.subscribe({
      next: (authState) => {

        switch (authState) {
          case AuthenticationStates.LoggedIn:
            this.isAuthenticated = AuthenticationStates.LoggedIn;
            this.displayLoginMessage();
            break;

          case AuthenticationStates.LoggedOut:
              this.isAuthenticated = AuthenticationStates.LoggedOut;
              this.username = '';
              this.toastService.sendMessage({
                severity: 'info',
                summary: 'You are logged out successfully'
              });

            break;

          case AuthenticationStates.FirstVisit:
              this.isAuthenticated = AuthenticationStates.FirstVisit;
              this.username = '';
              this.toastService.sendMessage({
                severity: 'info',
                summary: 'Welcome to Airbnb Clone'
              });
            break;
        }
      },
      error: () => {
        this.toastService.sendMessage({
          severity: 'error',
          summary: 'Unable to Authenticate. Please try again later.'
        });
      }
    });
  }

  getUser(){
    this.userService.getUserDetails().subscribe({
      next: (user) => {
        this.username = this.capitalizeFirstLetter(user.firstName);
        this.toastService.sendMessage({severity: 'success', summary: 'Welcome back, ' + this.username});
      },
      error: (err) => {
        console.error('Error fetching user details:', err);
      }
    });
  }

  private displayLoginMessage() {
    this.getUser();
  }

  private capitalizeFirstLetter(string: string) {
    return string.charAt(0).toUpperCase() + string.slice(1);
  }

  routeToMyProfile() {
    this.router.navigate(['/profile']).then(r =>     this.profileService.enableProfileView(true));
  }

  screenWidth = window.innerWidth;

  get showLargeMenu() {
    return this.screenWidth >= 750;
  }

// Listen to window resize
  @HostListener('window:resize', ['$event'])
  onResize() {
    this.screenWidth = window.innerWidth;
  }

  //older style, .subscribe() in listenToProfileView() without unsubscribing. This risks memory leaks if Navbar is ever destroyed/recreated.
  // listenToProfileView(){
  //   this.profileService.profileViewObs.subscribe({
  //     next: (profileView) => {
  //       console.log('Profile view:', profileView);
  //       this.profileStatus = profileView;
  //     }
  //   })
  // }

  //newer style, reactive signal instead of manual subscribe
  profileStatus = toSignal(this.profileService.profileViewObs,{initialValue: false});

  routeToHome() {
    this.profileService.enableProfileView(false);
  }
}
