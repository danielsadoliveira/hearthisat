package br.com.hearthisat.extensions

fun Int.percent(total: Long) : Int {
    return ((this * 100) / total).toInt()
}

fun Double.percent(total: Long) : Int {
    return ((this * 100) / total).toInt()
}

fun Long.percent(total: Long) : Int {
    return ((this * 100) / total).toInt()
}

