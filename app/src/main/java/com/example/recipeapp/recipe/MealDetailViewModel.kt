package com.example.recipeapp.recipe

import androidx.lifecycle.*
import com.example.recipeapp.db.CartRecipes
import com.example.recipeapp.db.DomainRecipe
import com.example.recipeapp.db.FavoriteRecipes
import com.example.recipeapp.db.IngredientRecipe
import com.example.recipeapp.network.asBookmarkRecipe
import com.example.recipeapp.network.asFavoriteRecipe
import com.example.recipeapp.repository.FoodRepository
import kotlinx.coroutines.launch
import java.math.BigDecimal

class MealDetailViewModel(private val args: DomainRecipe, private val repository: FoodRepository) :
    ViewModel() {
    private val TAG = MealDetailViewModel::class.java.simpleName

    private val _recipe = MutableLiveData<DomainRecipe>()
    private val _ingredient = MutableLiveData<String?>()
    private val _ingredientRecipe = MutableLiveData<IngredientRecipe>()
    private val _cartIngredients = MutableLiveData<List<CartRecipes>>()

    val favoriteRecipes: LiveData<FavoriteRecipes>?
    get() = repository.getFavoriteRecipe(args.label)

    val cartIngredients: LiveData<List<CartRecipes>>
    get() = repository.getCartIngredients(args.label)

    val ingredientsRecipe: LiveData<IngredientRecipe>
        get() = _ingredientRecipe

    val recipe: LiveData<DomainRecipe?>
        get() = _recipe

    val ingredient: LiveData<String?>
        get() = _ingredient

    private val _isClicked = MutableLiveData<Boolean>()

    val isClicked: LiveData<Boolean>
        get() = _isClicked


    val totalTime = Transformations.map(recipe) { recipe ->
        val time = recipe?.totalTime
        var newTime: String? = null
        if (time != null) {
            newTime = BigDecimal(time).toPlainString()
        }

        newTime

    }


    init {
        getRecipes()
        getShoppingIngredientRecipe(args.label)
        //getFavorites(args.label)
        //getIngredientsFromCart(args.label)
    }

    private fun getFavorites(label: String): LiveData<FavoriteRecipes>? {
        return repository.getFavoriteRecipe(label)
    }

    private fun getRecipes() {
        _recipe.value = args
    }

    private fun getShoppingIngredientRecipe(label: String) {

        val recipe = repository.getRecipeIngredient(label)
        _ingredientRecipe.postValue(recipe.value)
//        viewModelScope.launch {
//            val recipe = repository.getRecipeIngredient(label)
//            Log.d(TAG, "getShoppingIngredientRecipe: recipe $recipe")
//             _ingredientRecipe.value = recipe
//        }

    }

    fun getIngredients(recipe: DomainRecipe): List<String> {

        val ingredients = mutableListOf<String>()
        viewModelScope.launch {
            for (ing in recipe.ingredientLines!!) {
                ingredients.add(ing)
            }
        }

        return ingredients
    }

    fun addToBookmarks() {
        viewModelScope.launch {
            repository.addBookMark(args.asBookmarkRecipe())
        }
    }

    fun addToFavorites() {
        viewModelScope.launch {
            repository.addFavorites(args.asFavoriteRecipe())
        }
    }

    fun deleteFavorites(label: String) {
        viewModelScope.launch {
            repository.deleteFavorite(label)
        }
    }

    fun displayShoppingList(ingr: String) {
        _ingredient.value = ingr
    }

    fun displayShoppingList() {
        _ingredient.value = null
    }

    fun ingrIsClicked(bool: Boolean) {
        _isClicked.value = bool
    }

     fun getIngredientsFromCart(label: String) {

            val recipes = repository.getCartIngredients(label)
           _cartIngredients.postValue(recipes.value)
        }



    fun addToCart(item: CartRecipes) {
        viewModelScope.launch {
            repository.addIngredientToCart(item)
        }
    }

    fun removeFromCart(ingr: String) {
        viewModelScope.launch {
            repository.deleteIngredientFromCart(ingr)
        }
    }

    fun addToIngredientRecipe(recipe: IngredientRecipe) {
        viewModelScope.launch {
            repository.addToIngredientRecipe(recipe)
        }
    }

//    fun getAllCartRecipes(recipe: String): List<CartRecipes> {
//           return repository.getCartIngredients(recipe)
//    }

    fun deleteIngredientRecipe(label: String) {
        viewModelScope.launch {
            repository.deleteIngredientRecipe(label)
        }
    }

    fun getReceivedIngredients(it: IngredientRecipe):IngredientRecipe {
        return it
    }

}