package me.artspb.android.kids.wall.clock

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import me.artspb.android.kids.wall.clock.preference.TimeDialogPreference
import me.artspb.android.kids.wall.clock.preference.TimePreferenceDialogFragmentCompat

class ClockSettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.clock_settings_activity)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.settings, SettingsFragment())
            .commit()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        android.R.id.home -> {
            super.onBackPressed()
            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }

    class SettingsFragment : PreferenceFragmentCompat() {

        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.clock_preferences, rootKey)
        }

        override fun onDisplayPreferenceDialog(preference: Preference) {
            if (preference is TimeDialogPreference) {
                val fragment = TimePreferenceDialogFragmentCompat(preference.key)
                fragment.setTargetFragment(this, 0)
                fragment.show(
                    this.requireFragmentManager(),
                    "androidx.preference.PreferenceFragment.DIALOG"
                )
            } else {
                super.onDisplayPreferenceDialog(preference)
            }
        }
    }
}