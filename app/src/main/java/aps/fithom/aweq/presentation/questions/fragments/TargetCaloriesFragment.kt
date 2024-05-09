package aps.fithom.aweq.presentation.questions.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import aps.fithom.aweq.R
import aps.fithom.aweq.databinding.FragmentCurrentWeightBinding
import aps.fithom.aweq.databinding.FragmentTargetCaloriesBinding
import aps.fithom.aweq.domain.CURRENT_WEIGHT_DS
import aps.fithom.aweq.domain.TARGET_CALORIES_DS
import aps.fithom.aweq.domain.dataStore
import aps.fithom.aweq.presentation.questions.QuestionsViewModel
import kotlinx.coroutines.launch

class TargetCaloriesFragment : Fragment() {
    private val binding by lazy { FragmentTargetCaloriesBinding.inflate(layoutInflater) }
    private val viewModel by activityViewModels<QuestionsViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.etTargetCalories.doOnTextChanged{text, _, _, _ ->
            viewModel.targetCalories =
                text.toString().toInt()
        }
    }
}