package com.muhammadirfanwirakusuma.p6sk1pemutarmusiksederhana

import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.*
import android.app.Activity

class PlayerActivity : Activity() {
    private var mediaPlayer: MediaPlayer? = null
    private val handler = Handler(Looper.getMainLooper())
    private lateinit var seekBar: SeekBar
    private lateinit var tvCurrent: TextView
    private lateinit var tvDurasi: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)

        seekBar = findViewById(R.id.seekBar)
        tvCurrent = findViewById(R.id.tvCurrent)
        tvDurasi = findViewById(R.id.tvDurasi)

        // Inisialisasi MediaPlayer dari resource res/raw/annumfree.mp3
        mediaPlayer = MediaPlayer.create(this, R.raw.annumfree).apply {
            setOnPreparedListener {
                seekBar.max = duration
                tvDurasi.text = formatWaktu(duration)
            }
            setOnCompletionListener { resetPlayer() }
        }

        // Tombol Play
        findViewById<Button>(R.id.btnPlay).setOnClickListener {
            mediaPlayer?.let { mp ->
                if (!mp.isPlaying) {
                    mp.start()
                    updateSeekBar() // Mulai update seekbar
                }
            }
        }

        // Tombol Pause
        findViewById<Button>(R.id.btnPause).setOnClickListener {
            mediaPlayer?.takeIf { it.isPlaying }?.pause()
        }

        // Tombol Stop
        findViewById<Button>(R.id.btnStop).setOnClickListener { 
            resetPlayer() 
        }

        // SeekBar dragged by user
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(sb: SeekBar, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    mediaPlayer?.seekTo(progress)
                    tvCurrent.text = formatWaktu(progress)
                }
            }

            override fun onStartTrackingTouch(sb: SeekBar) {}

            override fun onStopTrackingTouch(sb: SeekBar) {}
        })
    }

    // Perbarui posisi SeekBar setiap 500ms
    private fun updateSeekBar() {
        handler.postDelayed({
            mediaPlayer?.let { mp ->
                if (mp.isPlaying) {
                    seekBar.progress = mp.currentPosition
                    tvCurrent.text = formatWaktu(mp.currentPosition)
                    updateSeekBar() // Rekursif
                }
            }
        }, 500)
    }

    // Reset ke posisi awal
    private fun resetPlayer() {
        mediaPlayer?.let { mp ->
            if (mp.isPlaying) {
                mp.pause() // Menggunakan pause agar mudah dimainkan kembali
            }
            mp.seekTo(0)
        }

        handler.removeCallbacksAndMessages(null)
        seekBar.progress = 0
        tvCurrent.text = "0:00"
    }

    // Konversi ms ke format m:ss
    private fun formatWaktu(ms: Int): String {
        val detik = (ms / 1000) % 60
        val menit = ms / 60000
        return "%d:%02d".format(menit, detik)
    }

    // Bebaskan resource saat Activity dihancurkan
    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacksAndMessages(null)
        mediaPlayer?.release()
        mediaPlayer = null
    }
}
