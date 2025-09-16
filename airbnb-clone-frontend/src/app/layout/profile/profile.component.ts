import {Component, inject, OnInit} from '@angular/core';
import {AvatarComponent} from '../navbar/avatar/avatar.component';
import {OverlayPanelModule} from 'primeng/overlaypanel';
import {RouterLink, RouterLinkActive} from '@angular/router';
import {ToolbarModule} from 'primeng/toolbar';
import {NavbarComponent} from '../navbar/navbar.component';
import {User} from '../../core/user/user.model';
import {UserService} from '../../core/user/user.service';
import {ToastService} from '../../core/toast/toast.service';
import {FooterComponent} from '../footer/footer.component';
import {CardModule} from 'primeng/card';
import {UpperCasePipe} from '@angular/common';
import {ProfileService} from './profile.service';

@Component({
  selector: 'app-profile',
  standalone: true,
  imports: [
    AvatarComponent,
    OverlayPanelModule,
    RouterLink,
    RouterLinkActive,
    ToolbarModule,
    NavbarComponent,
    FooterComponent,
    CardModule,
    UpperCasePipe
  ],
  templateUrl: './profile.component.html',
  styleUrl: './profile.component.scss'
})
export class ProfileComponent implements OnInit{

  userDetails : User | undefined;

  userService = inject(UserService);
  toastService = inject(ToastService);
  profileService = inject(ProfileService);


  ngOnInit(): void {
    this.getUserDetails();
    this.pushProfileStatus();
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

  pushProfileStatus(){
    this.profileService.enableProfileView(true);
  }

}
