package com.manageairproducts.petinfomate.ui.dashboard

import android.content.res.ColorStateList
import android.graphics.Color
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.manageairproducts.petinfomate.R
import com.manageairproducts.petinfomate.base.safeNavigate
import com.manageairproducts.petinfomate.databinding.FragmentDashboardBinding
import com.manageairproducts.petinfomate.databinding.ItemAnimalBinding
import com.manageairproducts.petinfomate.model.Animal
import com.manageairproducts.petinfomate.ui.detail.hideKeyboard
import com.wecare.vehicleinfomate.base.BaseRecyclerAdapter
import kotlin.random.Random

class DashboardFragment :
    com.manageairproducts.petinfomate.base.BaseFragmentVB<FragmentDashboardBinding>(FragmentDashboardBinding::inflate) {
    override fun populateUI() {
        val adapter = HomeAdapter()
        binding.rvResult.adapter = adapter

        adapter.onItemClick = { _, item ->
            mViewModel.selectedItem = item
            findNavController().safeNavigate(R.id.action_navigation_home_to_animalDetailFragment)
        }
        binding.btnSearch.setOnClickListener {
            if (binding.edSearch.text.isNullOrBlank().not()) {
                binding.lottieAnimationView.isVisible = true
                binding.lottieAnimationView.playAnimation()
                hideKeyboard()
                mViewModel.fetchPetAnimalData(binding.edSearch.text.toString())
            }
        }

        binding.btnDelete.setOnClickListener {
            binding.edSearch.text = null
        }

        mViewModel.animalData.observe(viewLifecycleOwner) {
            it.let { result ->
                binding.lottieAnimationView.isVisible = false
                binding.lottieAnimationView.pauseAnimation()
                when (result) {
                    is com.manageairproducts.petinfomate.base.Result.Error -> {
                        Toast.makeText(requireContext(), result.message, Toast.LENGTH_SHORT).show()
                    }

                    is com.manageairproducts.petinfomate.base.Result.Success -> {
                        adapter.addAll(result.data)
                    }

                    else -> {}

                }

            }
        }
    }

}

class HomeAdapter : BaseRecyclerAdapter<Animal, ItemAnimalBinding>(ItemAnimalBinding::inflate) {
    override fun populateItem(binding: ItemAnimalBinding, item: Animal, position: Int) {
        with(binding) {
            tvName.text = item.name
            tvChar.text = item.name.first().toString()
            tvDetail.text = "LifeSpan :".plus(item.characteristics.lifespan.orEmpty())
            tvDetail1.text = "Weight :".plus(item.characteristics.weight.orEmpty())
            ivImage.backgroundTintList = ColorStateList.valueOf(randomHighLuminanceColor())
        }
    }
}


fun randomColor(): Int {
    val red = Random.nextFloat()
    val green = Random.nextFloat()
    val blue = Random.nextFloat()

    return Color.rgb((red * 255).toInt(), (green * 255).toInt(), (blue * 255).toInt())
}

fun randomHighLuminanceColor(): Int {
    var red: Float
    var green: Float
    var blue: Float

    do {
        red = (Random.nextFloat() * 0.3 + 0.7).toFloat() // Adjust the range as needed
        green = (Random.nextFloat() * 0.3 + 0.7).toFloat()
        blue = (Random.nextFloat() * 0.3 + 0.7).toFloat()
    } while (red * 0.299 + green * 0.587 + blue * 0.114 <= 0.5) // Ensure high luminance

    return Color.rgb((red * 255).toInt(), (green * 255).toInt(), (blue * 255).toInt())
}