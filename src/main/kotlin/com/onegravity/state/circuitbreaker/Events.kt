package com.onegravity.state.circuitbreaker

import com.onegravity.state.Event

sealed class CircuitBreakerEvent : Event()

object OnSuccess : CircuitBreakerEvent()
object OnFailed : CircuitBreakerEvent()
object OnResetTimeout : CircuitBreakerEvent()
