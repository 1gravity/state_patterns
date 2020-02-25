package com.onegravity.state.writer

import com.onegravity.state.*

private val graph = createGraph<State<WriterContext, String, Any?>, Event, Any>(null) {
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
