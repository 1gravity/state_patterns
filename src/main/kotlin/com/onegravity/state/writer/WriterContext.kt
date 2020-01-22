package com.onegravity.state.writer

import com.onegravity.state.Event
import com.onegravity.state.ContextImpl
import com.onegravity.state.State
import com.onegravity.state.StateMachine

private val graph = StateMachine
    .createGraph<State<WriterContext, String, Any?>, Event, Any> {
        initialState(LowerCaseState())
        state<LowerCaseState> {
            on<OnLowerCaseDone> {
                transitionTo(MultipleUpperCaseState())
            }
        }
        state<MultipleUpperCaseState> {
            on<OnUpperCaseDone> {
                transitionTo(LowerCaseState())
            }
        }
    }

class WriterContext : ContextImpl<WriterContext, String, Any?>(graph), Writer {

    override fun write(text: String) {
        getState().handle(this, text)
    }

}
