package br.com.hearthisat.utils.general
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

object NotificationEvent {
    private val publisher : PublishSubject<Pair<String, Any?>> = PublishSubject.create()

    fun post(identifier: String, content: Any? = null) = publisher.onNext(Pair(identifier, content))

    fun <T> listen(identifier: String, contentType: Class<T>): Observable<Pair<String, Any?>> = publisher.filter {
            pair -> pair.first == identifier && (pair.second == null || pair.second!!::class.javaPrimitiveType == contentType || pair.second!!::class.javaObjectType == contentType)
    }
}