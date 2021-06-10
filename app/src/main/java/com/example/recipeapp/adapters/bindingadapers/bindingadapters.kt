package com.example.recipeapp.adapters.bindingadapers

import android.net.Uri
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.recipeapp.R
import com.example.recipeapp.db.BookmarkedRecipes
import com.example.recipeapp.db.DomainRecipe
import com.example.recipeapp.db.FavoriteRecipes
import com.example.recipeapp.db.SearchRecipes
import com.example.recipeapp.models.*

@BindingAdapter("RecipeImageView")
fun bindRecipeImage(imageView: ImageView, meal: DomainRecipe?) {

    if (meal != null){
        val imageUrl = Uri.parse(meal.image)

        Glide.with(imageView)
            .load(imageUrl)
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.meal_placeholder)
            )
            .into(imageView)
    }else{
        Glide.with(imageView)
            .load(R.drawable.meal_placeholder)
            .into(imageView)

    }


}

@BindingAdapter("bindSliderImage")
fun bindSliderImageView(imageView: ImageView, slider: Slider){

    val image = slider.image
   // val requestOptions = RequestOptions()
//    requestOptions.apply {
//        this.error(R.drawable.ic_broken_image)
//        this.placeholder(R.drawable.loading_animation)
//    }

    Glide.with(imageView)
        .load(image)
        .into(imageView)

}


@BindingAdapter("SearchImageView")
fun bindMealsImage(imageView: ImageView, meal: SearchRecipes) {
    val imageUrl = Uri.parse(meal.image)

    Glide.with(imageView)
        .load(imageUrl)
        .apply(
            RequestOptions()
                .placeholder(R.drawable.meal_placeholder)
        )
        .into(imageView)

}


@BindingAdapter("BookmarkImageView")
fun bindMealsImage(imageView: ImageView, meal: BookmarkedRecipes) {

    val imageUrl = Uri.parse(meal.image)

    Glide.with(imageView)
        .load(imageUrl)
        .apply(
            RequestOptions()
                .placeholder(R.drawable.meal_placeholder)
        )
        .into(imageView)

}

@BindingAdapter("FavoriteImageView")
fun bindMealsImage(imageView: ImageView, meal: FavoriteRecipes) {

    val imageUrl = Uri.parse(meal.image)

    Glide.with(imageView)
        .load(imageUrl)
        .apply(
            RequestOptions()
                .placeholder(R.drawable.meal_placeholder)
        )
        .into(imageView)

}

@BindingAdapter("DomainRecipeImageView")
fun bindMealsImage(imageView: ImageView, meal: DomainRecipe) {

    val imageUrl = Uri.parse(meal.image)

    Glide.with(imageView)
        .load(imageUrl)
        .apply(
            RequestOptions()
                .placeholder(R.drawable.meal_placeholder)
        )
        .into(imageView)

}


