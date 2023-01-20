package codeasus.projects.renaissance.features.contanct.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import codeasus.projects.renaissance.R
import codeasus.projects.renaissance.databinding.FragmentContactBinding

class ContactFragment : Fragment() {

    private lateinit var mBinding: FragmentContactBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentContactBinding.inflate(layoutInflater)
        return mBinding.root
    }
}