import {Component, inject, OnInit} from '@angular/core';
import {FaIconComponent} from '@fortawesome/angular-fontawesome';
import {ToolbarModule} from 'primeng/toolbar';
import {ButtonModule} from 'primeng/button';
import {MenuModule} from 'primeng/menu';
import {OverlayPanelModule} from 'primeng/overlaypanel';
import {AvatarComponent} from './avatar/avatar.component';
import {CategoryComponent} from './category/category.component';
import {KeycloakService} from '../../core/auth/keycloak.service';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';


export interface User {
  email: string
  firstName: string
  lastName: string
  profilePictureUrl: any
  accountEnabled: boolean
  publicId: string
  authorities: Authority[]
}

export interface Authority {
  name: string
}

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

  isAuthenticated = this.keycloakService.isAuthenticated();

  ngOnInit() {
  }


  login() {
    this.keycloakService.login();
    this.isAuthenticated = this.keycloakService.isAuthenticated();
  }

  logout() {
    this.keycloakService.logout();
    this.isAuthenticated = !this.keycloakService.isAuthenticated();  }

  routeToMyGitHub() {
    window.open('https://github.com/sukrut57/airbnb-clone');
  }

  getUser(){
    this.getUserDetails().subscribe({
      next: (user) => {
        console.log(user);
      },
      error: (err) => {
        console.error('Error fetching user details:', err);
      }
    });
  }

  httpClient = inject(HttpClient);

  getUserDetails():Observable<User>{
    return this.httpClient.get<User>('http://localhost:8080/api/v1/user');
}






}
