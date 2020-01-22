package com.onegravity.state.circuitbreaker

import com.onegravity.state.State
import io.reactivex.Single
import java.net.SocketTimeoutException

class HalfClosed : State<CircuitBreaker, String, Single<String>> {

    @Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE")
    override fun handle(context: CircuitBreaker, url: String) =
        call(url)
            .doOnSuccess { context.transition(OnSuccess) }
            .doOnError {
                when (it is SocketTimeoutException) {
                    true -> context.transition(OnFailed)
                    else -> context.transition(OnSuccess)
                }
            }

}
