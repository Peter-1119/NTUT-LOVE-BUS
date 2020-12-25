package com.example.finalproject

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import kotlinx.android.synthetic.main.activity_main.*
import pl.droidsonroids.gif.GifDrawable
import pl.droidsonroids.gif.GifImageView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btn_map.setOnClickListener{
            var intent = Intent(this, search_bus::class.java)
            startActivity(intent)
        }
        val translationY = ObjectAnimator.ofFloat(findViewById(R.id.textView2), "translationY", 100f, 100f)
        val ra = ObjectAnimator.ofFloat(findViewById(R.id.textView2), "rotation", 0f, 720.0f)
        val animatorSet = AnimatorSet() //组合动画
        animatorSet.playTogether(translationY, ra) //设置动画
        animatorSet.duration = 2000
        animatorSet.start()
        val ImageView = findViewById<GifImageView>(R.id.imageView7)
        try {
            val gifDrawable = GifDrawable(resources, R.drawable.test2)
            ImageView.setImageDrawable(gifDrawable)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }
}