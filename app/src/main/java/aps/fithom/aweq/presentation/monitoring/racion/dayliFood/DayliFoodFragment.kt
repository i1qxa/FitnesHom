package aps.fithom.aweq.presentation.monitoring.racion.dayliFood

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import aps.fithom.aweq.databinding.FragmentDayliFoodBinding
import aps.fithom.aweq.domain.Nutrients
import aps.fithom.aweq.domain.TARGET_CALORIES_DS
import aps.fithom.aweq.domain.TARGET_CARB_DS
import aps.fithom.aweq.domain.TARGET_FAT_DS
import aps.fithom.aweq.domain.TARGET_PROTEIN_DS
import aps.fithom.aweq.domain.dataStore
import aps.fithom.aweq.domain.launchNewFragment
import aps.fithom.aweq.presentation.monitoring.racion.dayliFood.rv.RecipeRVAdapter
import aps.fithom.aweq.presentation.monitoring.racion.recipeList.RecipeListFragment
import aps.fithom.aweq.presentation.monitoring.racion.recipeList.rv.FoodRVAdapter
import kotlinx.coroutines.launch

class DayliFoodFragment : Fragment() {

    private val binding by lazy { FragmentDayliFoodBinding.inflate(layoutInflater) }
    private val viewModel: DayliFoodViewModel by viewModels()
    private val rvAdapter by lazy { RecipeRVAdapter() }
    private val recyclerView by lazy { binding.rvRecipeList }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupBtnAddClickListener()
        fetchTargetValues()
        fetchCurrentNutrients()
        observeRecipeList()
    }

    private fun observeRecipeList(){
        viewModel.recipeList.observe(viewLifecycleOwner){
            rvAdapter.submitList(it)
        }
    }

    private fun setupRV() {
        setupRVAdapter()
        with(recyclerView) {
            adapter = rvAdapter
            layoutManager = LinearLayoutManager(
                context,
                RecyclerView.VERTICAL,
                false
            )
        }
    }

    private fun setupRVAdapter(){
//        rvAdapter.onBtnAddClickListener = { recipeItemShort ->
//            showInputWeightDialog(recipeItemShort)
//        }
    }

    private fun setupBtnAddClickListener(){
        binding.btnAddNewRecipe.setOnClickListener {
            parentFragmentManager.launchNewFragment(RecipeListFragment())
        }
    }

    private fun fetchCurrentNutrients(){
        viewModel.currentNutrients.observe(viewLifecycleOwner){
            with(binding){
                tvEnergyCurrent.text=it.energy.toString()
                pbEnergy.setProgress(it.energy, true)
                tvProteinCurrent.text=it.protein.toString()
                pbProtein.setProgress(it.protein,true)
                tvCarbCurrent.text=it.carbs.toString()
                pbCarbs.setProgress(it.carbs,true)
                tvFatCurrent.text=it.fat.toString()
                pbFat.setProgress(it.fat, true)
            }
        }
    }
    private fun fetchTargetValues(){
        lifecycleScope.launch {
            requireContext().dataStore.data.collect{
                val targetNutrients = Nutrients(
                    it[TARGET_CALORIES_DS]?:0,
                    it[TARGET_PROTEIN_DS]?:0,
                    it[TARGET_CARB_DS]?:0,
                    it[TARGET_FAT_DS]?:0
                )
                with(binding){
                    tvEnergyTarget.text = targetNutrients.energy.toString()
                    pbEnergy.max = targetNutrients.energy
                    tvProteinTarget.text = targetNutrients.protein.toString()
                    pbProtein.max = targetNutrients.protein
                    tvCarbTarget.text = targetNutrients.carbs.toString()
                    pbCarbs.max = targetNutrients.carbs
                    tvFatTarget.text = targetNutrients.fat.toString()
                    pbFat.max = targetNutrients.fat
                }
            }
        }

    }

}