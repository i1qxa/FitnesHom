package aps.fithom.aweq.presentation.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import aps.fithom.aweq.R
import aps.fithom.aweq.databinding.ActivityMainBinding
import aps.fithom.aweq.domain.IS_INITIAL_SETUP_COMPLETE
import aps.fithom.aweq.domain.State
import aps.fithom.aweq.domain.dataStore
import aps.fithom.aweq.presentation.monitoring.MonitoringActivity
import aps.fithom.aweq.presentation.questions.QuestionsActivity
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val viewModel by viewModels<MainViewModel>()
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
        observeState()
        setupBtnClickListener()
    }

    private fun observeState(){
        viewModel.state.observe(this){ state ->
            if (state==State.COMPLETE){
                binding.baseProgress.visibility = View.GONE
                binding.btnStart.visibility = View.VISIBLE
            }
        }
    }

    private fun setupBtnClickListener(){
        binding.btnStart.setOnClickListener {
            launchNextScreen()
        }
    }

    private fun launchNextScreen(){
        lifecycleScope.launch {
            dataStore.data.collect{
                val isInitialSetupComplete = it[IS_INITIAL_SETUP_COMPLETE]?:false
                if (isInitialSetupComplete){
                    launchMonitoringActivity()
                }else{
                    launchQuestionsActivity()
                }
            }
        }
    }
    private fun launchQuestionsActivity(){
        val intent = Intent(this, QuestionsActivity::class.java)
        startActivity(intent)
    }
    private fun launchMonitoringActivity(){
        val intent = Intent(this, MonitoringActivity::class.java)
        startActivity(intent)
    }
}