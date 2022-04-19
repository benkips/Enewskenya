package com.mabnets.e_newskenya.Utils

import android.app.PendingIntent
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.View
import androidx.navigation.NavDeepLinkBuilder
import com.google.android.material.snackbar.Snackbar
import com.mabnets.e_newskenya.Webactivity
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



fun getBitmapfromUrl(imageUrl: String?): Bitmap? {
    return try {
        val url = URL(imageUrl)
        BitmapFactory.decodeStream(url.openConnection().getInputStream())
    } catch (e: IOException) {
        System.out.println(e)
        null
    }
}

fun Context.gotomy(urlz:String?): PendingIntent {
//        navController.navigate(R.id.live)
    val bundle = Bundle()
    bundle.putString("url",urlz)
    val pendingintent= NavDeepLinkBuilder(this.applicationContext)
        .setComponentName(Webactivity::class.java)
        .setArguments(bundle)
        .createPendingIntent()
    return pendingintent
}
