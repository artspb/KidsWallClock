package me.artspb.android.kids.wall.clock.preference

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.TypedArray
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import androidx.preference.Preference
import androidx.preference.PreferenceViewHolder
import com.flask.colorpicker.ColorPickerView
import com.flask.colorpicker.builder.ColorPickerDialogBuilder
import me.artspb.android.kids.wall.clock.R

class ColorPickerPreference @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.preferenceStyle,
    defStyleRes: Int = 0
) : Preference(context, attrs, defStyleAttr, defStyleRes) {

    private val positive: String
    private val negative: String

    private var colorBox: View? = null

    var color: Int = Color.BLACK
        set(value) {
            if (field != value) {
                field = value
                persistInt(value)
                notifyChanged()
            }
        }

    init {
        widgetLayoutResource = R.layout.color_picker_preference
        val a = context?.obtainStyledAttributes(
            attrs,
            R.styleable.DialogPreference,
            defStyleAttr,
            defStyleRes
        )
        @SuppressLint("PrivateResource")
        positive = a?.getString(R.styleable.DialogPreference_positiveButtonText) ?: ""
        @SuppressLint("PrivateResource")
        negative = a?.getString(R.styleable.DialogPreference_negativeButtonText) ?: ""
        a?.recycle()
    }

    override fun onGetDefaultValue(a: TypedArray, index: Int): Any = a.getInt(index, 0)

    override fun onSetInitialValue(defaultValue: Any?) {
        color = getPersistedInt(defaultValue as? Int ?: Color.BLACK)
    }

    override fun onBindViewHolder(holder: PreferenceViewHolder) {
        super.onBindViewHolder(holder)
        colorBox = holder.findViewById(R.id.colorpicker_preference_colorbox)
        colorBox?.setBackgroundColor(color)
    }

    override fun onClick() {
        super.onClick()
        ColorPickerDialogBuilder.with(context)
            .setTitle(title.toString())
            .initialColor(color)
            .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
            .density(12)
            .setPositiveButton(positive) { _, lastSelectedColor, _ ->
                color = lastSelectedColor
                colorBox?.setBackgroundColor(lastSelectedColor)
            }
            .setNegativeButton(negative) { dialogInterface, _ -> dialogInterface.dismiss() }
            .build()
            .show()
    }
}
