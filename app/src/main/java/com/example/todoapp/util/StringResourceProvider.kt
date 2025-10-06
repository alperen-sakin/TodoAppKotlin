package com.example.todoapp.util

import androidx.annotation.StringRes

interface StringResourceProvider {
    fun getString(@StringRes resId: Int): String

    fun getString(@StringRes resId: Int, vararg formatArg: Any): String
}
