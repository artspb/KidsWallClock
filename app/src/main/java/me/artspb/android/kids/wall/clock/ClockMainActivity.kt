package me.artspb.android.kids.wall.clock

import android.app.Activity
import android.content.Intent
import android.os.Bundle

class ClockMainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val intent = Intent(Intent.ACTION_MAIN)
        intent.setClassName("com.android.systemui", "com.android.systemui.Somnambulator")
        startActivity(intent)
        finish()
    }
}