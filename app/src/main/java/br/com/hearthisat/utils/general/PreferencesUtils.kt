package br.com.hearthisat.utils.general

import android.content.Context
import android.content.SharedPreferences
import br.com.hearthisat.app.App
import br.com.hearthisat.app.DataManager
import br.com.hearthisat.extensions.unwrap

class PreferencesUtils { companion object }

val PreferencesUtils.Companion.shared : SharedPreferences
    get() = App.instance.getContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE)

//region ## PUTS ##
fun PreferencesUtils.Companion.putString(key: DataManager.Key, value: String? = null) {
    val edit = shared.edit()
    value.unwrap(
            notNull = { edit.putString(key.name, it)  },
            isNull = { edit.remove(key.name) }
    )
    edit.apply()
}

fun PreferencesUtils.Companion.putBoolean(key: DataManager.Key, value: Boolean? = null) {
    val edit = shared.edit()
    value.unwrap(
            notNull = { edit.putBoolean(key.name, value!!) },
            isNull = { edit.remove(key.name) }
    )
    edit.apply()
}

fun PreferencesUtils.Companion.putInt(key: DataManager.Key, value: Int? = null) {
    val edit = shared.edit()
    value.unwrap(
            notNull = { edit.putInt(key.name, value!!) },
            isNull = { edit.remove(key.name) }
    )
    edit.apply()
}

fun PreferencesUtils.Companion.putLong(key: DataManager.Key, value: Long? = null) {
    val edit = shared.edit()
    value.unwrap(
            notNull = { edit.putLong(key.name, value!!) },
            isNull = { edit.remove(key.name) }
    )
    edit.apply()
}

fun PreferencesUtils.Companion.putFloat(key: DataManager.Key, value: Float? = null) {
    val edit = shared.edit()
    value.unwrap(
            notNull = { edit.putFloat(key.name, value!!) },
            isNull = { edit.remove(key.name) }
    )
    edit.apply()
}
//endregion

//region ## GETS ##
fun PreferencesUtils.Companion.getString(key: DataManager.Key, default: String = "") : String {
    return shared.getString(key.name, default) ?: ""
}

fun PreferencesUtils.Companion.getBoolean(key: DataManager.Key, default: Boolean = false) : Boolean {
    return shared.getBoolean(key.name, default)
}

fun PreferencesUtils.Companion.getInt(key: DataManager.Key, default: Int = 0) : Int {
    return shared.getInt(key.name, default)
}

fun PreferencesUtils.Companion.getLong(key: DataManager.Key, default: Long = 0) : Long {
    return shared.getLong(key.name, default)
}

fun PreferencesUtils.Companion.getFloat(key: DataManager.Key, default: Float = 0f) : Float {
    return shared.getFloat(key.name, default)
}
//endregion