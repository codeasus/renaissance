package codeasus.projects.renaissance.features.modifycontact.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import codeasus.projects.renaissance.databinding.FragmentModifyContactBinding

class ModifyContactFragment : Fragment() {

    private lateinit var mBinding: FragmentModifyContactBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentModifyContactBinding.inflate(layoutInflater)
        return mBinding.root
    }
}