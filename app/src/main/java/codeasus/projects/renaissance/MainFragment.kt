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
        const val TAG = "MainFragment"
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
            btnMealFragment.setOnClickListener {
                mNavController.navigate(R.id.mainFragmentToMealFragment)
            }

            btnContactFragment.setOnClickListener {
                mNavController.navigate(R.id.mainFragmentToContactFragment)
            }
        }

        val dateTimeArray: Array<Long> = arrayOf(
            1675681429,
            1675611429,
            1675601429,
            1675581412,
            1675541429,
            1675511765,
            1675451765,
            1675242765,
            1675212765,
            1675111765,
            1675011765,
            1674900065,
            1674800065,
            1674400065,
            1671684392
        )

//        for (i in dateTimeArray) {
//            Log.d(
//                TAG,
//                "Formatted datetime: ${Date((i * 1000)).formatAsLastSeen(requireContext())}"
//            )
//        }
    }
}