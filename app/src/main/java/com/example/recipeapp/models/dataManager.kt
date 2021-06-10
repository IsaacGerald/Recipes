package com.example.recipeapp.models

import com.example.recipeapp.R
import com.example.recipeapp.network.Cuisines
import com.example.recipeapp.network.FoodApiTypes
import com.example.recipeapp.network.DishTypes
import org.imaginativeworld.whynotimagecarousel.CarouselItem

object DataManager {

    var foodTypes: MutableList<Categories> = ArrayList()
    var cuisineTypes: MutableList<Cuisine> = ArrayList()
    var dishTypes: MutableList<Meal> = ArrayList()
    val carouselList: MutableList<CarouselItem> = mutableListOf<CarouselItem>()
    val ingredientList: MutableList<String> = ArrayList()
    val ingredients: MutableList<Ingredients> = ArrayList()
    val sliderList = mutableListOf<Slider>()


    init {
        initializeCategories()
        initializeCuisines()
        initializeMeals()
        initializeIngredientLines()
        initializeIngredients()

    }

    private fun initializeIngredientLines() {
        ingredientList.add("cuisine")
        ingredientList.add("american")
        ingredientList.add("Africa")
        ingredientList.add("Asia")
        ingredientList.add("Australia")
    }

    private fun initializeIngredients() {
        ingredients.add(Ingredients("Pancakes", ingredientList))
        ingredients.add(Ingredients("Tea", ingredientList))
        ingredients.add(Ingredients("Drinks", ingredientList))
        ingredients.add(Ingredients("Lunch", ingredientList))
        ingredients.add(Ingredients("Breakfast", ingredientList))
        ingredients.add(Ingredients("Main course", ingredientList))
        ingredients.add(Ingredients("Dinner", ingredientList))
    }


    private fun initializeCategories() {
        foodTypes.add(Categories(FoodApiTypes.PANCAKE.value, 1))
        foodTypes.add(Categories(FoodApiTypes.DESSERT.value, 2))
        foodTypes.add(Categories(FoodApiTypes.MAIN_COURSE.value, 3))
        foodTypes.add(Categories(FoodApiTypes.BREAKFAST.value, 4))
        foodTypes.add(Categories(FoodApiTypes.SALAD.value, 5))
        foodTypes.add(Categories(FoodApiTypes.PREPS.value, 6))
        foodTypes.add(Categories(FoodApiTypes.ALCOHOL.value, 7))
        foodTypes.add(Categories(FoodApiTypes.BREAD.value, 8))
        foodTypes.add(Categories(FoodApiTypes.DRINK.value, 9))
        foodTypes.add(Categories(FoodApiTypes.SAUCE.value, 10))
        foodTypes.add(Categories(FoodApiTypes.SOUP.value, 11))
        foodTypes.add(Categories(FoodApiTypes.SNACK.value, 12))
        foodTypes.add(Categories(FoodApiTypes.CEREALS.value, 13))
        foodTypes.add(Categories(FoodApiTypes.OMELET.value, 14))
        foodTypes.add(Categories(FoodApiTypes.SIDE_DISH.value, 15))
    }

    private fun initializeCuisines() {
        cuisineTypes.add(Cuisine(Cuisines.AMERICAN.value, 1))
        cuisineTypes.add(Cuisine(Cuisines.ASIAN.value, 2))
        cuisineTypes.add(Cuisine(Cuisines.BRITISH.value, 3))
        cuisineTypes.add(Cuisine(Cuisines.CARIBBEAN.value, 4))
        cuisineTypes.add(Cuisine(Cuisines.MEXICAN.value, 5))
        cuisineTypes.add(Cuisine(Cuisines.CENTRAL_EUROPE.value, 6))
        cuisineTypes.add(Cuisine(Cuisines.CHINESE.value, 7))
        cuisineTypes.add(Cuisine(Cuisines.EASTERN_EUROPE.value, 8))
        cuisineTypes.add(Cuisine(Cuisines.INDIAN.value, 9))
        cuisineTypes.add(Cuisine(Cuisines.FRENCH.value, 10))
        cuisineTypes.add(Cuisine(Cuisines.ITALIAN.value, 11))
        cuisineTypes.add(Cuisine(Cuisines.JAPANESE.value, 12))
        cuisineTypes.add(Cuisine(Cuisines.KOSHER.value, 13))
        cuisineTypes.add(Cuisine(Cuisines.NORDIC.value, 14))
        cuisineTypes.add(Cuisine(Cuisines.MEDITERRANEAN.value, 15))
        cuisineTypes.add(Cuisine(Cuisines.SOUTH_AMERICAN.value, 16))
        cuisineTypes.add(Cuisine(Cuisines.SOUTH_EAST_ASIAN.value, 17))
        cuisineTypes.add(Cuisine(Cuisines.MIDDLE_EASTERN.value, 18))
    }

