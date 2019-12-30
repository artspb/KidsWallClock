package me.artspb.android.kids.wall.clock.preference

import android.content.Context
import android.content.res.TypedArray
import android.text.format.DateFormat
import android.util.AttributeSet
import androidx.preference.DialogPreference
import me.artspb.android.kids.wall.clock.R
import me.artspb.android.kids.wall.clock.toDate

class TimeDialogPreference @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.preferenceStyle,
    defStyleRes: Int = 0
) : DialogPreference(context, attrs, defStyleAttr, defStyleRes) {

    init {
        summaryProvider = SimpleSummaryProvider.instance
    }

    var time: Int = 0
        set(value) {
            if (field != value) {
                field = value
                persistInt(value)
                notifyChanged()
            }
        }

    override fun onGetDefaultValue(a: TypedArray, index: Int): Any = a.getInt(index, 0)

    override fun getDialogLayoutResource(): Int = R.layout.time_dialog_preference

    override fun onSetInitialValue(defaultValue: Any?) {
        time = getPersistedInt(defaultValue as? Int ?: 0)
    }

    class SimpleSummaryProvider private constructor() : SummaryProvider<TimeDialogPreference> {

        companion object {
            val instance: SimpleSummaryProvider by lazy { SimpleSummaryProvider() }
        }

        override fun provideSummary(preference: TimeDialogPreference): String {
            val date = preference.time.toDate()
            val format = DateFormat.getTimeFormat(preference.context)
            return format.format(date.time)
        }
    }
}