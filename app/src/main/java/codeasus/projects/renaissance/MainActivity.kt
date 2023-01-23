package codeasus.projects.renaissance

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import codeasus.projects.renaissance.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityMainBinding
    private lateinit var mNavController: NavController
    private lateinit var mNavHostFragment: NavHostFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        mNavHostFragment =
            supportFragmentManager.findFragmentById(mBinding.fragmentContainerView.id) as NavHostFragment
        mNavController = mNavHostFragment.navController
        setContentView(mBinding.root)
    }

    override fun onNavigateUp(): Boolean {
        return super.onNavigateUp() || onSupportNavigateUp()
    }
}