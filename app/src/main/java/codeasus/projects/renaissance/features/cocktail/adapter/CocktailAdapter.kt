package codeasus.projects.renaissance.features.cocktail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import codeasus.projects.renaissance.R
import codeasus.projects.renaissance.databinding.RvItemCocktailBinding
import codeasus.projects.renaissance.model.features.cocktail.Cocktail
import coil.load
import coil.transform.RoundedCornersTransformation

class CocktailAdapter(private val cocktailImageList: List<Cocktail>) :
    RecyclerView.Adapter<CocktailAdapter.MealImageViewHolder>() {

    inner class MealImageViewHolder(private val binding: RvItemCocktailBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(cocktail: Cocktail) {
            binding.apply {
                ivCocktail.load(
                    cocktail.thumbnail,
                    builder = {
                        placeholder(R.drawable.placeholder_image)
                        this.transformations(
                            RoundedCornersTransformation(20.0F)
                        )
                    }
                )
                tvCocktailName.text = cocktail.name
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealImageViewHolder {
        val view = RvItemCocktailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MealImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: MealImageViewHolder, position: Int) {
        holder.bind(cocktailImageList[position])
    }

    override fun getItemCount(): Int {
        return cocktailImageList.size
    }
}