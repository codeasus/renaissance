package codeasus.projects.renaissance.features.cocktail.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import codeasus.projects.renaissance.model.features.cocktail.Cocktails
import codeasus.projects.renaissance.network.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CocktailViewModel(application: Application) : AndroidViewModel(application) {

    private val mCocktails = MutableStateFlow<Cocktails?>(null)
    val cocktails = mCocktails.asStateFlow()

    init {
        getCocktailsByCategory("Cocktail")
    }

    fun getCocktailsByCategory(category: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val cocktailsResponse = RetrofitInstance.cocktailService.getCocktailsByCategory(category)
            val cocktails = cocktailsResponse.body()
            if (cocktailsResponse.isSuccessful && cocktails != null) {
                mCocktails.emit(cocktails)
            }
        }
    }
}