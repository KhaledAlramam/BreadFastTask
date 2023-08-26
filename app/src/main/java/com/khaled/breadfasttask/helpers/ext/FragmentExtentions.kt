package com.khaled.breadfasttask.helpers.ext

import android.widget.Toast
import androidx.fragment.app.Fragment

fun Fragment.showToast(msg: String){
    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
}

fun Fragment.showToast(msgResId: Int){
    Toast.makeText(context, getString(msgResId), Toast.LENGTH_SHORT).show()
}