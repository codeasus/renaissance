package codeasus.projects.renaissance.model.features.cocktail

import com.google.gson.annotations.SerializedName

data class Cocktails(
    @SerializedName("drinks")
    val cocktails: List<Cocktail>
)
