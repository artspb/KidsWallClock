package me.artspb.android.kids.wall.clock.preference

import android.os.Bundle
import android.text.format.DateFormat
import android.view.View
import android.widget.TimePicker
import androidx.preference.PreferenceDialogFragmentCompat
import me.artspb.android.kids.wall.clock.R
import me.artspb.android.kids.wall.clock.combine
import me.artspb.android.kids.wall.clock.split

class TimePreferenceDialogFragmentCompat(key: String?) : PreferenceDialogFragmentCompat() {

    init {
        val bundle = Bundle(1)
        bundle.putString(ARG_KEY, key)
        arguments = bundle
    }

    private var timePicker: TimePicker? = null

    override fun onBindDialogView(view: View) {
        super.onBindDialogView(view)
        val preference = preference
        if (preference is TimeDialogPreference) {
            val (hours, minutes) = preference.time.split()
            val picker = view.findViewById(R.id.time_picker) as TimePicker
            timePicker = picker
            picker.hour = hours
            picker.minute = minutes
            picker.setIs24HourView(DateFormat.is24HourFormat(context))
        }
    }

    override fun onDialogClosed(positiveResult: Boolean) {
        val picker = timePicker
        if (positiveResult && picker != null) {
            val time = combine(picker.hour, picker.minute)
            val preference = preference
            if (preference is TimeDialogPreference && preference.callChangeListener(time)) {
                preference.time = time
            }
        }
    }
}