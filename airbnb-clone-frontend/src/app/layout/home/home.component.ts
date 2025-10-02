import {Component, inject, OnInit} from '@angular/core';
import {FooterComponent} from "../footer/footer.component";
import {NavbarComponent} from "../navbar/navbar.component";
import {CategoryComponent} from '../navbar/category/category.component';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [
    FooterComponent,
    NavbarComponent,
    CategoryComponent
  ],
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss'
})
export class HomeComponent implements OnInit{

  ngOnInit(): void {
  }

}
