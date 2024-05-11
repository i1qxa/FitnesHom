package aps.fithom.aweq.presentation.monitoring

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.FragmentOnAttachListener
import aps.fithom.aweq.R
import aps.fithom.aweq.databinding.ActivityMonitoringBinding
import aps.fithom.aweq.domain.launchNewFragment
import aps.fithom.aweq.domain.makeInvisible
import aps.fithom.aweq.domain.makeVisible
import aps.fithom.aweq.presentation.monitoring.progres.ProgresFragment
import aps.fithom.aweq.presentation.monitoring.racion.dayliFood.DayliFoodFragment
import aps.fithom.aweq.presentation.monitoring.shoping_list.ShopingListFragment
import aps.fithom.aweq.presentation.monitoring.target.TargetFragment

class MonitoringActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMonitoringBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        supportFragmentManager.addFragmentOnAttachListener(fragmentChangeListener)
        setupNav()
    }

    private fun setupNav() {
        binding.shopingListIcon.setOnClickListener {
            supportFragmentManager.launchNewFragment(ShopingListFragment())
        }
        binding.racionIcon.setOnClickListener {
            supportFragmentManager.launchNewFragment(DayliFoodFragment())
        }
        binding.progresIcon.setOnClickListener {
            supportFragmentManager.launchNewFragment(ProgresFragment())
        }
        binding.targetIcon.setOnClickListener {
            supportFragmentManager.launchNewFragment(TargetFragment())
        }
    }

    private val fragmentChangeListener = FragmentOnAttachListener { fragmentManager, fragment ->
        when(fragment){
            is ShopingListFragment -> {
                launchShopingList()
            }
            is DayliFoodFragment -> {
                launchRacion()
            }
            is ProgresFragment -> {
                launchProgress()
            }
            is TargetFragment -> {
                launchTarget()
            }
        }
    }


    private fun launchShopingList() {
        binding.tvRacion.makeInvisible()
        binding.tvProgress.makeInvisible()
        binding.tvTarget.makeInvisible()
        binding.shopingListIcon.animate().apply {
            duration = 300
            scaleX(1.2F)
            scaleY(1.2F)
            withEndAction {
                binding.tvShopingList.makeVisible()
            }
        }
        binding.progresIcon.animate().apply {
            duration = 300
            scaleX(1F)
            scaleY(1F)
        }
        binding.racionIcon.animate().apply {
            duration = 300
            scaleX(1F)
            scaleY(1F)
        }
        binding.targetIcon.animate().apply {
            duration = 300
            scaleX(1F)
            scaleY(1F)
        }
    }

    private fun launchRacion() {
        binding.tvShopingList.makeInvisible()
        binding.tvProgress.makeInvisible()
        binding.tvTarget.makeInvisible()
        binding.racionIcon.animate().apply {
            duration = 300
            scaleX(1.2F)
            scaleY(1.2F)
            withEndAction {
                binding.tvRacion.makeVisible()
            }
        }
        binding.progresIcon.animate().apply {
            duration = 300
            scaleX(1F)
            scaleY(1F)
        }
        binding.shopingListIcon.animate().apply {
            duration = 300
            scaleX(1F)
            scaleY(1F)
        }
        binding.targetIcon.animate().apply {
            duration = 300
            scaleX(1F)
            scaleY(1F)
        }
    }

    private fun launchProgress() {
        binding.tvShopingList.makeInvisible()
        binding.tvRacion.makeInvisible()
        binding.tvTarget.makeInvisible()
        binding.progresIcon.animate().apply {
            duration = 300
            scaleX(1.2F)
            scaleY(1.2F)
            withEndAction {
                binding.tvProgress.makeVisible()
            }
        }
        binding.racionIcon.animate().apply {
            duration = 300
            scaleX(1F)
            scaleY(1F)
        }
        binding.shopingListIcon.animate().apply {
            duration = 300
            scaleX(1F)
            scaleY(1F)
        }
        binding.targetIcon.animate().apply {
            duration = 300
            scaleX(1F)
            scaleY(1F)
        }
    }

    private fun launchTarget() {
        binding.tvShopingList.makeInvisible()
        binding.tvRacion.makeInvisible()
        binding.tvProgress.makeInvisible()
        binding.targetIcon.animate().apply {
            duration = 300
            scaleX(1.2F)
            scaleY(1.2F)
            withEndAction {
                binding.tvTarget.makeVisible()
            }
        }
        binding.racionIcon.animate().apply {
            duration = 300
            scaleX(1F)
            scaleY(1F)
        }
        binding.shopingListIcon.animate().apply {
            duration = 300
            scaleX(1F)
            scaleY(1F)
        }
        binding.progresIcon.animate().apply {
            duration = 300
            scaleX(1F)
            scaleY(1F)
        }
    }



}