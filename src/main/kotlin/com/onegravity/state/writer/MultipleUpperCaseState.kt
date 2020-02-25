package com.onegravity.state.writer

import com.onegravity.state.State

class MultipleUpperCaseState : State<WriterEvent, WriterContext, String, Any?> {
    private var count = 0

    @Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE")
    override fun handle(context: WriterContext, text: String) : Any? {
        println(text.toUpperCase())
        if (++count > 1) context.transition(OnUpperCaseDone)
        return null
    }

}
