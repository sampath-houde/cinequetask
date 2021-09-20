package com.task.cinqueuex.utils

import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import androidx.core.content.ContextCompat
import com.google.android.material.textfield.TextInputLayout
import com.task.cinqueuex.R

object Utils {
    const val BASE_URL = "https://api.cinquex.com/api/internshala/"
}

fun textWatcher(inputLayout: TextInputLayout): TextWatcher {
    return object : TextWatcher {
        override fun beforeTextChanged(
            s: CharSequence?,
            start: Int,
            count: Int,
            after: Int
        ) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

        override fun afterTextChanged(s: Editable?) {
            inputLayout.apply {
                error = null
                boxStrokeWidth = 1
                boxStrokeColor = ContextCompat.getColor(context, R.color.pink_500);
            }
        }

    }
}


fun showError(editTextLayout: TextInputLayout, message: String) {
    editTextLayout.apply {
        error = message
        setErrorIconDrawable(R.drawable.ic_baseline_error_24)
    }
}