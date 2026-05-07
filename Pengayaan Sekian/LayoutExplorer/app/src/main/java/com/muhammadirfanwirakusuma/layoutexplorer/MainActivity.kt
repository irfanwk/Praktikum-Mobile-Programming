package com.muhammadirfanwirakusuma.layoutexplorer

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.ComponentActivity

class MainActivity : ComponentActivity() {

    private var currentProgress = 0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val circleView = findViewById<CircleProgressView>(R.id.customCircleProgress)
        val btnAdd = findViewById<Button>(R.id.btnTambahProgress)
        val tvText = findViewById<TextView>(R.id.tvProgressText)

        btnAdd.setOnClickListener {
            if (currentProgress < 100) {
                currentProgress += 10f
            } else {
                currentProgress = 0f
            }

            circleView.setProgress(currentProgress)

            tvText.text = "Progress: ${currentProgress.toInt()}%"
        }
    }
}


