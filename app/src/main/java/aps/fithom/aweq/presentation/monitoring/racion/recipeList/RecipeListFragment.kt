package aps.fithom.aweq.presentation.monitoring.racion.recipeList

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import aps.fithom.aweq.R
import aps.fithom.aweq.data.remote.RecipeItemShort
import aps.fithom.aweq.databinding.FragmentRecipeListBinding
import aps.fithom.aweq.databinding.InputWeightDialogBinding
import aps.fithom.aweq.domain.State
import aps.fithom.aweq.domain.gone
import aps.fithom.aweq.domain.makeVisible
import aps.fithom.aweq.presentation.monitoring.racion.recipeList.rv.FoodRVAdapter

class RecipeListFragment : Fragment() {

    private val binding by lazy { FragmentRecipeListBinding.inflate(layoutInflater) }
    private val viewModel: RecipeListViewModel by viewModels()
    private val rvAdapter by lazy { FoodRVAdapter() }
    private val recyclerView by lazy { binding.rvRecipeList }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRV()
        observeRecipeList()
        setupBtnClickListeners()
        observeState()
    }

    private fun observeState(){
        viewModel.state.observe(viewLifecycleOwner){ state ->
            when(state){
                State.LOADING ->{
                    hideEverything()
                    binding.pbLoading.makeVisible()
                }
                State.START ->{
                    hideEverything()
                    binding.tvStart.makeVisible()
                }
                State.ERROR ->{
                    hideEverything()
                    binding.tvEmptyList.makeVisible()
                }
                State.COMPLETE ->{
                    hideEverything()
                    binding.rvRecipeList.makeVisible()
                }
                null ->{
                    hideEverything()
                    binding.tvEmptyList.makeVisible()
                }
            }
        }
    }

    private fun hideEverything(){
        with(binding){
            tvEmptyList.gone()
            tvStart.gone()
            pbLoading.gone()
            rvRecipeList.gone()
        }
    }

    private fun setupBtnClickListeners(){
        binding.btnBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
        binding.btnSearch.setOnClickListener {
            viewModel.translateQuery(binding.etSearch.text.toString())
        }
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
        rvAdapter.onBtnAddClickListener = { recipeItemShort ->
            showInputWeightDialog(recipeItemShort)
        }
        rvAdapter.onShoppingCardClickListener = { recipeItemShort, position ->
            viewModel.addIngredientsToShoppingList(recipeItemShort)
            recipeItemShort.inShoppingCard = true
            rvAdapter.notifyItemChanged(position)
        }
    }

    private fun showInputWeightDialog(foodItem: RecipeItemShort) {
        val dialogBinding = InputWeightDialogBinding.inflate(layoutInflater)
        val dialog = AlertDialog.Builder(requireContext()).apply {
            setView(dialogBinding.root)
        }.create()
        dialog.show()
        dialogBinding.btnCancel.setOnClickListener {
            dialog.dismiss()
        }
        dialogBinding.btnSubmit.setOnClickListener {
            val weightStr = dialogBinding.etWeight.text.toString().ifEmpty { "100" }
            val weight = weightStr.toInt()
            viewModel.addFoodItemToRacion(foodItem, weight)
            Toast.makeText(requireContext(),getString(R.string.toast_new_recipe, foodItem.label),
                Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }
    }

}