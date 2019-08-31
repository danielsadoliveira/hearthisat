package br.com.hearthisat.extensions

import java.util.*

val Any?.isNull: Boolean
    get() = this == null

val Any?.isNotNull: Boolean
    get() = this != null

fun Any?.isEqual(value: Any?) = this == value

fun Any?.isNotEqual(value: Any?) = this != value

fun ClosedRange<Int>.random() =
        Random().nextInt(endInclusive - start) +  start
