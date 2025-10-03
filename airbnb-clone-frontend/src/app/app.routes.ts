import { Routes } from '@angular/router';
import {HomeComponent} from './layout/home/home.component';
import {ProfileComponent} from './layout/profile/profile.component';
import {AccountSettingsComponent} from './layout/profile/account-settings/account-settings.component';
import {authGuard} from './core/auth/auth.guard';

export const routes: Routes = [

  {
    path: 'home',
    component: HomeComponent,
    title: 'Airbnb - Home'

  },
  {
    path: 'profile',
    component: ProfileComponent,
    title: 'Airbnb - Profile',
    canActivate:[
      authGuard
    ]
  },
  {
    path:'account-settings',
    component:AccountSettingsComponent,
    title: 'Airbnb - Account Settings'
  },
  {
    path: '',
    redirectTo: 'home',
    pathMatch: 'full'
  },
  {
    path:'**',
    redirectTo:'home',
    pathMatch:'full'
  }
];
