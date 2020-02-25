package com.onegravity.state

interface Context<E : Event> {

    fun transition(event: E)

}
