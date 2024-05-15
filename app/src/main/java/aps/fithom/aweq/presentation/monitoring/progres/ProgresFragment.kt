package aps.fithom.aweq.presentation.monitoring.progres

import android.annotation.SuppressLint
import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import aps.fithom.aweq.R
import aps.fithom.aweq.databinding.FragmentProgresBinding
import aps.fithom.aweq.domain.NutrientsState
import aps.fithom.aweq.domain.gone
import aps.fithom.aweq.domain.makeVisible
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartModel
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartType
import com.github.aachartmodel.aainfographics.aachartcreator.AASeriesElement

class ProgresFragment : Fragment() {

    private val viewModel: ProgresViewModel by viewModels()
    private val binding by lazy { FragmentProgresBinding.inflate(layoutInflater) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupBtnClickListeners()
        observeState()
        observeProgressData()
    }

    private fun observeProgressData(){
        viewModel.progressDataLD.observe(viewLifecycleOwner){ progressData ->
            if (progressData.isEmpty()){
                binding.tvNotEnouthData.makeVisible()
                binding.chart.gone()
            }else{
                binding.tvNotEnouthData.gone()
                binding.chart.makeVisible()
                val chartModel = AAChartModel()
                chartModel.apply {
                    chartType(AAChartType.Areaspline)
                    backgroundColor("#FFFFFF")
                    dataLabelsEnabled(true)
                    series(
                        arrayOf(
                            AASeriesElement()
                                .name("Дата")
                                .data(progressData.map { it.value }.toList().toTypedArray())
                        )
                    )
                    binding.chart.aa_drawChartWithChartModel(chartModel)
                }
            }
        }
    }

    private fun setupBtnClickListeners(){
        with(binding){
            energy.setOnClickListener {
                viewModel.stateLD.value = NutrientsState.ENERGY
            }
            protein.setOnClickListener {
                viewModel.stateLD.value = NutrientsState.PROTEIN
            }
            fat.setOnClickListener {
                viewModel.stateLD.value = NutrientsState.FAT
            }
            carb.setOnClickListener {
                viewModel.stateLD.value = NutrientsState.CARBS
            }
        }
    }
    private fun observeState(){
        viewModel.stateLD.observe(viewLifecycleOwner){
            makeBtnsInActive()
            when(it){
                NutrientsState.ENERGY ->{
                    makeBtnActive(binding.energy)
                    binding.tvChartName.text = requireContext().getString(R.string.energy)
                }
                NutrientsState.PROTEIN ->{
                    makeBtnActive(binding.protein)
                    binding.tvChartName.text = requireContext().getString(R.string.protein)
                }
                NutrientsState.CARBS ->{
                    makeBtnActive(binding.carb)
                    binding.tvChartName.text = requireContext().getString(R.string.carbs)
                }
                else ->{
                    makeBtnActive(binding.fat)
                    binding.tvChartName.text = requireContext().getString(R.string.fat)
                }
            }
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun makeBtnsInActive(){
        with(binding){
            energy.background = requireContext().getDrawable(R.drawable.button_inactive_background)
            energy.setTextColor(requireContext().resources.getColor(R.color.black))
            protein.background = requireContext().getDrawable(R.drawable.button_inactive_background)
            protein.setTextColor(requireContext().resources.getColor(R.color.black))
            fat.background = requireContext().getDrawable(R.drawable.button_inactive_background)
            fat.setTextColor(requireContext().resources.getColor(R.color.black))
            carb.background = requireContext().getDrawable(R.drawable.button_inactive_background)
            carb.setTextColor(requireContext().resources.getColor(R.color.black))
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun makeBtnActive(btn:TextView){
        btn.background = requireContext().getDrawable(R.drawable.button_background)
        btn.setTextColor(requireContext().resources.getColor(R.color.white))
    }

}