import {Component, effect, OnInit} from '@angular/core';
import {FaIconComponent} from '@fortawesome/angular-fontawesome';
import {ToolbarModule} from 'primeng/toolbar';
import {ButtonModule} from 'primeng/button';
import {MenuModule} from 'primeng/menu';
import {OverlayPanelModule} from 'primeng/overlaypanel';
import {AvatarComponent} from './avatar/avatar.component';

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [
    FaIconComponent,
    ToolbarModule,
    ButtonModule,
    MenuModule,
    OverlayPanelModule,
    AvatarComponent
  ],
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.scss'
})
export class NavbarComponent implements OnInit{

  isAuthenticated = false;

  constructor() {
    effect(() => {
    });
  }

  ngOnInit() {
  }

  login() {
    this.isAuthenticated = true;
  }

  logout() {
    this.isAuthenticated = false;
  }

  routeToMyGitHub() {
    window.open('https://github.com/sukrut57/airbnb-clone');
  }

}
