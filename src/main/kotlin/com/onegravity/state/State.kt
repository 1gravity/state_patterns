package com.onegravity.state

interface State<C : Context, P: Any?, R : Any?> {

    fun handle(context: C, parameters: P): R

}
