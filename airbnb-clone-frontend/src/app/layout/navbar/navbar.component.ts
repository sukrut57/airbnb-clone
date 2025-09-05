import {Component, inject, OnInit} from '@angular/core';
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

  isAuthenticated = this.keycloakService.isAuthenticated();

  userService = inject(UserService);
  toastService = inject(ToastService);

  ngOnInit() {
    if(!this.isAuthenticated){
      this.toastService.sendMessage({severity: 'info', summary: 'Welcome to Airbnb'});
    }
    else{
      this.displayLoginMessage()
    }
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
}
