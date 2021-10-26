package com.example.memeShare

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import kotlinx.android.synthetic.main.activity_main.*
import java.text.Collator.getInstance
import javax.crypto.Cipher.getInstance

class MainActivity : AppCompatActivity() {

    var currentImageUrl:String? =null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadMeme()
    }

  private fun loadMeme(){
      //val textView = findViewById<TextView>(R.id.text)

      // progressBar
      // Instantiate the RequestQueue.

        val url = "https://meme-api.herokuapp.com/gimme"

       // Request a string response from the provided URL.

          val jsonObjectRequest = JsonObjectRequest(
                  Request.Method.GET, url , null,
                  Response.Listener { response ->
                    currentImageUrl = response. getString("url")
                     Glide.with(this).load(currentImageUrl).listener(object: RequestListener<Drawable>{

                         override fun onLoadFailed(
                             e: GlideException?,
                             model: Any?,
                             target: Target<Drawable>?,
                             isFirstResource: Boolean
                         ): Boolean {
                             progressBar.visibility = View.GONE
                             return false
                         }

                         override fun onResourceReady(
                             resource: Drawable?,
                             model: Any?,
                             target: Target<Drawable>?,
                             dataSource: DataSource?,
                             isFirstResource: Boolean
                         ): Boolean {
                             progressBar.visibility =View.GONE
                             return false

                         }

                     }).into(memeImageView)
                  //Glide takes time Bcs 5kb ki image jldi download ho jayegi aur 5mb ki image time legi .
                  },
              Response.ErrorListener {
              Toast.makeText(this,"something Went Wrong!\n Check Your Internet Connection",Toast.LENGTH_LONG).show()
             })

       // Add the request to the RequestQueue.

      MySingleton.getInstance(this,).addToRequestQueue(jsonObjectRequest)
  }

    fun shareMeme(view: View) {

        val intent =Intent(Intent.ACTION_SEND)
        intent.type ="text/plain"
        intent.putExtra(Intent.EXTRA_TEXT,"Key , Checkout this cool Meme I got from Reedit $currentImageUrl")
        val chooser = Intent.createChooser(intent,"Share this Meme Using....")
        startActivity(chooser)
    }
    fun nextMeme(view: View) {

        loadMeme()
    }
}