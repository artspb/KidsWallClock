package me.artspb.android.kids.wall.clock

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle

class ClockMainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sharedPreferences = getSharedPreferences(packageName, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("day", intent.getStringExtra("Day"))
        editor.apply()

        val intent = Intent(Intent.ACTION_MAIN)
        intent.setClassName("com.android.systemui", "com.android.systemui.Somnambulator")
        startActivity(intent)
        finish()
    }
}