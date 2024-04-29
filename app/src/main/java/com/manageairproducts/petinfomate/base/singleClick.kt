package com.manageairproducts.petinfomate.base

import android.view.View

fun View.singleClick(view: (View) -> Unit) {
    setOnClickListener(view)
}