package com.onegravity.state.circuitbreaker

import com.github.kittinunf.fuel.httpGet
import com.onegravity.state.State
import io.reactivex.Single
import java.net.SocketTimeoutException
import java.util.concurrent.atomic.AtomicInteger
import com.github.kittinunf.result.Result
import java.net.ConnectException

class Closed(private val failAfter: Int) : State<CircuitBreaker, String, Single<String>> {
    private val failures = AtomicInteger(0)

    @Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE")
    override fun handle(context: CircuitBreaker, url: String) =
        call(url)
            .doOnSuccess { failures.set(0) }
            .doOnError {
                when (it is SocketTimeoutException || it is ConnectException) {
                    true -> if (failures.incrementAndGet() >= failAfter) {
                        context.transition(OnFailed)
                    }
                    else -> failures.set(0)
                }
            }

}

fun call(url: String) = Single.create<String> { emitter ->
    url.httpGet()
        .timeout(1000)
        .responseString { _, _, result ->
            when (result) {
                is Result.Failure -> emitter.onError(result.getException().exception)
                is Result.Success -> emitter.onSuccess(result.get())
            }
        }
        .join()
}
