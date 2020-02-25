package com.onegravity.state.circuitbreaker

import com.onegravity.state.State
import io.reactivex.Single

class Open(private val timeout: Long) : State<CircuitBreakerEvent, CircuitBreaker, String, Single<String>> {
    private val startTime = System.currentTimeMillis()

    @Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE")
    override fun handle(context: CircuitBreaker, url: String): Single<String> {
        val timePassed = System.currentTimeMillis() - startTime
        if (timePassed > timeout) {
            context.transition(OnResetTimeout)
        }
        return Single.error(Exception("CircuitBreaker is open"))
    }

}
