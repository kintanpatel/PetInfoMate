package com.manageairproducts.petinfomate.ui.fav

import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.manageairproducts.petinfomate.base.safeNavigate
import com.manageairproducts.petinfomate.data.database.AnimalEntity
import com.manageairproducts.petinfomate.data.database.AnimalRepo
import com.manageairproducts.petinfomate.data.database.AppDatabase
import com.manageairproducts.petinfomate.databinding.FragmentFavroriteBinding
import com.manageairproducts.petinfomate.model.Animal
import com.manageairproducts.petinfomate.ui.dashboard.HomeAdapter

class FavFragment : com.manageairproducts.petinfomate.base.BaseFragmentVB<FragmentFavroriteBinding>(FragmentFavroriteBinding::inflate) {
    private var listOfData: ArrayList<Pair<AnimalEntity, Animal>> = arrayListOf()
    override fun populateUI() {
        binding.lottieAnimationView.playAnimation()
        val adapter = HomeAdapter()
        binding.rvResult.adapter = adapter
        adapter.onItemClick = { position, data ->
            mViewModel.selectedItem = data
            mViewModel.animalEntity = listOfData[position].first
            findNavController().safeNavigate(
                FavFragmentDirections.actionNavigationFavToAnimalDetailFragment().apply {
                    isFromDb = true
                })
        }
        val repository =
            AnimalRepo(AppDatabase.getInstance(requireActivity().applicationContext).animalDia)
        mViewModel.getAll(repository)
        mViewModel.items.observe(viewLifecycleOwner) {
            it.forEach { item ->
                listOfData.add(Pair(item, item.toAnimal()))
            }
            adapter.addAll(listOfData.map { it.second })
            binding.lottieAnimationView.isVisible = it.isEmpty()
        }
    }
}