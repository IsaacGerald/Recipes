package com.example.recipeapp.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
data class Recipe(
    val calories: Double,
    val cautions: List<String>,
    val cuisineType: List<String>?,
    val dietLabels: @RawValue List<String>?,
    val digest: @RawValue List<Digest>?,
    val dishType: @RawValue List<String>?,
    val healthLabels: List<String>,
    val image: String,
    val ingredientLines: List<String>,
    val ingredients: @RawValue List<Ingredient>?,
    val label: String,
    val mealType: List<String>?,
    val shareAs: String,
    val source: String,
    val totalTime: Double?,
    val totalWeight: Double,
    val uri: String?,
    val url: String?,
    val yield: Double?,
    val type: String?
):Parcelable

data class Ingredient(
    val foodCategory: String?,
    val foodId: String,
    val image: String?,
    val text: String,
    val weight: Double
)

@JsonClass(generateAdapter = true)
data class Hit(
    val bookmarked: Boolean?,
    val bought: Boolean?,
    val recipe: Recipe
)

@JsonClass(generateAdapter = true)
data class FoodRecipe(
    val count: Int,
    val from: Int,
    val hits: List<Hit>,
    val more: Boolean,
    val q: String,
    val to: Int
)

data class Digest(
    val daily: Double,
    val label: String,
    val tag: String,
    val total: Double,
    val unit: String
)