package com.example.herbario_nacional.adapters

import android.widget.Spinner
import androidx.databinding.BindingAdapter
import com.example.herbario_nacional.adapters.SpinnerExtensions.setSpinnerEntries
import com.example.herbario_nacional.adapters.SpinnerExtensions.setSpinnerItemSelectedListener
import com.example.herbario_nacional.adapters.SpinnerExtensions.setSpinnerValue

class SpinnerBindings {

    @BindingAdapter("entries")
    fun Spinner.setEntries(entries: List<Any>?) {
        setSpinnerEntries(entries)
    }

    @BindingAdapter("onItemSelected")
    fun Spinner.setItemSelectedListener(itemSelectedListener: SpinnerExtensions.ItemSelectedListener?) {
        setSpinnerItemSelectedListener(itemSelectedListener)
    }

    @BindingAdapter("newValue")
    fun Spinner.setNewValue(newValue: Any?) {
        setSpinnerValue(newValue)
    }
}