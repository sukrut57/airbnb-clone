import {Component, inject, OnInit} from '@angular/core';
import { RouterOutlet } from '@angular/router';
import {NavbarComponent} from './layout/navbar/navbar.component';
import {FaIconLibrary} from '@fortawesome/angular-fontawesome';
import {fontAwesomeIcons} from './shared/font-awesome-icons';
import {FooterComponent} from './layout/footer/footer.component';
import {MessageService} from 'primeng/api';
import {ToastService} from './core/toast/toast.service';
import {ToastModule} from 'primeng/toast';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, NavbarComponent, FooterComponent, ToastModule],
  providers:[MessageService],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent implements OnInit{

  faIconLibrary = inject(FaIconLibrary);

  messageService = inject(MessageService);
  toastService = inject(ToastService);

  ngOnInit(): void {
    this.initFontAwesome();
    this.listenToToastService();
  }

  private initFontAwesome() {
    // This method can be used to initialize FontAwesome icons if needed
    this.faIconLibrary.addIcons(...fontAwesomeIcons);
  }

  private listenToToastService(){
    this.toastService.sendMessageObs.subscribe({
      next: newMessage => {
        if(newMessage && newMessage.summary !== this.toastService.INIT_STATE){
          this.messageService.add(newMessage);
        }
      }
    })

  }
}
