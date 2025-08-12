import { Injectable } from '@angular/core';
import {Category} from './category.model';

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
      activated: false
    },
    {
      icon: "/assets/categories/amazing-views.jpg",
      technicalName: "AMAZING_POOLS",
      displayName: "Amazing pools",
      activated: false
    },
    {
      icon: "/assets/categories/farms.jpg",
      technicalName: "FARMS",
      displayName: "Farms",
      activated: false
    },
    {
      icon: "/assets/categories/tiny-homes.jpg",
      technicalName: "TINY_HOMES",
      displayName: "Tiny homes",
      activated: false
    },
    {
      icon: "/assets/categories/golfing.jpg",
      technicalName: "GOLFING",
      displayName: "Golfing",
      activated: false
    },
    {
      icon: "/assets/categories/amazing-views.jpg",
      technicalName: "AMAZING_VIEWS",
      displayName: "Amazing views",
      activated: false
    },
    {
      icon: "/assets/categories/tree-houses.jpg",
      technicalName: "TREE_HOUSES",
      displayName: "Tree houses",
      activated: false
    },
    {
      icon: "/assets/categories/beach.jpg",
      technicalName: "BEACH",
      displayName: "Beach",
      activated: false
    },
    {
      icon: "/assets/categories/cabins.jpg",
      technicalName: "CABINS",
      displayName: "Cabins",
      activated: false
    },
    {
      icon: "/assets/categories/lakefront.jpg",
      technicalName: "LAKEFRONT",
      displayName: "Lakefront",
      activated: false
    },
    {
      icon: "/assets/categories/luxe.jpg",
      technicalName: "LUXE",
      displayName: "Luxe",
      activated: false
    },
    {
      icon: "/assets/categories/rooms.jpg",
      technicalName: "ROOMS",
      displayName: "Rooms",
      activated: false
    },
    {
      icon: "/assets/categories/countryside.jpg",
      technicalName: "COUNTRYSIDE",
      displayName: "Countryside",
      activated: false
    },
    {
      icon: "/assets/categories/beachfront.jpg",
      technicalName: "BEACHFRONT",
      displayName: "Beachfront",
      activated: false
    },
    {
      icon: "/assets/categories/mansions.jpg",
      technicalName: "MANSIONS",
      displayName: "Mansions",
      activated: false
    },
    {
      icon: "/assets/categories/bed-and-breakfast.jpg",
      technicalName: "BED_AND_BREAKFASTS",
      displayName: "Bed and breakfasts",
      activated: false
    },
    {
      icon: "/assets/categories/national-parks.jpg",
      technicalName: "NATIONAL_PARKS",
      displayName: "National parks",
      activated: false
    },
    {
      icon: "/assets/categories/design.jpg",
      technicalName: "DESIGN",
      displayName: "Design",
      activated: false
    },
    {
      icon: "/assets/categories/trending.jpg",
      technicalName: "TRENDING",
      displayName: "Trending",
      activated: false
    },
    {
      icon:"/assets/categories/off-the-grid.jpg",
      technicalName: "OFF_THE_GRID",
      displayName: "Off the grid",
      activated: false
    },
    {
      icon: "/assets/categories/camping.jpg",
      technicalName: "CAMPING",
      displayName: "Camping",
      activated: false
    },
    {
      icon: "/assets/categories/chefs-kitchen.jpg",
      technicalName: "CHEFS_KITCHEN",
      displayName: "Chef's kitchen",
      activated: false
    },
    {
      icon: "/assets/categories/new.jpg",
      technicalName: "NEW",
      displayName: "New",
      activated: false
    },
    {
      icon: "/assets/categories/play.jpg",
      technicalName: "PLAY",
      displayName: "Play",
      activated: false
    },
    {
      icon: "/assets/categories/grand-piano.jpg",
      technicalName: "GRAND_PIANO",
      displayName: "Grand piano",
      activated: false
    },
    {
      icon: "/assets/categories/surfing.jpg",
      technicalName: "SURFING",
      displayName: "Surfing",
      activated: false
    },
    {
      icon: "/assets/categories/vineyards.jpg",
      technicalName: "VINEYARDS",
      displayName: "Vineyards",
      activated: false
    },
    {
      icon: "/assets/categories/lake.jpg",
      technicalName: "LAKE",
      displayName: "Lake",
      activated: false
    }


  ];

}
