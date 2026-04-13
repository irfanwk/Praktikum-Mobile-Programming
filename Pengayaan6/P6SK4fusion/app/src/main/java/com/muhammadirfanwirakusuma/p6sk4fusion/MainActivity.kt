package com.muhammadirfanwirakusuma.p6sk4fusion

import android.content.Context
import android.media.AudioManager
import android.os.Bundle
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var audioManager: AudioManager
    
    private lateinit var globalVolumeSeekBar: SeekBar
    private lateinit var bottomNav: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        globalVolumeSeekBar = findViewById(R.id.globalVolumeSeekBar)
        bottomNav = findViewById(R.id.bottom_navigation)

        // Setup Audio Manager for Global Volume
        audioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager
        val maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
        val currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)

        globalVolumeSeekBar.max = maxVolume
        globalVolumeSeekBar.progress = currentVolume

        globalVolumeSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0)
                }
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        // Setup Bottom Nav
        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_player -> loadFragment(PlayerFragment())
                R.id.nav_sound -> loadFragment(SoundFragment())
                R.id.nav_recorder -> loadFragment(RecorderFragment())
                else -> false
            }
        }

        // Load Default Fragment
        if (savedInstanceState == null) {
            bottomNav.selectedItemId = R.id.nav_player
        }
    }

    private fun loadFragment(fragment: Fragment): Boolean {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
        return true
    }
}