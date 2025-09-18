import {Component, inject, OnInit} from '@angular/core';
import {CarouselModule} from 'primeng/carousel';
import {Category} from './category.model';
import {CategoryService} from './category.service';
import {MatSlideToggle} from '@angular/material/slide-toggle';
import {FaIconComponent} from '@fortawesome/angular-fontawesome';

@Component({
  selector: 'app-category',
  standalone: true,
  imports: [
    CarouselModule,
    MatSlideToggle,
    FaIconComponent
  ],
  templateUrl: './category.component.html',
  styleUrl: './category.component.scss'
})
export class CategoryComponent implements OnInit{
  categories : Category[] = [];

  categoryService = inject(CategoryService);

  currentCategorySelected = this.categoryService.getDefaultCategory();

  responsiveOptions: any[] | undefined;


  ngOnInit(): void {
    this.fetchCategories();
    this.fetchResponsiveOptions();
  }

  private fetchResponsiveOptions() {
    this.responsiveOptions = [
      {
        breakpoint: '1270px', // tablets and small laptops
        numVisible: 7,
        numScroll: 1
      },
      {
        breakpoint: '990px', // landscape tablets
        numVisible: 5,
        numScroll: 1
      },
      {
        breakpoint: '600px', // small tablets / large phones
        numVisible: 3,
        numScroll: 1
      },
      {
        breakpoint: '400px', // small phones
        numVisible: 1,
        numScroll: 1
      }
    ];
  }
  private fetchCategories() {
    this.categories = this.categoryService.getCategories();
  }

  private activateCategory(category: Category) {
    this.currentCategorySelected.activated = false;
    this.currentCategorySelected = category;
    this.currentCategorySelected.activated = true;
  }

  onChangeCategory(category: Category) {
    this.activateCategory(category);
  }

  enableFilter() {

  }
}
