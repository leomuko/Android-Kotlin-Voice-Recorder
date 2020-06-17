package com.example.voicerecordingapp

import java.util.*
import java.util.concurrent.TimeUnit

class TimeAgo {
    fun getTimeAgo(duration: Long): String {
        var now: Date = Date()
        var seconds: Long = TimeUnit.MILLISECONDS.toSeconds(now.time - duration)
        var minutes: Long = TimeUnit.MILLISECONDS.toMinutes(now.time - duration)
        var hours: Long = TimeUnit.MILLISECONDS.toHours(now.time - duration)
        var days: Long = TimeUnit.MILLISECONDS.toDays(now.time - duration)

        if (seconds < 60) {
            return "Just Now"
        } else if (minutes == 1.toLong()) {
            return "a minute ago"
        } else if (minutes > 1 && minutes < 60) {
            return "${minutes} minutes ago"
        } else if (hours == 1.toLong()) {
            return "an hour ago"
        } else if (hours > 1 && hours < 24) {
            return "${hours} hours ago"
        } else if (days == 1.toLong()) {
            return "a day ago"
        } else {
            return "${days} days ago"
        }
    }
}