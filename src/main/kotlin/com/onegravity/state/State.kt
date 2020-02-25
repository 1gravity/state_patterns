package com.onegravity.state

interface State<E: Event, C : Context<E>, P: Any?, R : Any?> {

    fun handle(context: C, parameters: P): R

}
