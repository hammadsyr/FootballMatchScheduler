package com.example.hammad.fifthkadesubmission.util

import java.text.SimpleDateFormat
import java.util.*


class ConvertDate {

    fun convertDate(date : String, time : String) : String  {
        val calendar = setCalendar(date, time)
        val formatter = SimpleDateFormat("EEE, dd MMM yyyy")
        formatDate(formatter)
        return formatter.format(calendar.time)
    }

    fun convertTime(date : String, time : String) : String{
        val calendar = setCalendar(date, time)
        val formatter = SimpleDateFormat("HH:mm")
        formatDate(formatter)
        return formatter.format(calendar.time)
    }

    fun setCalendar(date : String, time : String) : Calendar{
        val calendar = Calendar.getInstance(TimeZone.getTimeZone("Europe/London"))
        calendar.set(stringToInt(date,0,4),stringToInt(date,5,7)-1,stringToInt(date,8,10),
            stringToInt(time,0,2), stringToInt(time,3,5))
        return calendar
    }

    fun formatDate(formatter : SimpleDateFormat){
        formatter.timeZone = TimeZone.getTimeZone("Asia/Jakarta")
    }

    fun stringToInt(string : String, start : Int, end : Int) : Int{
        return Integer.parseInt(string.substring(start, end))
    }
}