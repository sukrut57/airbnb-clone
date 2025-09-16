import {Component, inject, OnInit} from '@angular/core';
import {FooterComponent} from "../footer/footer.component";
import {NavbarComponent} from "../navbar/navbar.component";
import {RouterOutlet} from "@angular/router";
import {ProfileService} from '../profile/profile.service';

@Component({
  selector: 'app-home',
  standalone: true,
    imports: [
        FooterComponent,
        NavbarComponent,
        RouterOutlet
    ],
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss'
})
export class HomeComponent implements OnInit{
  profileStatus: boolean | undefined;
  profileService = inject(ProfileService);


  ngOnInit(): void {
    this.listenToProfileEvent();
  }
  listenToProfileEvent(){
    this.profileService.profileViewObs.subscribe({
      next: (profileView) => {
        console.log('Profile view:', profileView);
        this.profileStatus = profileView
      },
      error: (err) => {
        console.error('Error fetching user details:', err);
      }
    })
  }
}