    private fun initializeMeals() {
        dishTypes.add(Meal(DishTypes.BREAKFAST.value, 1))
        dishTypes.add(Meal(DishTypes.LUNCH.value, 2))
        dishTypes.add(Meal(DishTypes.SNACK.value, 3))
        dishTypes.add(Meal(DishTypes.TEATIME.value, 4))
        dishTypes.add(Meal(DishTypes.DINNER.value, 5))
    }


//    fun getImageCategory(id: Int): Int {
//        return when (id) {
//            1 -> R.drawable.pancake
//            2 -> R.drawable.dessert3
//            3 -> R.drawable.main_course3
//            4 -> R.drawable.breakfast2
//            5 -> R.drawable.salad2
//            6 -> R.drawable.prep
//            7 -> R.drawable.alcohol2
//            8 -> R.drawable.bread2
//            9 -> R.drawable.drinks2
//            10 -> R.drawable.sauce2
//            11 -> R.drawable.soup2
//            12 -> R.drawable.snacks2
//            13 -> R.drawable.cereals2
//            14 -> R.drawable.omelette2
//            15 -> R.drawable.side_dish2
//            else -> return 0
//        }
//    }
//
//    fun getImageCuisine(id: Int): Int {
//        return when (id) {
//            1 -> R.drawable.american2
//            2 -> R.drawable.asia
//            3 -> R.drawable.british
//            4 -> R.drawable.carribean
//            5 -> R.drawable.mexican
//            6 -> R.drawable.central_europe
//            7 -> R.drawable.chinese
//            8 -> R.drawable.eastern_europe
//            9 -> R.drawable.indian
//            10 -> R.drawable.french
//            11 -> R.drawable.italian
//            12 -> R.drawable.japanese
//            13 -> R.drawable.kosher
//            14 -> R.drawable.nodic
//            15 -> R.drawable.mediterranean
//            16 -> R.drawable.south_american
//            17 -> R.drawable.south_east_asian
//            18 -> R.drawable.middle_eastern
//            else -> return 0
//        }
//
//    }
//
//    fun getImageMeals(id: Int): Int {
//        return when (id) {
//            1 -> R.drawable.breakfast2
//            2 -> R.drawable.lunch2
//            3 -> R.drawable.snack2
//            4 -> R.drawable.teatime2
//            5 -> R.drawable.dinner2
//            else -> return -1
//        }
//
//
//    }
//
//    private fun setUpCarousels() {
//
//        carouselList.add(
//            CarouselItem(
//                imageDrawable = R.drawable.pancake,
//                caption = "Pancake"
//            )
//        )
//        carouselList.add(
//            CarouselItem(
//                imageDrawable = R.drawable.dessert3,
//                caption = "Dessert"
//            )
//        )
//        carouselList.add(
//            CarouselItem(
//                imageDrawable = R.drawable.main_course2,
//                caption = "Main Course"
//            )
//        )
//        carouselList.add(
//            CarouselItem(
//                imageDrawable = R.drawable.breakfast2,
//                caption = "Breakfast"
//            )
//        )
//        carouselList.add(
//            CarouselItem(
//                imageDrawable = R.drawable.salad2,
//                caption = "Salad"
//            )
//        )
//        carouselList.add(
//            CarouselItem(
//                imageDrawable = R.drawable.prep,
//                caption = "Prep"
//            )
//        )
//        carouselList.add(
//            CarouselItem(
//                imageDrawable = R.drawable.alcohol2,
//                caption = "Alcohol-cockTail"
//            )
//        )
//        carouselList.add(
//            CarouselItem(
//                imageDrawable = R.drawable.bread2,
//                caption = "Bread"
//            )
//        )
//        carouselList.add(
//            CarouselItem(
//                imageDrawable = R.drawable.drinks2,
//                caption = "Drinks"
//            )
//        )
//        carouselList.add(
//            CarouselItem(
//                imageDrawable = R.drawable.sauce2,
//                caption = "Sauce"
//            )
//        )
//        carouselList.add(
//            CarouselItem(
//                imageDrawable = R.drawable.soup2,
//                caption = "Soup"
//            )
//        )
//        carouselList.add(
//            CarouselItem(
//                imageDrawable = R.drawable.snacks2,
//                caption = "Snacks"
//            )
//        )
//        carouselList.add(
//            CarouselItem(
//                imageDrawable = R.drawable.cereals2,
//                caption = "Cereals"
//            )
//        )
//        carouselList.add(
//            CarouselItem(
//                imageDrawable = R.drawable.omelette2,
//                caption = "Omelet"
//            )
//        )
//        carouselList.add(
//            CarouselItem(
//                imageDrawable = R.drawable.side_dish2,
//                caption = "Side Dish"
//            )
//        )
//    }
//
//
//    private fun initializeSliderList() {
//        var slider = Slider(1, R.drawable.pancake)
//        sliderList.add(slider)
//        slider = Slider(2, R.drawable.dessert3)
//        sliderList.add(slider)
//        slider = Slider(3, R.drawable.main_course3)
//        sliderList.add(slider)
//        slider = Slider(4, R.drawable.breakfast2)
//        sliderList.add(slider)
//        slider = Slider(5, R.drawable.salad2)
//        sliderList.add(slider)
//        slider = Slider(6, R.drawable.alcohol2)
//        sliderList.add(slider)
//        slider = Slider(7, R.drawable.bread2)
//        sliderList.add(slider)
//        slider = Slider(8, R.drawable.drinks2)
//        sliderList.add(slider)
//        slider = Slider(9, R.drawable.sauce2)
//        sliderList.add(slider)
//        slider = Slider(10, R.drawable.snacks2)
//        sliderList.add(slider)
//        slider = Slider(11, R.drawable.soup2)
//        sliderList.add(slider)
//        slider = Slider(12, R.drawable.cereals2)
//        sliderList.add(slider)
//        slider = Slider(13, R.drawable.omelette2)
//        sliderList.add(slider)
//        slider = Slider(14, R.drawable.omelette2)
//        sliderList.add(slider)
//        slider = Slider(15, R.drawable.prep)
//        sliderList.add(slider)
//
//
//    }


}