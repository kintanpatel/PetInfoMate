package com.manageairproducts.petinfomate.ui.aboutus

import com.manageairproducts.petinfomate.databinding.FragmentAboutUsBinding

class AboutUsFragment : com.manageairproducts.petinfomate.base.BaseFragmentVB<FragmentAboutUsBinding>(FragmentAboutUsBinding::inflate) {
    override fun populateUI() {
        binding.btnShare.setOnClickListener {
            shareApp()
        }
    }


}