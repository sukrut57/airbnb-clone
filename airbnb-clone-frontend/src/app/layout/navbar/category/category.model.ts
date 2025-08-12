export type categoryName = "ALL" | "AMAZING_POOLS" | "FARMS" | "TINY_HOMES" | "GOLFING"
  | "AMAZING_VIEWS" | "TREE_HOUSES" | "BEACH" | "CABINS" | "LAKEFRONT" | "LUXE" | "ROOMS" | "COUNTRYSIDE" | "BEACHFRONT"
  | "MANSIONS" | "BED_AND_BREAKFAST" | "NATIONAL_PARKS" | "DESIGN" | "TRENDING" | "OFF_THE_GRID" | "CAMPING" | "CHEFS_KITCHEN"
  | "NEW" | "PLAY" | "GRAND_PIANO" | "SURFING" | "VINEYARDS" | "LAKE" ;


export interface Category {
  icon:string,
  technicalName: categoryName,
  displayName: string,
  activated: boolean
}

