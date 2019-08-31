package br.com.hearthisat.extensions

import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit


fun Date.getCalendar(): Calendar {
    val cal = Calendar.getInstance()
    cal.time = this
    return cal
}

fun Date.dateTime(year: Int, month: Int, day: Int, hour: Int, minute: Int, second: Int): Date? {
    return try {
        val calendar = Calendar.getInstance()
        calendar.time = this
        calendar.set(Calendar.YEAR, year)
        calendar.set(Calendar.MONTH, month)
        calendar.set(Calendar.DAY_OF_MONTH, day)
        calendar.set(Calendar.HOUR_OF_DAY, hour)
        calendar.set(Calendar.MINUTE, minute)
        calendar.set(Calendar.SECOND, second)
        return calendar.time
    } catch(e: Exception) {
        null
    }
}

fun Date.date(year: Int, month: Int, day: Int): Date? {
    return try {
        val calendar = Calendar.getInstance()
        calendar.time = this
        calendar.set(Calendar.YEAR, year)
        calendar.set(Calendar.MONTH, month)
        calendar.set(Calendar.DAY_OF_MONTH, day)
        return calendar.time
    } catch(e: Exception) {
        null
    }
}

fun Date.time(hour: Int, minute: Int, second: Int): Date? {
    return try {
        val calendar = Calendar.getInstance()
        calendar.time = this
        calendar.set(Calendar.HOUR_OF_DAY, hour)
        calendar.set(Calendar.MINUTE, minute)
        calendar.set(Calendar.SECOND, second)
        return calendar.time
    } catch(e: Exception) {
        null
    }
}

fun Date.validateBirthday(): Boolean {
    val a = this.getCalendar()
    val b = Date().getCalendar()

    var diff = b.get(Calendar.YEAR) - a.get(Calendar.YEAR)
    if (a.get(Calendar.MONTH) > b.get(Calendar.MONTH) || a.get(Calendar.MONTH) == b.get(Calendar.MONTH) && a.get(Calendar.DATE) > b.get(Calendar.DATE)) {
        diff--
    }

    return diff >= 0
}

fun Date.toString(format : String) : String {
    val dateFormat = SimpleDateFormat(format, Locale.getDefault())
    return dateFormat.format(this)
}

fun Date.fromString(format : String, dateString: String) : Date {
    val dateFormat = SimpleDateFormat(format, Locale.getDefault())
    return dateFormat.parse(dateString)
}

fun Date.subtract(days: Int? = null, week: Int? = null, month: Int? = null) : Date {
    val calendar = Calendar.getInstance()
    calendar.time = this

    when {
        days != null -> calendar.add(Calendar.DAY_OF_MONTH, -days)
        week != null -> calendar.add(Calendar.WEEK_OF_MONTH, -week)
        month != null -> calendar.add(Calendar.MONTH, -month)
    }

    return calendar.time
}

fun Date.increase(days: Int? = null, week: Int? = null, month: Int? = null) : Date {
    val calendar = Calendar.getInstance()
    calendar.time = this

    when {
        days != null -> calendar.add(Calendar.DAY_OF_MONTH, days)
        week != null -> calendar.add(Calendar.WEEK_OF_MONTH, week)
        month != null -> calendar.add(Calendar.MONTH, month)
    }

    return calendar.time
}

fun Date?.getAge(): Int{
    return if(this != null){
        val a = this.getCalendar()
        val b = Date().getCalendar()

        var diff = b.get(Calendar.YEAR) - a.get(Calendar.YEAR)
        if (a.get(Calendar.MONTH) > b.get(Calendar.MONTH) || a.get(Calendar.MONTH) == b.get(Calendar.MONTH) && a.get(Calendar.DATE) > b.get(Calendar.DATE)) {
            diff--
        }

        diff
    } else {
        0
    }
}

fun Date.difference(date: Date, timeUnit: TimeUnit): Long {
    return timeUnit.convert((date.time - this.time), TimeUnit.MILLISECONDS)
}

fun Date.monthsBetween(startDate: Date, endDate: Date): Int{
    val startCalendar = GregorianCalendar().apply {
        time = startDate
    }
    val endCalendar = GregorianCalendar().apply {
        time = endDate
    }

    val diffYear = endCalendar.get(Calendar.YEAR) - startCalendar.get(Calendar.YEAR)
    return diffYear * 12 + endCalendar.get(Calendar.MONTH) - startCalendar.get(Calendar.MONTH)
}