package aps.fithom.aweq.presentation.questions.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import aps.fithom.aweq.databinding.FragmentTargetProteinBinding
import aps.fithom.aweq.presentation.questions.QuestionsViewModel

class TargetProteinFragment : Fragment() {
    private val binding by lazy { FragmentTargetProteinBinding.inflate(layoutInflater) }
    private val viewModel by activityViewModels<QuestionsViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.etTargetProtein.doOnTextChanged{text, _, _, _ ->
            viewModel.targetProtein =
                text.toString().toInt()
        }
    }
}