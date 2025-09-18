import { Injectable } from '@angular/core';
import {Category} from './category.model';
import {BehaviorSubject} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CategoryService {

  constructor() { }

  private categories: Category[] = [
    {
      icon: "",
      technicalName: "ALL",
      displayName: "All",
      activated: true
    },
    {
      icon: "assets/category-icons/amazing_views.jpg",
      technicalName: "AMAZING_POOLS",
      displayName: "Amazing pools",
      activated: false
    },
    {
      icon: "assets/category-icons/farms.jpg",
      technicalName: "FARMS",
      displayName: "Farms",
      activated: false
    },
    {
      icon: "assets/category-icons/tiny_homes.jpg",
      technicalName: "TINY_HOMES",
      displayName: "Tiny homes",
      activated: false
    },
    {
      icon: "assets/category-icons/golfing.jpg",
      technicalName: "GOLFING",
      displayName: "Golfing",
      activated: false
    },
    {
      icon: "assets/category-icons/amazing_views.jpg",
      technicalName: "AMAZING_VIEWS",
      displayName: "Amazing views",
      activated: false
    },
    {
      icon: "assets/category-icons/tree_houses.jpg",
      technicalName: "TREE_HOUSES",
      displayName: "Tree houses",
      activated: false
    },
    {
      icon: "assets/category-icons/beach.jpg",
      technicalName: "BEACH",
      displayName: "Beach",
      activated: false
    },
    {
      icon: "assets/category-icons/cabins.jpg",
      technicalName: "CABINS",
      displayName: "Cabins",
      activated: false
    },
    {
      icon: "assets/category-icons/lakefront.jpg",
      technicalName: "LAKEFRONT",
      displayName: "Lakefront",
      activated: false
    },
    {
      icon: "assets/category-icons/luxe.jpg",
      technicalName: "LUXE",
      displayName: "Luxe",
      activated: false
    },
    {
      icon: "assets/category-icons/rooms.jpg",
      technicalName: "ROOMS",
      displayName: "Rooms",
      activated: false
    },
    {
      icon: "assets/category-icons/countryside.jpg",
      technicalName: "COUNTRYSIDE",
      displayName: "Countryside",
      activated: false
    },
    {
      icon: "assets/category-icons/beachfront.jpg",
      technicalName: "BEACHFRONT",
      displayName: "Beachfront",
      activated: false
    },
    {
      icon: "assets/category-icons/mansions.jpg",
      technicalName: "MANSIONS",
      displayName: "Mansions",
      activated: false
    },
    {
      icon: "assets/category-icons/bed_and_breakfast.jpg",
      technicalName: "BED_AND_BREAKFAST",
      displayName: "Bed and breakfast",
      activated: false
    },
    {
      icon: "assets/category-icons/national_parks.jpg",
      technicalName: "NATIONAL_PARKS",
      displayName: "National parks",
      activated: false
    },
    {
      icon: "assets/category-icons/design.jpg",
      technicalName: "DESIGN",
      displayName: "Design",
      activated: false
    },
    {
      icon: "assets/category-icons/trending.jpg",
      technicalName: "TRENDING",
      displayName: "Trending",
      activated: false
    },
    {
      icon:"assets/category-icons/off_the_grid.jpg",
      technicalName: "OFF_THE_GRID",
      displayName: "Off the grid",
      activated: false
    },
    {
      icon: "assets/category-icons/camping.jpg",
      technicalName: "CAMPING",
      displayName: "Camping",
      activated: false
    },
    {
      icon: "assets/category-icons/chefs_kitchen.jpg",
      technicalName: "CHEFS_KITCHEN",
      displayName: "Chef's kitchen",
      activated: false
    },
    {
      icon: "assets/category-icons/new.jpg",
      technicalName: "NEW",
      displayName: "New",
      activated: false
    },
    {
      icon: "assets/category-icons/play.jpg",
      technicalName: "PLAY",
      displayName: "Play",
      activated: false
    },
    {
      icon: "assets/category-icons/grand_piano.jpg",
      technicalName: "GRAND_PIANO",
      displayName: "Grand piano",
      activated: false
    },
    {
      icon: "assets/category-icons/surfing.jpg",
      technicalName: "SURFING",
      displayName: "Surfing",
      activated: false
    },
    {
      icon: "assets/category-icons/vineyard.jpg",
      technicalName: "VINEYARDS",
      displayName: "Vineyards",
      activated: false
    },
    {
      icon: "assets/category-icons/lake.jpg",
      technicalName: "LAKE",
      displayName: "Lake",
      activated: false
    }
  ];

  private changeCategory$ = new BehaviorSubject<Category>(this.getDefaultCategory());
  changeCategoryObs = this.changeCategory$.asObservable();

  changeCategory(category: Category) {
    this.changeCategory$.next(category);
  }

  getDefaultCategory(): Category {
    return this.categories[0];
  }

  getCategories(): Category[] {
    return this.categories;
  }

  getCategoryByTechnicalName(technicalName: string): Category | undefined {
    return this.categories.find(category => category.technicalName === technicalName);
  }
}
