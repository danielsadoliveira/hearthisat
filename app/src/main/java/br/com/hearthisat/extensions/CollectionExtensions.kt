package br.com.hearthisat.extensions

/**
 * Created by danielsdo on 17/08/17.
 */

inline fun <reified T> listFromLists(vararg lists: List<T>): List<T> {
    return lists.fold(mutableListOf<T>()) { mainList, list ->
        mainList.apply { addAll(list) }
    }
}

/**
 * Tries to cast this as? MutableList
 *
 * @return MutableList from Collection, or null in case cast failed.
 */
fun <E> Collection<E>?.asMutableList(): MutableList<E>? {
    return this as? MutableList<E>
}

/**
 * @return A list with pairs of Index, Value.
 */
fun <T> Array<T>.enumerated(): List<Pair<Int, T>> {
    return indices.zip(this)
}

/**
 * @return A list with pairs of Index, Value.
 */
fun <T> Collection<T>.enumerated(): List<Pair<Int, T>> {
    return indices.zip(this)
}

/**
 * Execute {action} for each item of the collection, case {count} > 1.
 * @param action Receives {item} from collection at {index}.
 *
 * @return itself
 */
inline fun <T> Collection<T>.caseMany(action: (Int, T) -> Unit): Collection<T> {
    if (count() > 1) {
        forEachIndexed { index, obj ->
            action.invoke(index, obj)
        }
    }
    return this
}

/**
 * Execute {action} when collection only have 1 item.
 * @param action Receives {first} item from collection.
 *
 * @return itself
 */
inline fun <T> Collection<T>.caseSingle(action: (T) -> Unit): Collection<T> {
    if (count() == 1) {
        action.invoke(first())
    }
    return this
}

/**
 * Execute {action} when Iterable only have 1 item.
 * @param action Receives {first} item from Iterable.
 *
 * @return itself
 */
inline fun <T> Iterable<T>.caseSingle(action: (T) -> Unit): Iterable<T> {
    if (count() == 1) {
        action.invoke(first())
    }
    return this
}

/**
 * Execute {action} for each item of the Iterable, case {count} > 1.
 * @param action Receives {item} from Iterable at {index}.
 *
 * @return itself
 */
inline fun <T> Iterable<T>.caseMany(action: (Int, T) -> Unit): Iterable<T> {
    if (count() > 1) {
        forEachIndexed { index, obj ->
            action.invoke(index, obj)
        }
    }
    return this
}