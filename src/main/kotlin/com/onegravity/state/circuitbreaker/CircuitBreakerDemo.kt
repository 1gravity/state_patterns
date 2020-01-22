package com.onegravity.state.circuitbreaker

import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

private val urls = arrayListOf(
    "https://www.google.com",
    "https://www.gooooogle.com",
    "https://www.gooooogle.com",
    "https://www.gooooogle.com",
    "https://www.gooooogle.com",
    "https://www.gooooogle.com",
    "https://www.google.com",
    "https://www.google.com",
    "https://www.google.com",
    "https://www.google.com",
    "https://www.google.com",
    "https://www.google.com",
    "https://www.google.com",
    "https://www.google.com",
    "https://www.google.com"
)

fun main() {
    val circuitBreaker = CircuitBreaker()
    val countDownLatch = CountDownLatch(urls.size)

    fun call(url: String) =
        circuitBreaker.call(url)
            .doOnSubscribe { println("Calling $url") }
            .doOnComplete { countDownLatch.countDown() }

    Observable.fromIterable(urls)
        .delay(1, TimeUnit.SECONDS, Schedulers.trampoline())
        .flatMapCompletable{ call(it) }
        .subscribe()

    countDownLatch.await()
}
