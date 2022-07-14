package com.shokworks.firstnews.providers

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

@SuppressLint("SimpleDateFormat")
fun timeCurrent(): String = SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().time)

fun converterUTC(fecha: String): String{
    val output = SimpleDateFormat("dd-MM-yyyy HH:mm a", Locale.getDefault())
    output.timeZone = TimeZone.getTimeZone("GMT-4")
    return output.format(SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault()).parse(fecha)!!)
}

data class TimeFormat(
    val day: Int,
    val dayOfWeek: String,
    val month: Int,
)

enum class DayOfWeek{
    MONDAY,
    TUESDAY,
    WEDNESDAY,
    THURSDAY,
    FRIDAY,
    SATURDAY,
    SUNDAY;
}

fun getWeekNameShort(name: String): String{
    return when (name){
        DayOfWeek.MONDAY.name -> {"Lunes"}
        DayOfWeek.TUESDAY.name -> {"Martes"}
        DayOfWeek.WEDNESDAY.name -> {"Miercoles"}
        DayOfWeek.THURSDAY.name -> {"Jueves"}
        DayOfWeek.FRIDAY.name -> {"Viernes"}
        DayOfWeek.SATURDAY.name -> {"Sabado"}
        DayOfWeek.SUNDAY.name -> {"Domingo"}
        else -> {"UNK"}
    }
}

enum class MonthOfYear {
    JANUARY,
    FEBRUARY,
    MARCH,
    APRIL,
    MAY,
    JUNE,
    JULY,
    AUGUST,
    SEPTEMBER,
    OCTOBER,
    NOVEMBER,
    DECEMBER;
}

fun getNameMonthShort(num: Int): String{
    return when (num - 1){
        MonthOfYear.JANUARY.ordinal -> {"de enero"}
        MonthOfYear.FEBRUARY.ordinal -> {"de febrero"}
        MonthOfYear.MARCH.ordinal -> {"de marzo"}
        MonthOfYear.APRIL.ordinal -> {"de abril"}
        MonthOfYear.MAY.ordinal -> {"de mayo"}
        MonthOfYear.JUNE.ordinal -> {"de junio"}
        MonthOfYear.JULY.ordinal -> {"de julio"}
        MonthOfYear.AUGUST.ordinal -> {"de agosto"}
        MonthOfYear.SEPTEMBER.ordinal -> {"de septiembre"}
        MonthOfYear.OCTOBER.ordinal -> {"de octubre"}
        MonthOfYear.NOVEMBER.ordinal -> {"de noviembre"}
        MonthOfYear.DECEMBER.ordinal -> {"de diciembre"}
        else -> {"UNK"}
    }
}

@SuppressLint("NewApi")
fun stringDateToTimeFormat(fecha: String): TimeFormat {
    val date = LocalDate.parse(fecha , DateTimeFormatter.ofPattern("dd-MM-yyy HH:mm a"))
    return TimeFormat(day = date.dayOfMonth, dayOfWeek = date.dayOfWeek.name, month = date.month.value)
}