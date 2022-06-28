package com.rainy.test.ui


import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ScrollView
import androidx.appcompat.app.AppCompatActivity

/**
 * @author jhy
 * @description 简易的魔板
 * @date 2021/10/9
 */
abstract class SampleAct : AppCompatActivity() {

    lateinit var content: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        content = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
        }
        setContentView(ScrollView(this).apply {
            addView(content)
        })
        onCreate()
    }

    abstract fun onCreate()

    fun addClick(info: String, view: ((Button) -> Unit)? = null, click: () -> Unit) {
        content.addView(Button(this).apply {
            text = info
            setOnClickListener {
                click()
            }
            view?.invoke(this)
        })
    }

    fun addClick(info: String, click: () -> Unit) {
        content.addView(Button(this).apply {
            text = info
            setOnClickListener {
                click()
            }
        })
    }

    open fun startIntent(act: Class<*>) {
        startActivity(Intent(this, act))
    }
}