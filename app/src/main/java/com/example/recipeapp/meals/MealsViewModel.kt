package com.example.recipeapp.meals

import androidx.lifecycle.*
import com.example.recipeapp.constants.CATEGORY
import com.example.recipeapp.constants.CUISINE
import com.example.recipeapp.constants.MEAL
import com.example.recipeapp.db.DomainRecipe
import com.example.recipeapp.models.*
import com.example.recipeapp.repository.FoodRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MealsViewModel(
    private val modelName: String,
    val id: Int,
    val repository: FoodRepository
) :
    ViewModel() {
    private val TAG = MealsViewModel::class.java.simpleName
    private val _displayName = MutableLiveData<String?>()
    private val _navigateToSelectedMeal = MutableLiveData<DomainRecipe?>()
    private val _isNetworkConnected = MutableLiveData<Boolean>()
    private val _displayImage = MutableLiveData<Int>()
    private var category: Categories? = null
    private var cuisines: Cuisine? = null
    private var meals: Meal? = null
    private var queryString: String? = null
    private var lastQueryString = MutableLiveData<String>()


    val response: LiveData<String>
        get() = repository.response

    private val _mealResponse = MutableLiveData<List<DomainRecipe>>()

    fun mealResponse() =
        repository.getSearchRecipes(queryString!!)


    val displayName: LiveData<String?>
        get() = _displayName

    val navigateToSelectedMeal: MutableLiveData<DomainRecipe?>
        get() = _navigateToSelectedMeal

    val displayImage: LiveData<Int>
        get() = _displayImage

    val displayResponse: LiveData<String>
        get() = repository.response


    init {

        getRecipe(modelName, id)
        // getMealResponse(queryString!!)


    }

//    private fun getMealResponse(query: String) {
//
//        val response = repository.getSearchRecipes(query)
//        _mealResponse.postValue(response.value)
//
//
//    }

    private fun getRecipe(modelName: String, foodId: Int) {


        getModel(modelName, foodId)




        queryString = getQueryName()

        refreshFoodRecipe()


    }

    fun isNetworkAvailable(isConnected: Boolean) {

    }


    fun refreshFoodRecipe() {

        if (queryString != null) {
            viewModelScope.launch {
                //repository.clearAll()
                repository.refreshFoodRecipes(queryString)
            }

        }


    }


    private fun getQueryName(): String? {
        var name: String? = null

        when {
            category != null -> {
                name = this.category!!.name
                _displayName.value = name
            }
            cuisines != null -> {
                name = this.cuisines!!.name
                _displayName.value = name
            }
            meals != null -> {
                name = this.meals!!.name
                _displayName.value = name
            }
        }

        return name
    }

    private fun getModel(modelName: String, foodId: Int) {

//        val foodType = DataManager.foodTypes
//        val cuisineType = DataManager.cuisineTypes
//        val mealType = DataManager.dishTypes
//        when (modelName) {
//            CATEGORY -> {
//                _displayImage.value = DataManager.getImageCategory(id)
//                viewModelScope.launch {
//                    for (food in foodType) {
//                        if (foodId == food.id) {
//                            category = food
//                        }
//                    }
//                }
//
//            }
//            CUISINE -> {
//                _displayImage.value = DataManager.getImageCuisine(id)
//                viewModelScope.launch {
//                    for (food in cuisineType) {
//                        if (foodId == food.id) {
//                            cuisines = food
//                        }
//                    }
//                }
//
//            }
//            MEAL -> {
//                _displayImage.value = DataManager.getImageMeals(id)
//                viewModelScope.launch {
//                    for (food in mealType) {
//                        if (foodId == food.id) {
//                            meals = food
//                        }
//                    }
//                }
//
//            }
//            else -> null
//        }

    }


    fun displayDetailMeal(recipe: DomainRecipe?) {

        _navigateToSelectedMeal.value = recipe


    }

    fun displayDetailMealComplete() {
        _navigateToSelectedMeal.value = null
    }


}