import {Component, inject, OnInit} from '@angular/core';
import {AvatarComponent} from '../navbar/avatar/avatar.component';
import {ButtonDirective} from 'primeng/button';
import {FaIconComponent} from '@fortawesome/angular-fontawesome';
import {OverlayPanelModule} from 'primeng/overlaypanel';
import {PrimeTemplate} from 'primeng/api';
import {RouterLink, RouterLinkActive, RouterOutlet} from '@angular/router';
import {ToolbarModule} from 'primeng/toolbar';
import {NavbarComponent} from '../navbar/navbar.component';
import {User} from '../../core/user/user.model';
import {UserService} from '../../core/user/user.service';
import {ToastService} from '../../core/toast/toast.service';
import {FooterComponent} from '../footer/footer.component';

@Component({
  selector: 'app-profile',
  standalone: true,
  imports: [
    AvatarComponent,
    ButtonDirective,
    FaIconComponent,
    OverlayPanelModule,
    PrimeTemplate,
    RouterLink,
    RouterLinkActive,
    ToolbarModule,
    NavbarComponent,
    FooterComponent,
    RouterOutlet
  ],
  templateUrl: './profile.component.html',
  styleUrl: './profile.component.scss'
})
export class ProfileComponent implements OnInit{

  userDetails : User | undefined;

  userService = inject(UserService);
  toastService = inject(ToastService);


  ngOnInit(): void {
    this.getUserDetails();
  }

  private getUserDetails(){
    this.userService.getUserDetails().subscribe({
      next: (user) => {
        this.userDetails = user;
      },
      error: (err) => {
        this.toastService.sendMessage({severity: 'error', summary: 'Unable to fetch user details. Please try again later.'});
        console.error('Error fetching user details:', err);
      }
    });
  }

  isActive(val: string) {
    
  }
}
