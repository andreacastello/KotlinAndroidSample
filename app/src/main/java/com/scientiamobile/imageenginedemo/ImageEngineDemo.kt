package com.scientiamobile.imageenginedemo

import android.content.Context
import android.graphics.Point
import android.net.ConnectivityManager
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.telephony.TelephonyManager
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso

class ImageEngineDemo : AppCompatActivity() {

    internal var v: ImageView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_image_engine_demo)

        // we detect the display width in pixel and set it as default value for the input text field
        val tv = findViewById(R.id.editText) as TextView
        val display = windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        tv.text = "" + size.x

        v = findViewById(R.id.imageView) as ImageView

        // We use plain ImageEngine call first, and let it detect the best size
        Picasso.with(this).load(BASE_IMAGE_ENGINE_URL + "/https://" + DEFAULT_IMAGE_URL).into(v)

        // The use a text field to enter the dimension of an image we want to use.
        // The send button
        val b = findViewById(R.id.button) as Button
        //Set an event listener on the "OK" button.
        b.setOnClickListener {
            // The input text field
            val tv = findViewById(R.id.editText) as TextView
            val iv = findViewById(R.id.imageView) as ImageView

            var w = ""
            if ("" != tv.text) {
                w = tv.text.toString()
            }

            var url = BASE_IMAGE_ENGINE_URL
            val networkType = networkType
            // If we are under a slow network, we request an image compression of 50
            if (networkType == TelephonyManager.NETWORK_TYPE_GPRS || networkType == TelephonyManager.NETWORK_TYPE_EDGE) {
                url += "/w_$w/cmpr_50/https://$DEFAULT_IMAGE_URL"
            } else {
                url += "/w_$w/https://$DEFAULT_IMAGE_URL"
            }
            Picasso.with(applicationContext).load(url).into(iv)
        }


    }

    /*
     * Detects the network subtype.
     * Subtype can be GPRS, EDGE, etc., while type is MOBILE/WIFI/WIMAX etc.
     */
    internal val networkType: Int
        get() {
            val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

            val ni = connectivityManager.activeNetworkInfo
            if (ni != null) {
                return ni.subtype
            }
            return -1
        }

    companion object {

        internal val BASE_IMAGE_ENGINE_URL = "http://try.imgeng.in"
        internal val DEFAULT_IMAGE_URL = "www.scientiamobile.com/page/wp-content/uploads/2017/02/HomePage-Demo-Woman-iPad-Redov3.png"
    }
}
