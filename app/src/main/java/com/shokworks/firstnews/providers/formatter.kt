package com.shokworks.firstnews.providers

import android.annotation.SuppressLint
import timber.log.Timber
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

@SuppressLint("SimpleDateFormat")
fun timeCurrent(): String = SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().time)

@SuppressLint("SimpleDateFormat")
fun convertirFormatoUTC(fecha: String?, UTC: (String) -> Unit) {
    UTC(SimpleDateFormat("dd/MM/yyyy").format(SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS").parse(fecha)))
}

fun convertirUTC(fecha: String): String{
    val output = SimpleDateFormat("dd-MM-yyyy HH:mm a")
    output.timeZone = TimeZone.getTimeZone("GMT-4")
    return output.format(SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(fecha))
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
        MonthOfYear.JANUARY.ordinal -> {"Enero"}
        MonthOfYear.FEBRUARY.ordinal -> {"Febrero"}
        MonthOfYear.MARCH.ordinal -> {"Marzo"}
        MonthOfYear.APRIL.ordinal -> {"Abril"}
        MonthOfYear.MAY.ordinal -> {"Mayo"}
        MonthOfYear.JUNE.ordinal -> {"Junio"}
        MonthOfYear.JULY.ordinal -> {"Julio"}
        MonthOfYear.AUGUST.ordinal -> {"Agosto"}
        MonthOfYear.SEPTEMBER.ordinal -> {"Septiembre"}
        MonthOfYear.OCTOBER.ordinal -> {"Octubre"}
        MonthOfYear.NOVEMBER.ordinal -> {"Noviembre"}
        MonthOfYear.DECEMBER.ordinal -> {"Diciembre"}
        else -> {"UNK"}
    }
}

@SuppressLint("NewApi")
fun stringDateToTimeFormat(fecha: String): TimeFormat {
    Timber.e("Timer: $fecha")
    val firstApiFormat = DateTimeFormatter.ofPattern("dd-MM-yyy HH:mm a")
    val date = LocalDate.parse(fecha , firstApiFormat)
    Timber.e("Date: $date")
    return TimeFormat(day = date.dayOfMonth, dayOfWeek = date.dayOfWeek.name, month = date.month.value)
}