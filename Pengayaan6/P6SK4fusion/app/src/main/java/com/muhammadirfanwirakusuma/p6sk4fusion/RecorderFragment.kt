package com.muhammadirfanwirakusuma.p6sk4fusion

import android.Manifest
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import java.io.File

class RecorderFragment : Fragment() {
    private var recorder: MediaRecorder? = null
    private var player: MediaPlayer? = null
    private var outputFile: String = ""
    private var isRecording = false

    private lateinit var btnRekam: Button
    private lateinit var btnPutar: Button
    private lateinit var tvStatus: TextView

    companion object {
        const val REQUEST_MIC = 101
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_recorder, container, false)
        
        outputFile = "${requireContext().externalCacheDir?.absolutePath}/rekaman_memo.mp4"

        btnRekam = view.findViewById(R.id.btnRekam)
        btnPutar = view.findViewById(R.id.btnPutar)
        tvStatus = view.findViewById(R.id.tvStatus)

        // Periksa dan minta izin mikrofon di versi Android > M
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(Manifest.permission.RECORD_AUDIO), REQUEST_MIC)
        }

        btnRekam.setOnClickListener {
            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(requireContext(), "Izin Mikrofon belum diberikan", Toast.LENGTH_SHORT).show()
                requestPermissions(arrayOf(Manifest.permission.RECORD_AUDIO), REQUEST_MIC)
                return@setOnClickListener
            }

            if (!isRecording) {
                mulaiRekam()
            } else {
                hentiRekam()
            }
        }

        btnPutar.setOnClickListener {
            if (File(outputFile).exists()) {
                putarHasilRekaman()
            } else {
                Toast.makeText(requireContext(), "Belum ada rekaman.", Toast.LENGTH_SHORT).show()
            }
        }

        return view
    }

    private fun mulaiRekam() {
        try {
            recorder = MediaRecorder().apply {
                setAudioSource(MediaRecorder.AudioSource.MIC)
                setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
                setOutputFile(outputFile)
                setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
                setAudioSamplingRate(44100)
                setAudioEncodingBitRate(128000)
                prepare()
                start()
            }
            isRecording = true
            tvStatus.text = "Sedang merekam..."
            btnRekam.text = "Stop Rekam"
        } catch (e: Exception) {
            tvStatus.text = "Gagal merekam: ${e.message}"
        }
    }

    private fun hentiRekam() {
        try {
            recorder?.apply {
                stop()
                release()
            }
        } catch (e: Exception) {
            // Error jika stop dipanggil secara dini
        } finally {
            recorder = null
            isRecording = false
            tvStatus.text = "Rekaman selesai. Tekan Putar untuk mendengarkan."
            btnRekam.text = "Mulai Rekam"
        }
    }

    private fun putarHasilRekaman() {
        player?.release()
        try {
            player = MediaPlayer().apply {
                setDataSource(outputFile)
                prepare()
                start()
                setOnCompletionListener { release() }
            }
        } catch (e: Exception) {
            Toast.makeText(requireContext(), "Gagal memutar: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        recorder?.release()
        recorder = null
        player?.release()
        player = null
    }
}
