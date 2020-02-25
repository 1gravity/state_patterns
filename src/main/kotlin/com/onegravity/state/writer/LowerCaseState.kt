package com.onegravity.state.writer

import com.onegravity.state.State

class LowerCaseState : State<WriterEvent, WriterContext, String, Any?> {

    @Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE")
    override fun handle(context: WriterContext, text: String) : Any? {
        println(text.toLowerCase())
        context.transition(OnLowerCaseDone)
        return null
    }

}
