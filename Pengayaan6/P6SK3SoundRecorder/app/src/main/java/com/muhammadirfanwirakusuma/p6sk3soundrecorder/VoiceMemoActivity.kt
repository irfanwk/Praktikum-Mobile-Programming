package com.muhammadirfanwirakusuma.p6sk3soundrecorder

import android.Manifest
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.io.File

class VoiceMemoActivity : ComponentActivity() {
    private var recorder: MediaRecorder? = null
    private var player: MediaPlayer? = null
    private var outputFile: String = ""
    private var isRecording = false

    companion object {
        const val REQUEST_MIC = 101
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_voice_memo)

        // Path output di external cache (tidak butuh izin WRITE_EXTERNAL_STORAGE)
        outputFile = "${externalCacheDir?.absolutePath}/rekaman_memo.mp4"

        val btnRekam = findViewById<Button>(R.id.btnRekam)
        val btnPutar = findViewById<Button>(R.id.btnPutar)
        val tvStatus = findViewById<TextView>(R.id.tvStatus)

        // Periksa dan minta izin mikrofon
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.RECORD_AUDIO), REQUEST_MIC)
        }

        btnRekam.setOnClickListener {
            if (!isRecording) {
                mulaiRekam()
                tvStatus.text = "Sedang merekam..."
                btnRekam.text = "Stop Rekam"
            } else {
                hentiRekam()
                tvStatus.text = "Rekaman selesai. Tekan Putar untuk mendengarkan."
                btnRekam.text = "Mulai Rekam"
            }
        }

        btnPutar.setOnClickListener {
            if (File(outputFile).exists()) {
                putarHasilRekaman()
            } else {
                Toast.makeText(this, "Belum ada rekaman.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun mulaiRekam() {
        recorder = MediaRecorder().apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
            setOutputFile(outputFile)
            setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
            setAudioSamplingRate(44100) // Kualitas CD
            setAudioEncodingBitRate(128000)
            prepare()
            start()
        }
        isRecording = true
    }

    private fun hentiRekam() {
        recorder?.apply {
            stop()
            release()
        }
        recorder = null
        isRecording = false
    }

    private fun putarHasilRekaman() {
        player?.release() // Hentikan player sebelumnya jika ada
        player = MediaPlayer().apply {
            setDataSource(outputFile)
            prepare()
            start()
            setOnCompletionListener { release() }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        recorder?.release()
        player?.release()
    }
}
