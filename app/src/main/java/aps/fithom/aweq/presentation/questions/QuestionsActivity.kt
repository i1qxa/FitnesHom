package aps.fithom.aweq.presentation.questions

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.FragmentOnAttachListener
import androidx.lifecycle.lifecycleScope
import aps.fithom.aweq.R
import aps.fithom.aweq.databinding.ActivityQuestionsBinding
import aps.fithom.aweq.domain.IS_INITIAL_SETUP_COMPLETE
import aps.fithom.aweq.domain.dataStore
import aps.fithom.aweq.domain.launchNewQuestion
import aps.fithom.aweq.domain.setCurrentWeight
import aps.fithom.aweq.presentation.monitoring.MonitoringActivity
import aps.fithom.aweq.presentation.questions.fragments.CurrentWeightFragment
import aps.fithom.aweq.presentation.questions.fragments.TargetCaloriesFragment
import aps.fithom.aweq.presentation.questions.fragments.TargetCarbsFragment
import aps.fithom.aweq.presentation.questions.fragments.TargetFatFragment
import aps.fithom.aweq.presentation.questions.fragments.TargetProteinFragment
import aps.fithom.aweq.presentation.questions.fragments.TargetWeightFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class QuestionsActivity : AppCompatActivity() {

    private val binding by lazy { ActivityQuestionsBinding.inflate(layoutInflater) }
    private val viewModel by viewModels<QuestionsViewModel>()
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
        supportFragmentManager.addFragmentOnAttachListener(questionsChangeListener)
        setupStartData()
        observeInitialComplete()
    }

    private fun observeInitialComplete(){
        lifecycleScope.launch {
            this@QuestionsActivity.dataStore.data.collect{
                if (it[IS_INITIAL_SETUP_COMPLETE] == true){
                    val intent = Intent(this@QuestionsActivity, MonitoringActivity::class.java)
                    launch(Dispatchers.Main) {
                        startActivity(intent)
                    }
                }
            }
        }
    }

    private fun setupStartData(){
        binding.tvQuestionName.text = getString(R.string.current_weight)
        binding.btnNext.setOnClickListener {
            supportFragmentManager.launchNewQuestion(TargetWeightFragment())
        }
    }


    private val questionsChangeListener = FragmentOnAttachListener { fragmentManager, fragment ->
        when (fragment) {
            is CurrentWeightFragment -> {
                setupQuestionName(getString(R.string.current_weight))
                binding.btnNext.setOnClickListener {
                    supportFragmentManager.launchNewQuestion(TargetWeightFragment())
                }
                binding.btnBack.visibility = View.INVISIBLE
            }

            is TargetWeightFragment -> {
                setupQuestionName(getString(R.string.target_weight))
                binding.btnNext.setOnClickListener {
                    supportFragmentManager.launchNewQuestion(TargetCaloriesFragment())
                }
                binding.btnBack.visibility = View.VISIBLE
                binding.btnBack.setOnClickListener {
                    supportFragmentManager.launchNewQuestion(CurrentWeightFragment())
                }
            }

            is TargetCaloriesFragment -> {
                setupQuestionName(getString(R.string.target_calories))
                binding.btnNext.setOnClickListener {
                    supportFragmentManager.launchNewQuestion(TargetProteinFragment())
                }
                binding.btnBack.visibility = View.VISIBLE
                binding.btnBack.setOnClickListener {
                    supportFragmentManager.launchNewQuestion(TargetWeightFragment())
                }
            }

            is TargetProteinFragment -> {
                setupQuestionName(getString(R.string.target_protein))
                binding.btnNext.setOnClickListener {
                    supportFragmentManager.launchNewQuestion(TargetFatFragment())
                }
                binding.btnBack.visibility = View.VISIBLE
                binding.btnBack.setOnClickListener {
                    supportFragmentManager.launchNewQuestion(TargetCaloriesFragment())
                }
            }

            is TargetFatFragment -> {
                setupQuestionName(getString(R.string.target_fat))
                binding.btnNext.setOnClickListener {
                    supportFragmentManager.launchNewQuestion(TargetCarbsFragment())
                }
                binding.btnBack.visibility = View.VISIBLE
                binding.btnBack.setOnClickListener {
                    supportFragmentManager.launchNewQuestion(TargetProteinFragment())
                }
            }

            else -> {
                setupQuestionName(getString(R.string.target_carbs))
                binding.btnNext.setOnClickListener {
                    viewModel.saveData()
                }
                binding.btnBack.visibility = View.VISIBLE
                binding.btnBack.setOnClickListener {
                    supportFragmentManager.launchNewQuestion(TargetFatFragment())
                }
            }
        }
    }

    private fun setupQuestionName(name:String){
        binding.tvQuestionName.text =name
    }

}