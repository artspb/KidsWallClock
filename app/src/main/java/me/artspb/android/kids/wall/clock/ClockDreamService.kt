package me.artspb.android.kids.wall.clock

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.service.dreams.DreamService
import android.view.View
import android.widget.TextClock
import androidx.core.content.ContextCompat
import androidx.preference.PreferenceManager
import java.util.*

class ClockDreamService : DreamService() {

    private val clock: TextClock
        get() = findViewById(R.id.kids_wall_clock)

    private val broadcastReceiver: BroadcastReceiver by lazy {
        object : BroadcastReceiver() {
            override fun onReceive(ctx: Context, intent: Intent) {
                setColors()
            }
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        isInteractive = false
        isFullscreen = true
        setContentView(R.layout.clock_dream_service)
        setColors()
        clock.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LOW_PROFILE or
                    View.SYSTEM_UI_FLAG_FULLSCREEN or
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                    View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or
                    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
    }

    override fun onDreamingStarted() {
        super.onDreamingStarted()
        val filter = IntentFilter()
        filter.addAction(Intent.ACTION_TIME_TICK)
        filter.addAction(Intent.ACTION_TIME_CHANGED)
        filter.addAction(Intent.ACTION_TIMEZONE_CHANGED)
        registerReceiver(broadcastReceiver, filter)
    }

    private fun setColors() {
        val sharedPreferences = getSharedPreferences(packageName, Context.MODE_PRIVATE)
        val day = sharedPreferences.getString("day", null)
        if (day != null) {
            val editor = sharedPreferences.edit()
            editor.putString("day", null)
            editor.apply()
            setColors(Day.valueOf(day))
        } else {
            val preferences = PreferenceManager.getDefaultSharedPreferences(this)
            val start = preferences.getInt("beginning_of_day", 480).toDate()
            val end = preferences.getInt("end_of_day", 1260).toDate()
            setColors(Calendar.getInstance().toDay(start, end))
        }
    }

    private fun setColors(day: Day) {
        val preferences = PreferenceManager.getDefaultSharedPreferences(this)
        val (text, background) = when (day) {
            Day.NIGHT -> {
                val text = preferences.getInt(
                    "night_text_color",
                    ContextCompat.getColor(this, R.color.colorNightText)
                )
                val background = preferences.getInt(
                    "night_background_color",
                    ContextCompat.getColor(this, R.color.colorNightBackground)
                )
                Pair(text, background)
            }
            Day.DUSK -> {
                val text = preferences.getInt(
                    "dusk_text_color",
                    ContextCompat.getColor(this, R.color.colorDuskText)
                )
                val background = preferences.getInt(
                    "dusk_background_color",
                    ContextCompat.getColor(this, R.color.colorDuskBackground)
                )
                Pair(text, background)
            }
            Day.DAY -> {
                val text = preferences.getInt(
                    "day_text_color",
                    ContextCompat.getColor(this, R.color.colorDayText)
                )
                val background = preferences.getInt(
                    "day_background_color",
                    ContextCompat.getColor(this, R.color.colorDayBackground)
                )
                Pair(text, background)
            }
            Day.DAWN -> {
                val text = preferences.getInt(
                    "dawn_text_color",
                    ContextCompat.getColor(this, R.color.colorDawnText)
                )
                val background = preferences.getInt(
                    "dawn_background_color",
                    ContextCompat.getColor(this, R.color.colorDawnBackground)
                )
                Pair(text, background)
            }
        }
        clock.setTextColor(text)
        clock.setBackgroundColor(background)
    }

    override fun onDreamingStopped() {
        super.onDreamingStopped()
        unregisterReceiver(broadcastReceiver)
    }
}