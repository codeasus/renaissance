package codeasus.projects.renaissance.features.meal.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import codeasus.projects.renaissance.R
import codeasus.projects.renaissance.databinding.FragmentMealBinding
import codeasus.projects.renaissance.features.meal.adapter.MealAdapter
import codeasus.projects.renaissance.model.features.meal.Meal

class MealFragment : Fragment() {

    private lateinit var mBinding: FragmentMealBinding

    private lateinit var mRVMeal: RecyclerView
    private lateinit var mMealAdapter: MealAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentMealBinding.inflate(layoutInflater)
        setView()
        return mBinding.root
    }

    fun setView() {
        mRVMeal = mBinding.rvMeals
        mMealAdapter = MealAdapter(getMeals())
        mRVMeal.adapter = mMealAdapter
        mRVMeal.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun getMeals(): List<Meal> {
        return listOf(
            Meal(
                "https://www.themealdb.com/images/media/meals/ryppsv1511815505.jpg",
                "BeaverTails"
            ),
            Meal(
                "https://www.themealdb.com/images/media/meals/1550441882.jpg",
                "Breakfast Potatoes"
            ),
            Meal(
                "https://www.themealdb.com/images/media/meals/wpputp1511812960.jpg",
                "Canadian Butter Tarts"
            ),
            Meal(
                "https://www.themealdb.com/images/media/meals/uttupv1511815050.jpg",
                "Montreal Smoked Meat"
            ),
            Meal(
                "https://www.themealdb.com/images/media/meals/vwuprt1511813703.jpg",
                "Nanaimo Bars"
            ),
            Meal(
                "https://www.themealdb.com/images/media/meals/yyrrxr1511816289.jpg",
                "Pate Chinois"
            ),
            Meal(
                "https://www.themealdb.com/images/media/meals/yqqqwu1511816912.jpg",
                "Pouding chomeur"
            ),
            Meal(
                "https://www.themealdb.com/images/media/meals/uuyrrx1487327597.jpg",
                "Poutine"
            ),
            Meal(
                "https://www.themealdb.com/images/media/meals/ruwpww1511817242.jpg",
                "Rappie Pie"
            ),
            Meal(
                "https://www.themealdb.com/images/media/meals/xxtsvx1511814083.jpg",
                "Split Pea Soup"
            ),
            Meal(
                "https://www.themealdb.com/images/media/meals/yrstur1511816601.jpg",
                "Sugar Pie"
            ),
            Meal(
                "https://www.themealdb.com/images/media/meals/txsupu1511815755.jpg",
                "Timbits"
            ),
            Meal(
                "https://www.themealdb.com/images/media/meals/ytpstt1511814614.jpg",
                "Tourtiere"
            )
        )
    }
}