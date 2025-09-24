import {Injectable, signal, WritableSignal} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {User} from './user.model';
import {UriConstants} from '../../constants/uri-constants';
import {BehaviorSubject, catchError, Observable, shareReplay, throwError} from 'rxjs';


@Injectable({
  providedIn: 'root'
})
export class UserService {

  private userDetails$? : Observable<User> // cache

  constructor(private httpClient: HttpClient) { }


  getUserDetails(){
    if(!this.userDetails$){
      this.userDetails$ = this.httpClient.get<User>(UriConstants.getUserUri()).pipe(
        shareReplay(1), // cache the response, share across subscribers
        catchError(err => {
          console.error('Error fetching user details:', err);
          // clear cache so next call retries
          this.userDetails$ = undefined;
          return throwError(() => new Error('Failed to fetch user details. Please try again later.'));
        })
      );
    }
    return this.userDetails$;
  }
  /** Force refresh */
  refreshUserDetails(): Observable<User> {
    this.userDetails$ = this.httpClient.get<User>(UriConstants.getUserUri()).pipe(
      shareReplay(1),
      catchError(err => {
        console.error('Error refreshing user details:', err);
        this.userDetails$ = undefined;
        return throwError(() => new Error('Failed to refresh user details.'));
      })
    );
    return this.userDetails$;
  }

  /** Reset after logout */
  clearUserDetails() {
    this.userDetails$ = undefined;
  }
}
