import { Injectable } from '@angular/core';
import {BehaviorSubject} from 'rxjs';
import {Message} from 'primeng/api';

@Injectable({
  providedIn: 'root'
})
export class ToastService {

  INIT_STATE = "INIT"

  constructor() { }

  private sendMessage$ : BehaviorSubject<Message> = new BehaviorSubject<Message>({summary: this.INIT_STATE, severity: '', detail: ''});

  sendMessageObs = this.sendMessage$.asObservable();

  sendMessage(message:Message){
    this.sendMessage$.next(message);
  }
}
