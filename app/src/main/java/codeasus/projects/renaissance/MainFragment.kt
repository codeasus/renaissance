package codeasus.projects.renaissance

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import codeasus.projects.renaissance.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    private lateinit var mBinding: FragmentMainBinding
    private lateinit var mNavController: NavController

    companion object {
        private val TAG = MainFragment::class.java.name
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentMainBinding.inflate(layoutInflater, container, false)
        mNavController = findNavController()
        setMenuVisibility(false)
        setView()
        return mBinding.root
    }

    private fun setView() {
        mBinding.apply {
            btnCocktailFragment.setOnClickListener {
                mNavController.navigate(R.id.mainFragmentToMealFragment)
            }

            btnContactFragment.setOnClickListener {
                mNavController.navigate(R.id.mainFragmentToContactFragment)
            }
        }
    }
}