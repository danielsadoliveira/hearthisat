package br.com.hearthisat.extensions

infix fun <T> Boolean.then(param: T): T? = if (this) param else null
