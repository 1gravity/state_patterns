package com.onegravity.state.circuitbreaker

import com.onegravity.state.Event

object OnSuccess : Event()
object OnFailed : Event()
object OnResetTimeout : Event()
