package com.muhammadirfanwirakusuma.p6sk4fusion

import android.media.AudioAttributes
import android.media.SoundPool
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment

class SoundFragment : Fragment() {
    private lateinit var soundPool: SoundPool
    private var soundShoot = 0
    private var soundBoom = 0
    private var soundCoin = 0
    private var isLoaded = false
    private lateinit var tvErrorTextSound: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_sound, container, false)
        tvErrorTextSound = view.findViewById(R.id.tvErrorTextSound)

        val audioAttr = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_GAME)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build()

        soundPool = SoundPool.Builder()
            .setMaxStreams(6)
            .setAudioAttributes(audioAttr)
            .build()

        try {
            soundShoot = soundPool.load(requireContext(), R.raw.shoot, 1)
            soundBoom = soundPool.load(requireContext(), R.raw.boom, 1)
            soundCoin = soundPool.load(requireContext(), R.raw.coin, 1)
            
            soundPool.setOnLoadCompleteListener { _, sampleId, status ->
                if (status == 0) {
                    isLoaded = true // Anggap berhasil jika satupun loaded status = 0
                } else {
                    tvErrorTextSound.text = "Gagal memuat FX. Pastikan file raw valid (Bukan txt dummy)."
                }
            }
        } catch (e: Exception) {
            tvErrorTextSound.text = "Error load: ${e.message}"
        }

        view.findViewById<Button>(R.id.btnShoot).setOnClickListener {
            if (isLoaded) playSound(soundShoot)
            else tvErrorTextSound.text = "Sound belum terload atau invalid."
        }

        view.findViewById<Button>(R.id.btnBoom).setOnClickListener {
            if (isLoaded) playSound(soundBoom)
            else tvErrorTextSound.text = "Sound belum terload atau invalid."
        }

        view.findViewById<Button>(R.id.btnCoin).setOnClickListener {
            if (isLoaded) playSound(soundCoin)
            else tvErrorTextSound.text = "Sound belum terload atau invalid."
        }

        view.findViewById<Button>(R.id.btnSimultan).setOnClickListener {
            if (isLoaded) {
                playSound(soundShoot)
                playSound(soundBoom)
                playSound(soundCoin)
            } else {
                tvErrorTextSound.text = "Sound belum terload atau invalid."
            }
        }

        return view
    }

    private fun playSound(soundId: Int) {
        if(soundId != 0) {
            soundPool.play(soundId, 1.0f, 1.0f, 1, 0, 1.0f)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        soundPool.release()
    }
}
