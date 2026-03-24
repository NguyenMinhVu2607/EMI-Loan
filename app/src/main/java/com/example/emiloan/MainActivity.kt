package com.example.emiloan

import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.emiloan.base.BaseActivity
import com.example.emiloan.base.inVisible
import com.example.emiloan.base.visible
import com.example.emiloan.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {
    private lateinit var navController: NavController

    override fun initView() {
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        navController.addOnDestinationChangedListener { _, destination, _ ->
            updateBottomNavUI(destination.id)
        }

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val currentDestId = navController.currentDestination?.id
                if (currentDestId == R.id.homeFragment) {
                    isEnabled = false
                    onBackPressedDispatcher.onBackPressed()
                    return
                }
                navigateToTab(R.id.homeFragment)
            }
        })
    }

    override fun initData() {
    }

    override fun initListener() {
        binding.btnNavHome.setOnClickListener {
            navigateToTab(R.id.homeFragment)
        }
        binding.btnNavHistory.setOnClickListener {
            navigateToTab(R.id.historyFragment)
        }
        binding.btnNavSetting.setOnClickListener {
            navigateToTab(R.id.settingFragment)
        }

    }


    private fun updateBottomNavUI(currentId: Int) {
        val colorSelected = getColor(R.color.brand_primary)
        val colorUnselected = getColor(R.color.white)

        fun updateTab(
            layoutId: Int,
            imgView: android.widget.ImageView,
            tvView: android.widget.TextView,
//            lineView: View,
            iconOn: Int,
            iconOff: Int,
        ) {
            if (currentId == layoutId) {
                imgView.setImageResource(iconOn)
                tvView.setTextColor(colorSelected)
//                lineView.visible()
                animateTab(imgView)
            } else {
                imgView.setImageResource(iconOff)
                tvView.setTextColor(colorUnselected)
//                lineView.inVisible()
            }
        }

        updateTab(
            R.id.homeFragment,
            binding.imgHome,
            binding.tvHome,
//            binding.lineHome,
            R.drawable.ic_home_on,
            R.drawable.ic_home_off
        )
        updateTab(
            R.id.historyFragment,
            binding.imgSwipe,
            binding.tvSwipe,
//            binding.lineSwipe,
            R.drawable.ic_history_on,
            R.drawable.ic_history_off
        )
        updateTab(
            R.id.settingFragment,
            binding.imgContact,
            binding.tvContact,
//            binding.lineContact,
            R.drawable.ic_setting_on,
            R.drawable.ic_setting_off
        )

    }

    private fun navigateToTab(resId: Int) {
        navController.navigate(
            resId, null, androidx.navigation.NavOptions.Builder()
                .setPopUpTo(navController.graph.startDestinationId, false)
                .setLaunchSingleTop(true)
                .setRestoreState(true)
                .build()
        )
    }

    private fun animateTab(view: android.view.View) {
        view.animate()
            .scaleX(1.2f).scaleY(1.2f)
            .setDuration(100)
            .withEndAction {
                view.animate().scaleX(1f).scaleY(1f).setDuration(100).start()
            }
            .start()
    }
}