# hearthis.at - API Usage example

This android application is a exemple of a simple music streaming player that use a music API from https://hearthis.at/api-v2/.

<p align="center">
  <img src="https://raw.githubusercontent.com/danielsadoliveira/hearthisat/master/screenshots/1.png" alt="screenshot_1" width="180" height="370"> <img src="https://raw.githubusercontent.com/danielsadoliveira/hearthisat/master/screenshots/2.png" alt="screenshot_2" width="180" height="370"> <img src="https://raw.githubusercontent.com/danielsadoliveira/hearthisat/master/screenshots/3.png" alt="screenshot_3" width="180" height="370"> <img src="https://raw.githubusercontent.com/danielsadoliveira/hearthisat/master/screenshots/4.png" alt="screenshot_4" width="180" height="370">
</p>

## About the project
The project was build using the **MVC design pattern, OOP, Event Oriented Programming and Functional Programming**.  

Where Event Oriented Programming was primarily used to control independent components asynchronously based on end user action. 

### NotificationEvent

```kotlin
object NotificationEvent {
    private val publisher : PublishSubject<Pair<String, Any?>> = PublishSubject.create()

    fun post(identifier: String, content: Any? = null) = publisher.onNext(Pair(identifier, content))

    fun <T> listen(identifier: String, contentType: Class<T>): Observable<Pair<String, Any?>> = publisher.filter {
            pair -> pair.first == identifier && (pair.second == null || pair.second!!::class.javaPrimitiveType == contentType || pair.second!!::class.javaObjectType == contentType)
    }
}
```
Post of a event:
```kotlin
NotificationEvent.post(identifier = "play", content = track.toJson())
```

Listening a event:
```kotlin
NotificationEvent.listen(identifier = "play", contentType = String::class.java).subscribe { pair ->
  val track = (pair.second as String).objectFromJson(Track::class.java)
  playing_now_frame.play(track = track, full = true)
}
```

## Libraries
**KOTLIN**  
org.jetbrains.kotlin:kotlin-stdlib-jdk7:1.3.50  
org.jetbrains.kotlinx:kotlinx-coroutines-core:1.1.1  
org.jetbrains.kotlinx:kotlinx-coroutines-android:1.1.1  

**ANDROID SUPPORT**  
com.android.support:appcompat-v7:28.0.0  
com.android.support:support-v4:28.0.0  
com.android.support:support-v13:28.0.0  
com.android.support:recyclerview-v7:28.0.0  
com.android.support:cardview-v7:28.0.0  
com.android.support:customtabs:28.0.0  
com.android.support:design:28.0.0  
com.android.support:multidex:1.0.3  
com.android.support.constraint:constraint-layout:1.1.3  
com.android.support:support-annotations:28.0.0  

**ANKO**  
org.jetbrains.anko:anko:0.10.8  
org.jetbrains.anko:anko-design:0.10.8  

**REACTIVEX**  
io.reactivex.rxjava2:rxjava:2.2.8  
io.reactivex.rxjava2:rxandroid:2.1.1  
io.reactivex.rxjava2:rxkotlin:2.3.0  

**GOOGLE**  
com.google.code.gson:gson:2.8.5  

**RETROFIT**  
com.squareup.retrofit2:retrofit:2.6.0  
com.squareup.retrofit2:converter-gson:2.6.0  
com.squareup.retrofit2:adapter-rxjava2:2.6.0  
com.squareup.okhttp3:logging-interceptor:3.7.0  

**GLIDE**  
com.github.bumptech.glide:compiler:4.9.0  
com.github.bumptech.glide:glide:4.9.0  
com.github.bumptech.glide:recyclerview-integration:4.6.1  

**OTHERS**  
com.danikula:videocache:2.7.1  
com.github.jkwiecien:EasyImage:2.0.4  
id.zelory:compressor:1.0.4  
com.redmadrobot:inputmask:4.1.0  
com.afollestad:assent:0.2.5  

## MIT License
Copyright (c) 2019 Daniel Sant'Anna de Oliveira

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
