package codeasus.projects.renaissance.model.features.cocktail

import com.google.gson.annotations.SerializedName

data class Cocktail(
    @SerializedName("idDrink")
    val id: String,
    @SerializedName("strDrink")
    val name: String,
    @SerializedName("strDrinkThumb")
    val thumbnail: String
)