package com.muhammadirfanwirakusuma.p6sk4fusion

import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView
import androidx.fragment.app.Fragment

class PlayerFragment : Fragment() {
    private var mediaPlayer: MediaPlayer? = null
    private val handler = Handler(Looper.getMainLooper())
    private lateinit var seekBar: SeekBar
    private lateinit var tvCurrent: TextView
    private lateinit var tvDurasi: TextView
    private lateinit var tvErrorText: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_player, container, false)
        seekBar = view.findViewById(R.id.seekBar)
        tvCurrent = view.findViewById(R.id.tvCurrent)
        tvDurasi = view.findViewById(R.id.tvDurasi)
        tvErrorText = view.findViewById(R.id.tvErrorText)

        inisialisasiMediaPlayer()

        view.findViewById<Button>(R.id.btnPlay).setOnClickListener {
            mediaPlayer?.let { mp ->
                if (!mp.isPlaying) {
                    mp.start()
                    updateSeekBar()
                }
            } ?: run {
                // If it was failed to create previously due to placeholder
                tvErrorText.text = "MediaPlayer kosong. Pastikan res/raw/song.mp3 valid."
            }
        }

        view.findViewById<Button>(R.id.btnPause).setOnClickListener {
            mediaPlayer?.takeIf { it.isPlaying }?.pause()
        }

        view.findViewById<Button>(R.id.btnStop).setOnClickListener { 
            resetPlayer() 
        }

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

        return view
    }

    private fun inisialisasiMediaPlayer() {
        try {
            mediaPlayer = MediaPlayer.create(requireContext(), R.raw.song)
            mediaPlayer?.apply {
                setOnPreparedListener {
                    seekBar.max = duration
                    tvDurasi.text = formatWaktu(duration)
                }
                setOnCompletionListener { resetPlayer() }
            }
            if (mediaPlayer == null) {
                tvErrorText.text = "Gagal memuat song.mp3 (Mungkin file masih berupa text placeholder)"
            }
        } catch (e: Exception) {
            tvErrorText.text = "Exception: ${e.message}"
        }
    }

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

    private fun resetPlayer() {
        // Menggunakan pause dan seekTo(0) agar bisa di-play kembali tanpa recreate
        mediaPlayer?.apply { 
            if (isPlaying) pause()
            seekTo(0)
        }
        handler.removeCallbacksAndMessages(null)
        seekBar.progress = 0
        tvCurrent.text = "0:00"
    }

    private fun formatWaktu(ms: Int): String {
        val detik = (ms / 1000) % 60
        val menit = ms / 60000
        return "%d:%02d".format(menit, detik)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        handler.removeCallbacksAndMessages(null)
        mediaPlayer?.release()
        mediaPlayer = null
    }
}
