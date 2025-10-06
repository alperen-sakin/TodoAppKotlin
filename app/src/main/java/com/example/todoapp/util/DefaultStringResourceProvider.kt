package com.example.todoapp.util

import android.content.Context
import androidx.annotation.StringRes
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class DefaultStringResourceProvider @Inject constructor(
    @ApplicationContext private val context: Context
) : StringResourceProvider {

    /**
     * @StringRes ID'sini kullanarak String kaynağını döndürür.
     */
    override fun getString(@StringRes resId: Int): String {
        return context.getString(resId)
    }

    /**
     * String kaynağını format argümanları ile döndürür (Örn: "En az %d karakter olmalı").
     */
    override fun getString(@StringRes resId: Int, vararg formatArgs: Any): String {
        // String formatlamak için *formatArgs kullanılıyor
        return context.getString(resId, *formatArgs)
    }
}
