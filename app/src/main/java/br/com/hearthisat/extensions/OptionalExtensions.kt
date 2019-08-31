package br.com.hearthisat.extensions

/**
 * Created by danielsdo on 15/08/17.
 */


inline fun <reified T> T?.caseNull(action: () -> Unit): T? {
    if (this == null) {
        action.invoke()
    }
    return this
}

inline fun <reified T> T?.caseNotNull(action: (T) -> Unit): T? {
    if (this != null) {
        action.invoke(this)
    }
    return this
}

inline fun <reified T> T?.unwrap(notNull: (T) -> Unit, isNull: () -> Unit): T? {
    if (this != null) {
        notNull.invoke(this)
    } else {
        isNull.invoke()
    }
    return this
}
