package com.example.oneclock

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.oneclock.databinding.TimeZoneBinding
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

// Link for time zones: https://ozenero.com/kotlin-get-current-datetime
// https://developer.android.com/reference/kotlin/android/icu/util/TimeZone
// London, California, Denver, Chicago, Austin Texas

@RequiresApi(Build.VERSION_CODES.O)
class MainActivity : AppCompatActivity() {
    //Used to find the different available time zone IDs
    private val allZones = ZoneId.getAvailableZoneIds().sortedBy { it }.filter { it.contains("America/A") }

    private val easternTime: ZoneId = ZoneId.of("America/New_York")
    private val centralTime: ZoneId = ZoneId.of("America/Chicago")
    private val mountainTime: ZoneId = ZoneId.of("America/Denver")
    private val pacificTime: ZoneId = ZoneId.of("America/Los_Angeles")
    private val londonTime= ZoneId.of("Europe/London")


    private lateinit var binding: TimeZoneBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = TimeZoneBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /**
         * Button used to refresh time
         */
        binding.updateTimeButton.setOnClickListener { getCurrentTimes() }

        getCurrentTimes()
        Log.d("MainActivity", allZones.toString())
    }

    private fun getCurrentTimes() {
        getCurrentTime()
        getCurrentNewYorkTime()
        getCurrentChicagoTime()
        getCurrentDenverTime()
        getCurrentLosAngelesTime()
        getCurrentLondonTime()
        getCurrentTexasTime()
    }

    private fun getCurrentTime() {
        val currentTime = LocalDateTime.now()
        val formattedCurrentTime = currentTime.format(
            DateTimeFormatter
                .ofLocalizedTime(FormatStyle.SHORT)
        )
        binding.currentTime.text = getString(R.string.local_current_time) + "\n $formattedCurrentTime"
    }

    private fun getCurrentNewYorkTime() {
        val formattedTime = getCurrentZoneTime(easternTime)
        binding.currentTimeNewYork.text = getString(R.string.new_york) + "\n $formattedTime"
    }

    private fun getCurrentLosAngelesTime() {
        val formattedTime = getCurrentZoneTime(pacificTime)
        binding.currentTimeLosAngeles.text = getString(R.string.los_angeles) + "\n $formattedTime"
    }

    private fun getCurrentChicagoTime() {
        val formattedTime = getCurrentZoneTime(centralTime)
        binding.currentTimeChicago.text = getString(R.string.chicago) + "\n $formattedTime"
    }

    private fun getCurrentTexasTime() {
        val formattedTime = getCurrentZoneTime(centralTime)
        binding.currentTimeTexas.text = getString(R.string.texas) + "\n $formattedTime"
    }

    private fun getCurrentDenverTime() {
        val formattedTime = getCurrentZoneTime(mountainTime)
        binding.currentTimeDenver.text = getString(R.string.denver) + "\n $formattedTime"
    }

    private fun getCurrentLondonTime() {
        val formattedTime = getCurrentZoneTime(londonTime)
        binding.currentTimeLondon.text = getString(R.string.london) + "\n $formattedTime"
    }

    private fun getCurrentZoneTime(timeZone: ZoneId): String {
        val localCurrentTime = ZonedDateTime.now(timeZone)
        return localCurrentTime.format(
            DateTimeFormatter
                .ofLocalizedTime(FormatStyle.SHORT)
        )
    }
}