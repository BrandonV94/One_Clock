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
import java.util.*
import java.util.Locale.getDefault

// Link for time zones: https://ozenero.com/kotlin-get-current-datetime
// https://developer.android.com/reference/kotlin/android/icu/util/TimeZone
// Main cities:        New York, London, LA California, Denver Colorado, Chicago Illinois, Austin Texas
// UTC times:          UTC-5,    UTC+0,  UTC-7,         UTC-7,           UTC-6,            UTC-6
// UTC Daylight Times: UTC-4,    UTC+1,  UTC-6,         UTC-6,           UTC-5,            UTC-5

@RequiresApi(Build.VERSION_CODES.O)
class MainActivity : AppCompatActivity() {
    //Used to find the different available time zone IDs
    private val allZones = ZoneId.getAvailableZoneIds().sortedBy { it }.filter { it.contains("GMT") }
    // Used to display the users current time zone.
    private val yourTimeZone = TimeZone.getDefault().getDisplayName(false, TimeZone.SHORT)

    val isDayLightSaving: Boolean = TimeZone.getTimeZone("America/Los_Angeles").inDaylightTime(Date())

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
         * Button used to update all the available times.
         */
        binding.updateTimeButton.setOnClickListener { getCurrentTimes() }

        // Makes sure that the times are displayed when the app is opened the first time
        getCurrentTimes()

        /**
         * Logs used to test out different Time Zone methods
         */
        //Log.d("MainActivity", allZones.toString())
        Log.d("MainActivity", "The local current time is " + LocalDateTime.now().toString())
        Log.d("MainActivity", "Daylight Saving Time: $isDayLightSaving")
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

    /**
     * Method used to get the current time from the users device.
     */
    private fun getCurrentTime() {
        val currentTime = LocalDateTime.now()
        val formattedCurrentTime = currentTime.format(
            DateTimeFormatter
                .ofLocalizedTime(FormatStyle.SHORT)
        )
        binding.currentTime.text = getString(R.string.local_current_time) + " ($yourTimeZone):\n $formattedCurrentTime"
    }

    // Determines the current formatted time of a specific time zone
    private fun getCurrentZoneTime(timeZone: ZoneId): String {
        val localCurrentTime = ZonedDateTime.now(timeZone)
        return localCurrentTime.format(
            DateTimeFormatter
                .ofLocalizedTime(FormatStyle.SHORT)
        )
    }

    /**
     * Methods used to update the different times displayed in the app.
     */
    private fun getCurrentNewYorkTime() {
        val formattedTime = getCurrentZoneTime(easternTime)
        binding.currentTimeNewYork.text = getString(R.string.new_york) + "\n $formattedTime"
    }

    private fun getCurrentLosAngelesTime() {
        val formattedTime = getCurrentZoneTime(pacificTime)
        val codeTz = TimeZone.getTimeZone(pacificTime).getDisplayName(false, TimeZone.SHORT)
        Log.d("MainActivity", "This is LA's time zone: $codeTz")
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
}