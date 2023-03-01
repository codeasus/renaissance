package codeasus.projects.renaissance.features.cocktail.view

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import codeasus.projects.renaissance.R
import codeasus.projects.renaissance.databinding.FragmentCocktailBinding
import codeasus.projects.renaissance.features.cocktail.adapter.CocktailAdapter
import codeasus.projects.renaissance.features.cocktail.viewmodel.CocktailViewModel
import codeasus.projects.renaissance.model.features.cocktail.Cocktail
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CocktailFragment : Fragment() {

    private lateinit var mBinding: FragmentCocktailBinding
    private lateinit var mVM: CocktailViewModel
    private lateinit var mRVMeal: RecyclerView
    private lateinit var mCocktailAdapter: CocktailAdapter
    private lateinit var mCocktailCategoryAdapter: ArrayAdapter<String>
    private lateinit var mMenuHost: MenuHost

    private var mCocktails: MutableList<Cocktail> = mutableListOf()

    companion object {
        private val COCKTAIL_CATEGORIES = arrayOf(
            "Cocktail",
            "Ordinary Drink"
        )
        private val TAG = CocktailFragment::class.java.name
    }

    private val cocktailCategoryMap = mapOf(
        0 to "Cocktail",
        1 to "Ordinary_Drink"
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentCocktailBinding.inflate(layoutInflater)
        mVM = ViewModelProvider(this)[CocktailViewModel::class.java]
        mMenuHost = requireActivity()
        setView()
        observeData()
        return mBinding.root
    }

    private fun setView() {
        mRVMeal = mBinding.rvCocktail
        mCocktailAdapter = CocktailAdapter(mCocktails)
        mRVMeal.adapter = mCocktailAdapter
        mRVMeal.layoutManager = LinearLayoutManager(requireContext())

        mMenuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.meal_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.menu_meal_save -> {
                        Toast.makeText(requireContext(), "Save meals", Toast.LENGTH_SHORT).show()
                        true
                    }
                    R.id.menu_meal_clear -> {
                        Toast.makeText(requireContext(), "Clear db", Toast.LENGTH_SHORT).show()
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

        mCocktailCategoryAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_list_item_1,
            COCKTAIL_CATEGORIES
        )
        mBinding.acTvCocktailCategories.setText(COCKTAIL_CATEGORIES[0])
        mBinding.acTvCocktailCategories.setAdapter(mCocktailCategoryAdapter)
        mBinding.acTvCocktailCategories.setOnItemClickListener { _, _, position, _ ->
            mVM.getCocktailsByCategory(cocktailCategoryMap[position]!!)
        }
    }

    private fun observeData() {
        viewLifecycleOwner.lifecycleScope.launch {
            mVM.cocktails.collectLatest { cocktails ->
                if (cocktails != null) {
                    Log.d(TAG, cocktails.cocktails.toString())
                    withContext(Dispatchers.Main) {
                        mCocktails.clear()
                        mCocktails.addAll(cocktails.cocktails)
                        mCocktailAdapter.notifyItemRangeChanged(0, mCocktails.size)
                    }
                }
            }
        }
    }
}