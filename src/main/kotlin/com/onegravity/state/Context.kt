package com.onegravity.state

interface Context {

    fun transition(event: Event)

}
