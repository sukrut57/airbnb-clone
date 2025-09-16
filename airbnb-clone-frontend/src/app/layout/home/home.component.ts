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
  profileService = inject(ProfileService);


  ngOnInit(): void {
    this.pushProfileStatus();
  }
  pushProfileStatus(){
    this.profileService.enableProfileView(false);
  }
}
