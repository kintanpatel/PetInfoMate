package com.manageairproducts.petinfomate.ui.detail

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.manageairproducts.petinfomate.R
import com.manageairproducts.petinfomate.data.database.AnimalEntity
import com.manageairproducts.petinfomate.data.database.AnimalRepo
import com.manageairproducts.petinfomate.data.database.AppDatabase
import com.manageairproducts.petinfomate.databinding.FragmentAnimalDetailBinding

class AnimalDetailFragment :
    com.manageairproducts.petinfomate.base.BaseFragmentVB<FragmentAnimalDetailBinding>(FragmentAnimalDetailBinding::inflate) {
    private var isFromDb = false
    private val arg: AnimalDetailFragmentArgs by navArgs()


    override fun populateUI() {
        isFromDb = arg.isFromDb
        val repository =
            AnimalRepo(AppDatabase.getInstance(requireActivity().applicationContext).animalDia)
        binding.btnShare.setOnClickListener {
            val textToShare = getShareText()
            val sendIntent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, textToShare)
                type = "text/plain"
            }

            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)
        }
        updateButton()

        binding.btnFav.setOnClickListener {
            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle("Favourite")
                .setMessage(if (isFromDb.not()) "Item marked as Favourite" else "Item Removed from Favorite")
                .setPositiveButton("OK") { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
            if (isFromDb) {
                mViewModel.animalEntity?.let {
                    mViewModel.delete(repository, it)
                }
            } else {
                mViewModel.selectedItem?.let { im ->
                    mViewModel.insert(repository, AnimalEntity().apply {
                        data = im.toEntity()
                    })
                }
            }
            isFromDb = isFromDb.not()
            updateButton()
        }

        mViewModel.selectedItem?.let { a ->
            with(binding) {
                lblName.text = a.name
                lblSlogan.text = "Slogan : ${a.characteristics.slogan ?: "NA"}"
                lblTopSpeed.text = "TopSpeed : ${a.characteristics.topSpeed ?: "NA"}"
                lblLifeSpan.text = "LifeSpan : ${a.characteristics.lifespan ?: "NA"}"
                lblWeight.text = "Weight : ${a.characteristics.weight ?: "NA"}"
                lblSkinType.text = "SkinType : ${a.characteristics.skinType ?: "NA"}"
                lblColor.text = "Color : ${a.characteristics.color ?: "NA"}"
                lblLocation.text = "Location : ${a.locations.joinToString()}"
                lblHabbit.text = "Habit : ${a.characteristics.habitat ?: "NA"}"
                lblDiet.text = "Diet : ${a.characteristics.diet ?: "NA"}"
                lblLifeStyle.text = "LifeStyle : ${a.characteristics.lifestyle ?: "NA"}"
                lblFavFood.text = "FavFood : ${a.characteristics.favoriteFood ?: "NA"}"
                lblMainPrey.text = "MainPrey : ${a.characteristics.mainPrey ?: "NA"}"
            }
        }
    }

    private fun updateButton() {
        if (isFromDb) {
            binding.btnFav.setText("Remove from Fav.")
        } else {
            binding.btnFav.setText(getString(R.string.favourite))
        }
    }

    private fun getShareText(): String {
        var shareText = ""
        with(binding) {
            val list: List<TextView> = listOf(
                lblName, lblSlogan, lblTopSpeed, lblLifeSpan, lblWeight, lblSkinType,
                lblColor, lblLocation, lblHabbit, lblDiet, lblLifeStyle, lblFavFood, lblMainPrey
            )
            list.forEach { item ->
                shareText += "${item.text}\n"
            }
        }
        return shareText
    }


}

fun Fragment.hideKeyboard() {
    view?.let { activity?.hideKeyboard(it) }
}

fun Activity.hideKeyboard() {
    hideKeyboard(currentFocus ?: View(this))
}

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}