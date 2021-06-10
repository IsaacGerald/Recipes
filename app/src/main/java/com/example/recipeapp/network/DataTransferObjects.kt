package com.example.recipeapp.network

import com.example.recipeapp.db.*
import com.example.recipeapp.models.Hit
import com.example.recipeapp.models.Recipe
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FoodRecipeContainer(val hits: List<Hit>)


fun FoodRecipeContainer.asDomainModel(): List<DomainRecipe>? {
    val recipes: MutableList<Recipe>? = mutableListOf()
    for (element in hits) {
        recipes?.add(element.recipe)
    }
    return recipes?.map {
        DomainRecipe(
            cuisineType = it.cuisineType,
            calories = it.calories,
            dishType = it.dishType,
            dietLabels = it.dietLabels,
            ingredientLines = it.ingredientLines,
            image = it.image,
            label = it.label,
            shareAs = it.shareAs,
            source = it.source,
            totalTime = it.totalTime,
            totalWeight = it.totalWeight,
            uri = it.uri,
            url = it.url,
            yield = it.yield,
            type = it.type,
            id = 0
        )
    }
}


fun DomainRecipe.asFavoriteRecipe() =
    FavoriteRecipes(
        id = 0,
        cuisineType = cuisineType,
        calories = calories,
        dishType = dishType,
        dietLabels = dietLabels,
        ingredientLines = ingredientLines,
        image = image,
        label = label,
        shareAs = shareAs,
        source = source,
        totalTime = totalTime,
        totalWeight = totalWeight,
        uri = uri,
        url = url,
        yield = this.yield
    )


fun FavoriteRecipes.asDomainRecipe() =
    DomainRecipe(
        cuisineType = cuisineType,
        calories = calories,
        dishType = dishType,
        dietLabels = dietLabels,
        ingredientLines = ingredientLines,
        image = image,
        label = label,
        shareAs = shareAs,
        source = source,
        totalTime = totalTime,
        totalWeight = totalWeight,
        uri = uri,
        url = url,
        yield = this.yield,
        type = "Favorite",
        id = 0

    )


fun DomainRecipe.asBookmarkRecipe() =
    BookmarkedRecipes(
        id = 0,
        cuisineType = cuisineType,
        calories = calories,
        dishType = dishType,
        dietLabels = dietLabels,
        ingredientLines = ingredientLines,
        image = image,
        label = label,
        shareAs = shareAs,
        source = source,
        totalTime = totalTime,
        totalWeight = totalWeight,
        uri = uri,
        url = url,
        yield = this.yield
    )

fun SearchRecipes.asDomainRecipe() =
    DomainRecipe(
        cuisineType = cuisineType,
        calories = calories,
        dishType = dishType,
        dietLabels = dietLabels,
        ingredientLines = ingredientLines,
        image = image,
        label = label!!,
        shareAs = shareAs,
        source = source,
        totalTime = totalTime,
        totalWeight = totalWeight,
        uri = uri,
        url = url,
        yield = this.yield,
        type = type,
        id = 0
    )

@JvmName("asDomainRecipeBookmarkedRecipes")
fun List<BookmarkedRecipes>.asDomainRecipe(): List<DomainRecipe> {
    return map {
        DomainRecipe(
            cuisineType = it.cuisineType,
            calories = it.calories,
            dishType = it.dishType,
            dietLabels = it.dietLabels,
            ingredientLines = it.ingredientLines,
            image = it.image,
            label = it.label!!,
            shareAs = it.shareAs,
            source = it.source,
            totalTime = it.totalTime,
            totalWeight = it.totalWeight,
            uri = it.uri,
            url = it.url,
            yield = it.yield,
            type = null,
            id = 0
        )
    }
}

@JvmName("asDomainRecipeFavoriteRecipes")
fun List<FavoriteRecipes>.asDomainRecipe(): List<DomainRecipe> {
    return map {
        DomainRecipe(
            cuisineType = it.cuisineType,
            calories = it.calories,
            dishType = it.dishType,
            dietLabels = it.dietLabels,
            ingredientLines = it.ingredientLines,
            image = it.image,
            label = it.label,
            shareAs = it.shareAs,
            source = it.source,
            totalTime = it.totalTime,
            totalWeight = it.totalWeight,
            uri = it.uri,
            url = it.url,
            yield = it.yield,
            type = null,
            id = 0
        )
    }
}

fun List<DomainRecipe>.asSearchRecipes(): List<SearchRecipes> {
    return map {
        SearchRecipes(
            id = 0,
            cuisineType = it.cuisineType,
            calories = it.calories,
            dishType = it.dishType,
            dietLabels = it.dietLabels,
            ingredientLines = it.ingredientLines,
            image = it.image,
            label = it.label,
            shareAs = it.shareAs,
            source = it.source,
            totalTime = it.totalTime,
            totalWeight = it.totalWeight,
            uri = it.uri,
            url = it.url,
            yield = it.yield,
            type = it.type

        )
    }


}

fun List<String>.asIngredientRecipe(): List<CartRecipes> {
    return map {
        CartRecipes(
            id = 0,
            label = "label",
            ingredient = it,
            isAddedToCart = false,
            bought = false
        )
    }


}


fun DomainRecipe.asIngredientRecipe() =
    IngredientRecipe(
        cuisineType = cuisineType,
        calories = calories,
        dishType = dishType,
        dietLabels = dietLabels,
        ingredientLines = ingredientLines,
        image = image,
        label = label!!,
        shareAs = shareAs,
        source = source,
        totalTime = totalTime,
        totalWeight = totalWeight,
        uri = uri,
        url = url,
        yield = this.yield,
        type = type
    )




