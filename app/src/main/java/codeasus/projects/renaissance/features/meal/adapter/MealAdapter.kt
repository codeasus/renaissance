package codeasus.projects.renaissance.features.meal.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import codeasus.projects.renaissance.R
import codeasus.projects.renaissance.databinding.RvMealItemBinding
import codeasus.projects.renaissance.model.features.meal.Meal
import coil.load
import coil.transform.RoundedCornersTransformation

class MealAdapter(private val mealImageList: List<Meal>) :
    RecyclerView.Adapter<MealAdapter.MealImageViewHolder>() {

    inner class MealImageViewHolder(private val binding: RvMealItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(meal: Meal) {
            binding.apply {
                tvImage.load(
                    meal.imageURL,
                    builder = {
                        placeholder(R.drawable.placeholder_image)
                        this.transformations(
                            RoundedCornersTransformation(30.0F)
                        )
                    }
                )
                tvText.text = meal.name
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealImageViewHolder {
        val view = RvMealItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MealImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: MealImageViewHolder, position: Int) {
        holder.bind(mealImageList[position])
    }

    override fun getItemCount(): Int {
        return mealImageList.size
    }
}