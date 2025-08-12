import {Component, effect, OnInit} from '@angular/core';
import {FaIconComponent} from '@fortawesome/angular-fontawesome';
import {ToolbarModule} from 'primeng/toolbar';
import {Button, ButtonDirective, ButtonModule} from 'primeng/button';
import {MenuModule} from 'primeng/menu';
import {MenuItem, MenuItemCommandEvent} from 'primeng/api';
import {OverlayPanelModule} from 'primeng/overlaypanel';

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [
    FaIconComponent,
    ToolbarModule,
    ButtonModule,
    MenuModule,
    OverlayPanelModule
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
    this.isAuthenticated = !this.isAuthenticated;
  }

  logout() {
    this.isAuthenticated = !this.isAuthenticated;
  }

  routeToMyGitHub() {
    window.open('https://github.com/sukrut57/airbnb-clone');
  }
}
