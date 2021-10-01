package com.example.oneclock

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.oneclock.databinding.ActivityMainBinding
import com.example.oneclock.databinding.TimeZoneBinding
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
// Link for time zones: https://ozenero.com/kotlin-get-current-datetime
// https://developer.android.com/reference/kotlin/android/icu/util/TimeZone

class MainActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.O)
    val allZones = ZoneId.getAvailableZoneIds().filter { it.contains("America") }

    private lateinit var binding: TimeZoneBinding

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = TimeZoneBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /**
         * Button used to refresh time
         */
        binding.updateTimeButton.setOnClickListener { getCurrentTime() }

        getCurrentTime()
        Log.d("MainActivity", allZones.toString())
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getCurrentTime(){
        val newYorkZone = ZoneId.of("America/New_York")
        val localCurrentTime = ZonedDateTime.now(newYorkZone)
        val formattedTime = localCurrentTime.format(DateTimeFormatter
            .ofLocalizedTime(FormatStyle.SHORT))
        binding.currentTime.text = "Current Time: \n $formattedTime"
    }
}