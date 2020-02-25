package com.onegravity.state.writer

import com.onegravity.state.Event

sealed class WriterEvent: Event()

object OnLowerCaseDone : WriterEvent()

object OnUpperCaseDone : WriterEvent()
