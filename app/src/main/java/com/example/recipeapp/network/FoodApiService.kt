package com.example.recipeapp.network

import com.example.recipeapp.constants.APP_ID
import com.example.recipeapp.constants.APP_KEY
import com.example.recipeapp.constants.BASE_URL
import com.example.recipeapp.models.FoodRecipe
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


enum class FoodApiTypes(val value: String) {
    MAIN_COURSE("Main Course"),
    SIDE_DISH("Side Dish"),
    DESSERT("Dessert"),
    PREPS("Preps"),
    SALAD("Salad"),
    BREAD("Bread"),
    BREAKFAST("Breakfast"),
    SOUP("Soup"),
    ALCOHOL("Alcohol"),
    SAUCE("Sauce"),
    OMELET("Omelet"),
    SANDWICHES("Sandwiches"),
    SNACK("Snack"),
    DRINK("Drink"),
    PANCAKE("Pancake"),
    CEREALS("Cereals")

}

enum class Cuisines(val value: String) {
    AMERICAN("American"),
    ASIAN("Asian"),
    BRITISH("British"),
    CARIBBEAN("Caribbean"),
    CENTRAL_EUROPE("Central europe"),
    CHINESE("Chinese"),
    EASTERN_EUROPE("Eastern europe"),
    FRENCH("French"),
    INDIAN("Indian"),
    JAPANESE("Japanese"),
    ITALIAN("Italian"),
    KOSHER("Kosher"),
    MEDITERRANEAN("Mediterranean"),
    MEXICAN("Mexican"),
    MIDDLE_EASTERN("Middle Eastern"),
    NORDIC("Nordic"),
    SOUTH_AMERICAN(" South American "),
    SOUTH_EAST_ASIAN("South East Asian ")

}

enum class DishTypes(val value: String) {
    BREAKFAST("Breakfast"),
    LUNCH("lunch"),
    DINNER("Dinner"),
    SNACK("Snack"),
    TEATIME("Teatime")
}

private val Moshi = com.squareup.moshi.Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()



private val retrofit = Retrofit.Builder()
    .addConverterFactory(ApiWorker.gsonConverter)
    .client(ApiWorker.client)
    .baseUrl(BASE_URL)
    .build()


interface FoodApiService {

    @GET("search")
    suspend fun getFoodTypes(
        @Query("app_id") appId: String = APP_ID,
        @Query("app_key") apiKey: String = APP_KEY,
        @Query("q") query: String,
        @Query("from") from: Int? = 0,
        @Query("to") to: Int? = 50,
    ): Response<FoodRecipeContainer>


}

object FoodApi {
    val retrofitService: FoodApiService by lazy {
        retrofit.create(FoodApiService::class.java)
    }
}



