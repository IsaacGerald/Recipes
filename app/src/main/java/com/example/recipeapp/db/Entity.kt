package com.example.recipeapp.db

import android.os.Parcelable
import androidx.appcompat.app.AppCompatActivity
import androidx.room.*
import com.example.recipeapp.Injection
import com.example.recipeapp.MainActivity
import com.example.recipeapp.home.HomeFragment
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.parcel.Parcelize
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext

@Entity(tableName = "recipe_table")
@Parcelize
data class DomainRecipe(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val label: String,
    val url: String?,
    val cuisineType: List<String>?,
    val calories: Double?,
    val dishType: List<String>?,
    val dietLabels: List<String>?,
    val image: String?,
    val ingredientLines: List<String>?,
    val shareAs: String?,
    val source: String?,
    val totalTime: Double?,
    val totalWeight: Double?,
    val uri: String?,
    val yield: Double?,
    var type: String?,
    var liked: Boolean = false

) : Parcelable{



}

@Entity(tableName = "bookmarked_recipes")
@Parcelize
data class BookmarkedRecipes(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val url: String?,
    val cuisineType: List<String>?,
    val calories: Double?,
    val dishType: List<String>?,
    val dietLabels: List<String>?,
    val image: String?,
    val ingredientLines: List<String>?,
    val label: String?,
    val shareAs: String?,
    val source: String?,
    val totalTime: Double?,
    val totalWeight: Double?,
    val uri: String?,
    val yield: Double?
) : Parcelable

@Entity(tableName = "favorite_recipes")
@Parcelize
data class FavoriteRecipes(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val label: String,
    val url: String?,
    val cuisineType: List<String>?,
    val calories: Double?,
    val dishType: List<String>?,
    val dietLabels: List<String>?,
    val image: String?,
    val ingredientLines: List<String>?,
    val shareAs: String?,
    val source: String?,
    val totalTime: Double?,
    val totalWeight: Double?,
    val uri: String?,
    val yield: Double?
) : Parcelable

@Entity(tableName = "search_table", indices = [Index(value = [ "label" ], unique = true)])
@Parcelize
data class SearchRecipes(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val url: String?,
    val label: String?,
    val cuisineType: List<String>?,
    val calories: Double?,
    val dishType: List<String>?,
    val dietLabels: List<String>?,
    val image: String?,
    val ingredientLines: List<String>?,
    val shareAs: String?,
    val source: String?,
    val totalTime: Double?,
    val totalWeight: Double?,
    val uri: String?,
    val yield: Double?,
    val type: String?
) : Parcelable

@Entity(tableName = "ingredient_recipes")
@Parcelize
data class IngredientRecipe(
    @PrimaryKey(autoGenerate = false)
    val label: String,
    val url: String?=null,
    val cuisineType: List<String>?=null,
    val calories: Double?=null,
    val dishType: List<String>?=null,
    val dietLabels: List<String>?=null,
    val image: String?=null,
    val ingredientLines: List<String>?=null,
    val shareAs: String?=null,
    val source: String?=null,
    val totalTime: Double?=null,
    val totalWeight: Double?=null,
    val uri: String?=null,
    val yield: Double?=null,
    val type: String?=null
) : Parcelable

@Entity(tableName = "cart_table")
data class CartRecipes(
    @PrimaryKey(autoGenerate = false)
    val ingredient: String,
    val id: Long,
    var label: String,
    var isAddedToCart: Boolean,
    val bought: Boolean?= false
)




class CuisinesTypeConverters() {

    @TypeConverter
    fun fromList(list: List<String>?): String {
        return Gson().toJson(list)
    }

    @TypeConverter
    fun fromString(value: String?): List<String>? {
        val listType = object : TypeToken<List<String>>() {}.type
        return Gson().fromJson(value, listType)
    }

}


//fun List<DatabaseRecipe>.asDomainModel(): List<DomainRecipe>{
//    return map {
//        DomainRecipe(
//            id = 0,
//            calories = it.calories,
//            image = it.image,
//            label = it.label,
//            shareAs = it.shareAs,
//            source = it.source,
//            totalTime = it.totalTime,
//            totalWeight = it.totalWeight,
//            uri = it.uri,
//            url = it.url,
//            yield = it.yield
//        )
//    }

//}

//data class DatabaseRecipe(
//    @PrimaryKey(autoGenerate = true)
//    val id: Long,
//    val url: String?,
//    val calories: Double,
//    val image: String,
//    val label: String,
//    val shareAs: String,
//    val source: String,
//    val totalTime: Double?,
//    val totalWeight: Double,
//    val uri: String?,
//    val yield: Double?
//)
