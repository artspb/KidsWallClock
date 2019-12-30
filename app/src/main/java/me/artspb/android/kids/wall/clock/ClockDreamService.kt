package me.artspb.android.kids.wall.clock

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.service.dreams.DreamService
import android.view.View
import android.widget.TextClock
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
        val preferences = PreferenceManager.getDefaultSharedPreferences(this)
        val start = preferences.getInt("beginning_of_day", 480).toDate()
        val end = preferences.getInt("end_of_day", 1260).toDate()
        setColors(Calendar.getInstance().toDay(start, end))
    }

    private fun setColors(day: Day) = when (day) {
        Day.NIGHT -> {
            clock.setTextColor(Color.parseColor("#060607"))
            clock.setBackgroundColor(Color.parseColor("#484e52"))
        }
        Day.DUSK -> {
            clock.setTextColor(Color.parseColor("#001244"))
            clock.setBackgroundColor(Color.parseColor("#005086"))
        }
        Day.DAY -> {
            clock.setTextColor(Color.parseColor("#475808"))
            clock.setBackgroundColor(Color.parseColor("#9ab031"))
        }
        Day.DAWN -> {
            clock.setTextColor(Color.parseColor("#dc8998"))
            clock.setBackgroundColor(Color.parseColor("#718ec4"))
        }
    }

    override fun onDreamingStopped() {
        super.onDreamingStopped()
        unregisterReceiver(broadcastReceiver)
    }
}