package com.superflix.app.ui.settings

import android.os.Bundle
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import com.superflix.app.R

class SettingsActivity : AppCompatActivity() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        
        val autoPlaySwitch = findViewById<SwitchCompat>(R.id.autoPlaySwitch)
        val subtitlesSwitch = findViewById<SwitchCompat>(R.id.subtitlesSwitch)
        val qualityGroup = findViewById<RadioGroup>(R.id.qualityGroup)
        
        autoPlaySwitch.setOnCheckedChangeListener { _, isChecked ->
            // Save preference
        }
        
        subtitlesSwitch.setOnCheckedChangeListener { _, isChecked ->
            // Save preference
        }
        
        qualityGroup.setOnCheckedChangeListener { _, checkedId ->
            // Save quality preference
        }
    }
}