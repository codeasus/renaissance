package codeasus.projects.renaissance

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import codeasus.projects.renaissance.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    private lateinit var mBinding: FragmentMainBinding
    private lateinit var mNavController: NavController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentMainBinding.inflate(layoutInflater, container, false)
        mNavController = findNavController()
        setView()
        return mBinding.root
    }

    private fun setView() {
        mBinding.btnMealFragment.setOnClickListener {
            mNavController.navigate(R.id.mainFragmentToMealFragment)
        }
    }
}