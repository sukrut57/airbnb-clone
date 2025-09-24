import {HostListener, Injectable} from '@angular/core';
import {BehaviorSubject} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ProfileService {


  private profileView$ = new BehaviorSubject<boolean>(false);
  profileViewObs = this.profileView$.asObservable();

  enableProfileView($event: boolean){
    this.profileView$.next($event);
  }




}
