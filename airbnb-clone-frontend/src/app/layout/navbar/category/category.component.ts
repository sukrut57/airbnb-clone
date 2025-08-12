import {Component, inject, OnInit} from '@angular/core';
import {CarouselModule} from 'primeng/carousel';
import {Category} from './category.model';
import {CategoryService} from './category.service';

@Component({
  selector: 'app-category',
  standalone: true,
  imports: [
    CarouselModule
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
        breakpoint: '1199px',
        numVisible: 1,
        numScroll: 1
      },
      {
        breakpoint: '991px',
        numVisible: 2,
        numScroll: 1
      },
      {
        breakpoint: '767px',
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
}
