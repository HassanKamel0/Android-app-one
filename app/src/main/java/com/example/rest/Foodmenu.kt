package com.example.rest

data class Foodmenu (var name:String,var image:Int)

val Burger=Foodmenu("Burger", R.drawable.burger)
val Pasta=Foodmenu("Pasta", R.drawable.pasta)
val Pizza=Foodmenu("Pizza", R.drawable.pizza)
val Soup=Foodmenu("Soup", R.drawable.soup)
val Chicken=Foodmenu("Chicken",R.drawable.chicken)
val Fries=Foodmenu("Fries",R.drawable.fries)
val Meat=Foodmenu("Meat",R.drawable.meat)
val Juice=Foodmenu("Juice",R.drawable.juice)
val Fish=Foodmenu("Fish",R.drawable.fish)
val Cola=Foodmenu("Cola", R.drawable.cola)

val foodlist= arrayListOf<Foodmenu>(Burger, Pasta, Pizza, Soup, Chicken, Fries, Meat, Juice, Fish,Cola)
val todayfood= arrayListOf<Foodmenu>(Chicken, Pizza, Meat)