package aps.fithom.aweq.presentation.questions.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.datastore.dataStore
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import aps.fithom.aweq.R
import aps.fithom.aweq.databinding.FragmentCurrentWeightBinding
import aps.fithom.aweq.domain.CURRENT_WEIGHT_DS
import aps.fithom.aweq.domain.dataStore
import aps.fithom.aweq.domain.setCurrentWeight
import aps.fithom.aweq.presentation.questions.QuestionsViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class CurrentWeightFragment : Fragment() {

    private val binding by lazy { FragmentCurrentWeightBinding.inflate(layoutInflater) }
    private val viewModel by activityViewModels<QuestionsViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.etCurrentWeight.doOnTextChanged{text, _, _, _ ->
            viewModel.currentWeight =
                text.toString().toDouble()
        }
    }

}