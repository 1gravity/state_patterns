package com.onegravity.state.circuitbreaker

import com.onegravity.state.*
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

const val timeout = 5 * 1000L
const val failAfter = 5

private val graph = createGraph<State<CircuitBreakerEvent, CircuitBreaker, String, Single<String>>, Event, Any>(null) {
        initialState(Closed(failAfter))
        state<Closed> {
            on<OnFailed> {
                transitionTo(Open(timeout))
            }
        }
        state<Open> {
            on<OnResetTimeout> {
                transitionTo(HalfClosed())
            }
        }
        state<HalfClosed> {
            on<OnFailed> {
                transitionTo(Open(timeout))
            }
            on<OnSuccess> {
                transitionTo(Closed(failAfter))
            }
        }
    }

class CircuitBreaker : ContextImpl<CircuitBreakerEvent, CircuitBreaker, String, Single<String>>(graph) {

    /**
     * We wrap the call into a Completable so that we don't have to handle onError in the caller
     */
    fun call(url: String) = Completable.create { emitter ->
        getState().handle(this, url)
            .subscribeOn(Schedulers.io())
            .doFinally { emitter.onComplete() }
            .subscribe( {
                println("Call to $url succeeded")
            }, {
                println("Call to $url failed: ${it.message}")
            } )
    }

}
