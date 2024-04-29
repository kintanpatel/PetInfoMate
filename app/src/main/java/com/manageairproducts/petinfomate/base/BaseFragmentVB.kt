package com.manageairproducts.petinfomate.base

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.viewbinding.ViewBinding
import com.manageairproducts.petinfomate.viewModel.MainViewModel


abstract class BaseFragmentVB<VB : ViewBinding>(private val inflate: (LayoutInflater, ViewGroup?, Boolean) -> VB) :
    Fragment() {

    val mViewModel by activityViewModels<MainViewModel>()
    private var _binding: VB? = null
    val binding
        get() = _binding
            ?: throw IllegalStateException("Trying to access the binding outside of the view lifecycle.")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = inflate.invoke(inflater, container, false)
        viewLifecycleOwner.lifecycle.addObserver(object : DefaultLifecycleObserver {
            override fun onCreate(owner: LifecycleOwner) {
                super.onCreate(owner)
                populateUI()
            }
        })
        return binding.root
    }


    protected abstract fun populateUI()

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun shareApp() {
        // Create an Intent to share text
        val sharingIntent = Intent(Intent.ACTION_SEND)
        sharingIntent.type = "text/plain"

        // Replace "YOUR_APP_PACKAGE_NAME" with your app's package name
        val playStoreLink =
            "https://play.google.com/store/apps/details?id=${requireActivity().packageName}"

        val shareBody = "Check out our app on the Play Store:\n$playStoreLink"
        sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody)

        // Start the sharing activity
        startActivity(Intent.createChooser(sharingIntent, "Share via"))
    }

}
