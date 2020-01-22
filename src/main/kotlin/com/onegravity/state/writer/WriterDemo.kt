package com.onegravity.state.writer

import io.reactivex.Observable
import java.util.concurrent.CountDownLatch

fun main() {
    execute(WriterContext())
}

private fun execute(writer: Writer) {
    val finish = CountDownLatch(1)

    Observable.just("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday")
        .doOnComplete { finish.countDown() }
        .subscribe { writer.write(it) }

    finish.await()
}
