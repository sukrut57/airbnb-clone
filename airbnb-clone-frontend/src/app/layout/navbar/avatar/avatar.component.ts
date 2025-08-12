import {Component, effect, input} from '@angular/core';
import {NgClass} from '@angular/common';
import {FaIconComponent} from '@fortawesome/angular-fontawesome';

@Component({
  selector: 'app-avatar',
  standalone: true,
  imports: [
    NgClass,
    FaIconComponent
  ],
  templateUrl: './avatar.component.html',
  styleUrl: './avatar.component.scss'
})
export class AvatarComponent {
  imageUrl = input<string>();
  avatarSize = input<"avatar-sm" | "avatar-xl">('avatar-sm');

}
