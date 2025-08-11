import { Component } from '@angular/core';
import {FaIconComponent} from '@fortawesome/angular-fontawesome';
import {ToolbarModule} from 'primeng/toolbar';
import {Button, ButtonDirective} from 'primeng/button';

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [
    FaIconComponent,
    ToolbarModule,
    Button,
    ButtonDirective
  ],
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.scss'
})
export class NavbarComponent {

}
