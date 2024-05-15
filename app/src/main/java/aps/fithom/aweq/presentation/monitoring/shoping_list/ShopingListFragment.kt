package aps.fithom.aweq.presentation.monitoring.shoping_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import aps.fithom.aweq.R
import aps.fithom.aweq.databinding.FragmentShopingListBinding
import aps.fithom.aweq.domain.gone
import aps.fithom.aweq.domain.makeVisible
import aps.fithom.aweq.presentation.monitoring.shoping_list.rv.IngredientRVAdapter

class ShopingListFragment : Fragment() {

    private val viewModel by viewModels<ShopingListViewModel>()
    private val binding by lazy { FragmentShopingListBinding.inflate(layoutInflater) }
    private val rv by lazy { binding.rvShoppingList }
    private val rvAdapter = IngredientRVAdapter()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRV()
        observeShoppingList()
    }

    private fun observeShoppingList(){
        viewModel.listOfIngredients.observe(viewLifecycleOwner){
            if (it.isNotEmpty()){
                binding.tvEmptyShoppingList.gone()
                rvAdapter.submitList(it)
                rv.makeVisible()
            }else{
                binding.tvEmptyShoppingList.makeVisible()
                rv.gone()
            }
        }
    }

    private fun setupAdapter(){
        rvAdapter.onCompleteClickListener = { ingredientDB ->
            viewModel.removeIngredient(ingredientDB)
        }
    }

    private fun setupRV(){
        setupAdapter()
        with(rv) {
            adapter = rvAdapter
            layoutManager = LinearLayoutManager(
                context,
                RecyclerView.VERTICAL,
                false
            )
        }
    }

}