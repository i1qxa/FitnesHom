package aps.fithom.aweq.presentation.monitoring.target

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import aps.fithom.aweq.R
import aps.fithom.aweq.databinding.FragmentTargetBinding
import aps.fithom.aweq.domain.NutrientsState
import aps.fithom.aweq.domain.TARGET_CALORIES_DS
import aps.fithom.aweq.domain.TARGET_CARB_DS
import aps.fithom.aweq.domain.TARGET_FAT_DS
import aps.fithom.aweq.domain.TARGET_PROTEIN_DS
import aps.fithom.aweq.domain.dataStore
import aps.fithom.aweq.domain.gone
import aps.fithom.aweq.domain.makeInvisible
import aps.fithom.aweq.domain.makeVisible
import aps.fithom.aweq.domain.setTargetCalories
import aps.fithom.aweq.domain.setTargetFat
import aps.fithom.aweq.domain.setTargetProtein
import kotlinx.coroutines.launch

class TargetFragment : Fragment() {


    private val viewModel: TargetViewModel by viewModels()
    private val binding by lazy { FragmentTargetBinding.inflate(layoutInflater) }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeDS()
        observeNutrientState()
    }

    private fun observeNutrientState() {
        viewModel.nutrientState.observe(viewLifecycleOwner) { state ->
            when (state) {
                null -> {
                    launchWatchMode()
                }

                NutrientsState.ENERGY -> {
                    launchWatchMode()
                    binding.tvEnergyValue.gone()
                    binding.etEnergyValue.makeVisible()
                    binding.btnEditEnergy.setImageResource(R.drawable.save)
                    binding.etEnergyValue.doOnTextChanged { text, start, before, count ->
                        viewModel.currentValue = text.toString().toInt()
                    }
                    binding.btnEditEnergy.setOnClickListener {
                        lifecycleScope.launch {
                            requireContext().setTargetCalories(viewModel.currentValue)
                            viewModel.currentValue = 0
                            viewModel.nutrientState.postValue(null)
                        }
                    }
                }

                NutrientsState.PROTEIN -> {
                    launchWatchMode()
                    binding.tvProteinValue.gone()
                    binding.etProteinValue.makeVisible()
                    binding.btnProteinHeight.setImageResource(R.drawable.save)
                    binding.etProteinValue.doOnTextChanged { text, start, before, count ->
                        viewModel.currentValue = text.toString().toInt()
                    }
                    binding.btnProteinHeight.setOnClickListener {
                        lifecycleScope.launch {
                            requireContext().setTargetProtein(viewModel.currentValue)
                            viewModel.currentValue = 0
                            viewModel.nutrientState.postValue(null)
                        }
                    }
                }

                NutrientsState.FAT -> {
                    launchWatchMode()
                    binding.tvFatValue.gone()
                    binding.etFatValue.makeVisible()
                    binding.btnEditFat.setImageResource(R.drawable.save)
                    binding.etFatValue.doOnTextChanged { text, start, before, count ->
                        viewModel.currentValue = text.toString().toInt()
                    }
                    binding.btnEditFat.setOnClickListener {
                        lifecycleScope.launch {
                            requireContext().setTargetFat(viewModel.currentValue)
                            viewModel.currentValue = 0
                            viewModel.nutrientState.postValue(null)
                        }
                    }
                }

                NutrientsState.CARBS -> {
                    launchWatchMode()
                    binding.tvCarbValue.gone()
                    binding.etCarbValue.makeVisible()
                    binding.btnEditCarb.setImageResource(R.drawable.save)
                    binding.etCarbValue.doOnTextChanged { text, start, before, count ->
                        viewModel.currentValue = text.toString().toInt()
                    }
                    binding.btnEditCarb.setOnClickListener {
                        lifecycleScope.launch {
                            requireContext().setTargetCalories(viewModel.currentValue)
                            viewModel.currentValue = 0
                            viewModel.nutrientState.postValue(null)
                        }
                    }
                }
            }
        }
    }

    private fun observeDS() {
        lifecycleScope.launch {
            requireContext().dataStore.data.collect {
                val energyDS = (it[TARGET_CALORIES_DS] ?: 0).toString()
                binding.tvEnergyValue.text = energyDS
                binding.etEnergyValue.setText(energyDS)
                val proteinDS = (it[TARGET_PROTEIN_DS] ?: 0).toString()
                binding.tvProteinValue.text = proteinDS
                binding.etProteinValue.setText(proteinDS)
                val fatDS = (it[TARGET_FAT_DS] ?: 0).toString()
                binding.tvFatValue.text = fatDS
                binding.etFatValue.setText(fatDS)
                val carbsDS = (it[TARGET_CARB_DS] ?: 0).toString()
                binding.tvCarbValue.text = carbsDS
                binding.etCarbValue.setText(carbsDS)
            }
        }

    }

    private fun launchWatchMode() {
        viewModel.currentValue = 0
        with(binding) {
            tvEnergyValue.makeVisible()
            etEnergyValue.gone()
            btnEditEnergy.setImageResource(R.drawable.edit)
            tvProteinValue.makeVisible()
            etProteinValue.gone()
            btnProteinHeight.setImageResource(R.drawable.edit)
            tvFatValue.makeVisible()
            etFatValue.gone()
            btnEditFat.setImageResource(R.drawable.edit)
            tvCarbValue.makeVisible()
            etCarbValue.gone()
            btnEditCarb.setImageResource(R.drawable.edit)
        }
        with(binding) {
            btnEditEnergy.setOnClickListener {
                viewModel.nutrientState.value = NutrientsState.ENERGY
            }
            btnProteinHeight.setOnClickListener {
                viewModel.nutrientState.value = NutrientsState.PROTEIN
            }
            btnEditFat.setOnClickListener {
                viewModel.nutrientState.value = NutrientsState.FAT
            }
            btnEditCarb.setOnClickListener {
                viewModel.nutrientState.value = NutrientsState.CARBS
            }
        }

    }

}