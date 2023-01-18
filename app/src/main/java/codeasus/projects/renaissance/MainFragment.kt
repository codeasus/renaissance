package codeasus.projects.renaissance

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import codeasus.projects.renaissance.databinding.FragmentMainBinding

class MainFragment : Fragment() {
    private lateinit var mBinding: FragmentMainBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentMainBinding.inflate(layoutInflater)
        return mBinding.root
    }
}