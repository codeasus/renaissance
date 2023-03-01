package codeasus.projects.renaissance.network

import codeasus.projects.renaissance.model.features.cocktail.Cocktails
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CocktailService {
    @GET("filter.php?c=Cocktail")
    suspend fun getCocktailsByCategory(@Query("c") category: String): Response<Cocktails>
}