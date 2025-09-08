import { Routes } from '@angular/router';
import {HomeComponent} from './layout/home/home.component';
import {ProfileComponent} from './layout/navbar/profile/profile.component';

export const routes: Routes = [

  {
    path: 'home',
    component: HomeComponent,

  },
  {
    path: 'profile',
    component: ProfileComponent
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
