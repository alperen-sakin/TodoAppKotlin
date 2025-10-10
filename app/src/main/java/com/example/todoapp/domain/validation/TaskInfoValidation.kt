package com.example.todoapp.domain.validation

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TaskInfoValidation @Inject constructor() {

    fun validateTitle(title: String): Boolean {
        return title.isNotBlank()
    }

    fun validateDate(date: String): Boolean {
        return date.isNotBlank() && date != ""
    }

    fun validateTime(time: String): Boolean {
        return time.isNotBlank() && time != ""
    }
}
