package codeasus.projects.renaissance

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import codeasus.projects.renaissance.databinding.ActivityMainBinding
import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

class MainActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityMainBinding
    private lateinit var mNavController: NavController
    private lateinit var mNavHostFragment: NavHostFragment

    companion object {
        private val TAG = MainActivity::class.java.name
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        mNavHostFragment =
            supportFragmentManager.findFragmentById(mBinding.fragmentContainerView.id) as NavHostFragment
        mNavController = mNavHostFragment.navController
        setContentView(mBinding.root)

        GlobalScope.launch(Dispatchers.IO) {
            val processDuration = measureTimeMillis {
                val a = async(Dispatchers.IO) { getLocalNumbers() }
                val b = async(Dispatchers.IO) { getAppLocalNumbers() }
                Log.d(TAG, a.await())
                Log.d(TAG, b.await())
            }
            Log.d(TAG, "PROCESS DURATION: $processDuration")
        }
    }

    private suspend fun getLocalNumbers(): String {
        Log.i(TAG, "STARTED: getLocalNumbers()")
        delay(3000)
        Log.i(TAG, "COMPLETED: getLocalNumbers()")
        return "Response One"
    }

    private suspend fun getAppLocalNumbers(): String {
        Log.i(TAG, "STARTED: getAppLocalNumbers()")
        delay(5000)
        Log.i(TAG, "COMPLETED: getAppLocalNumbers()")
        return "Response Two"
    }

    override fun onNavigateUp(): Boolean {
        return super.onNavigateUp() || onSupportNavigateUp()
    }
}