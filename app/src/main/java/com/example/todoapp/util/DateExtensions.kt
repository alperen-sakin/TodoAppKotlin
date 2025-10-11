package com.example.todoapp.util

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

@RequiresApi(Build.VERSION_CODES.O)
fun Long.toLocalDateTime(): LocalDateTime {
    val instant = Instant.ofEpochMilli(this)

    return LocalDateTime.ofInstant(instant, ZoneId.systemDefault())
}
