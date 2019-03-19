package com.example.hammad.fifthkadesubmission.util

import android.content.Context
import android.content.Intent
import android.provider.CalendarContract
import android.support.v4.content.ContextCompat.startActivity
import java.util.*


class CalendarUtil {
    fun calendar(context: Context, eventName: String, eventDate: String, eventTime: String) {
        val beginTime = ConvertDate().setCalendar(eventDate, eventTime)
        val endTime = Calendar.getInstance(TimeZone.getTimeZone("Europe/London"))
        endTime.set(
            ConvertDate().stringToInt(eventDate, 0, 4), ConvertDate().stringToInt(eventDate, 5, 7) - 1,
            ConvertDate().stringToInt(eventDate, 8, 10), ConvertDate().stringToInt(eventTime, 0, 2) + 1,
            ConvertDate().stringToInt(eventTime, 3, 5)
        )
        beginTime.timeZone = TimeZone.getTimeZone("Asia/Jakarta")
        endTime.timeZone = TimeZone.getTimeZone("Asia/Jakarta")
        val intent = Intent(Intent.ACTION_INSERT)
            .setData(CalendarContract.Events.CONTENT_URI)
            .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginTime.timeInMillis)
            .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime.timeInMillis)
            .putExtra(CalendarContract.Events.TITLE, eventName)
        startActivity(context, intent, null)
    }
}



