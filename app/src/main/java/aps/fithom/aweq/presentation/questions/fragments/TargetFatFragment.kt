package aps.fithom.aweq.presentation.questions.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.activityViewModels
import aps.fithom.aweq.R
import aps.fithom.aweq.databinding.FragmentTargetFatBinding
import aps.fithom.aweq.databinding.FragmentTargetProteinBinding
import aps.fithom.aweq.presentation.questions.QuestionsViewModel

class TargetFatFragment : Fragment() {
    private val binding by lazy { FragmentTargetFatBinding.inflate(layoutInflater) }
    private val viewModel by activityViewModels<QuestionsViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.etTargetFat.doOnTextChanged{text, _, _, _ ->
            viewModel.targetFat =
                text.toString().toInt()
        }
    }
}