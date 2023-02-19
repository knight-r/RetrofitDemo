package com.example.retrofitdemo

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.squareup.picasso.Picasso

class ShowImage : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_image)
        val fullImageView = findViewById<ImageView>(R.id.iv_full_image)
        var bundle :Bundle ?=intent.extras
        var imageUrl = bundle!!.getString("image_url")
        Picasso.get().load(imageUrl).into(fullImageView)
    }
}