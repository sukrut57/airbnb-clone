import {Injectable, signal, WritableSignal} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {User} from './user.model';
import {UriConstants} from '../../constants/uri-constants';
import {BehaviorSubject, catchError, throwError} from 'rxjs';


@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private httpClient: HttpClient) { }


  getUserDetails(){
    return this.httpClient.get<User>(UriConstants.getUserUri()).pipe(
      catchError(((error) => {
        console.error('Error fetching user details:', error);
        return throwError(() => new Error('Failed to fetch user details. Please try again later.'));
      }))
    )
  }

}
