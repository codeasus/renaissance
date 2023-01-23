package codeasus.projects.renaissance.features.contanct.view

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import codeasus.projects.renaissance.R
import codeasus.projects.renaissance.databinding.FragmentContactBinding

class ContactFragment : Fragment() {

    private lateinit var mBinding: FragmentContactBinding
    private lateinit var mNavController: NavController
    private lateinit var mMenuHost: MenuHost

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentContactBinding.inflate(layoutInflater)
        mNavController = findNavController()
        mMenuHost = requireActivity()
        setView()
        return mBinding.root
    }

    private fun setView() {
        mMenuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_contact, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.menu_contact_update -> {
                        Toast.makeText(requireContext(), "Update contacts", Toast.LENGTH_SHORT).show()
                        true
                    }
                    R.id.menu_contact_clear -> {
                        Toast.makeText(requireContext(), "Delete contacts", Toast.LENGTH_SHORT).show()
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }
}