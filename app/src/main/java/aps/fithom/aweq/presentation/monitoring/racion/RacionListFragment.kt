package aps.fithom.aweq.presentation.monitoring.racion

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import aps.fithom.aweq.R

class RacionListFragment : Fragment() {

    companion object {
        fun newInstance() = RacionListFragment()
    }

    private val viewModel: RacionListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_racion_list, container, false)
    }
}