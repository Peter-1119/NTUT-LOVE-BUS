package com.example.finalproject

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.ImageView


class ImageDialog(context: Context): Dialog(context){
    private var image: Int?= null
    fun setImage(image: Int?): ImageDialog {
        this.image = image
        return this
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.image_dialog)

        val largeImage = findViewById<ImageView>(R.id.large_image)
        image?.let {
            largeImage.setImageResource(it)
        }
        largeImage.setOnClickListener{
            this.cancel()
        }
    }
}