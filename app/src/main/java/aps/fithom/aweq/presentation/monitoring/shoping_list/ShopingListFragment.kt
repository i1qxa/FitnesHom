package aps.fithom.aweq.presentation.monitoring.shoping_list

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import aps.fithom.aweq.R

class ShopingListFragment : Fragment() {

    companion object {
        fun newInstance() = ShopingListFragment()
    }

    private val viewModel: ShopingListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_shoping_list, container, false)
    }
}