import {Component, OnInit} from '@angular/core';
import {FaIconComponent} from '@fortawesome/angular-fontawesome';
import {ToolbarModule} from 'primeng/toolbar';
import {Button, ButtonDirective, ButtonModule} from 'primeng/button';
import {MenuModule} from 'primeng/menu';
import {MenuItem} from 'primeng/api';

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [
    FaIconComponent,
    ToolbarModule,
    ButtonModule,
    MenuModule
  ],
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.scss'
})
export class NavbarComponent implements OnInit{
  items!: MenuItem[];

  ngOnInit() {
    this.items = [
      {
        label: 'File',
        items: [
          { label: 'New', icon: 'pi pi-plus', command: () => alert('New clicked') },
          { label: 'Open', icon: 'pi pi-folder-open', command: () => alert('Open clicked') }
        ]
      },
      {
        label: 'Edit',
        items: [
          { label: 'Undo', icon: 'pi pi-undo', command: () => alert('Undo clicked') },
          { label: 'Redo', icon: 'pi pi-redo', command: () => alert('Redo clicked') }
        ]
      }
    ];
  }
}
