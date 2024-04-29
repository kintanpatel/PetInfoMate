package com.manageairproducts.petinfomate.base

import java.text.SimpleDateFormat
import java.util.Locale

fun Long.toUserFormat(): String {
    return SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(this)
}