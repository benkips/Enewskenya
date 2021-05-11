package com.mabnets.e_newskenya.Utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import www.digitalexperts.church_tracker.Network.Resource
import java.io.IOException
import java.net.URL


fun View.snackbar(message: String, action: (() -> Unit)? = null) {
    val snackbar = Snackbar.make(this, message, Snackbar.LENGTH_SHORT)
    action?.let {
        snackbar.setAction("Retry") {
            it()
        }
    }
    snackbar.show()

}

fun View.visible(isVisible: Boolean) {
    visibility = if (isVisible) View.VISIBLE else View.GONE
}

fun Fragment.handleApiError(
    failure: Resource.Failure,
    retry: (() -> Unit)? = null
) {
    when {
        failure.isNetworkError -> requireView().snackbar("please check your internet", retry)
        else -> {
            val error = failure.errorBody?.string().toString()
            requireView().snackbar(error)
        }

    }
}

fun getBitmapfromUrl(imageUrl: String?): Bitmap? {
    return try {
        val url = URL(imageUrl)
        BitmapFactory.decodeStream(url.openConnection().getInputStream())
    } catch (e: IOException) {
        System.out.println(e)
        null
    }
}
