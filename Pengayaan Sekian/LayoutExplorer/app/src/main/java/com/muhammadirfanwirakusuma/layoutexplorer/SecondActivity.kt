package com.muhammadirfanwirakusuma.layoutexplorer

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.ComponentActivity

class SecondActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        val tvReceivedData = findViewById<TextView>(R.id.tvReceivedData)
        val btnKembali = findViewById<Button>(R.id.btnKembali)

        // Mengambil data dari Intent
        val data = intent.getStringExtra("EXTRA_MESSAGE")
        tvReceivedData.text = data

        // Tombol kembali untuk menutup activity
        btnKembali.setOnClickListener {
            finish()
        }
    }
}