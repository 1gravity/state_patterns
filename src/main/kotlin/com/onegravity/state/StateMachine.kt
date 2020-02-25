package com.onegravity.state

interface StateMachine<STATE : Any, EVENT : Any, SIDE_EFFECT : Any> {
    fun getState() : STATE
    fun transition(event: EVENT): Transition<STATE, EVENT, SIDE_EFFECT>
}

fun <STATE : Any, EVENT : Any, SIDE_EFFECT : Any> createStateMachine(
    graph: Graph<STATE, EVENT, SIDE_EFFECT>?,
    init: GraphBuilder<STATE, EVENT, SIDE_EFFECT>.() -> Unit
) : StateMachine<STATE, EVENT, SIDE_EFFECT> = StateMachineImpl(createGraph(graph, init))

fun <STATE : Any, EVENT : Any, SIDE_EFFECT : Any> createGraph(
    graph: Graph<STATE, EVENT, SIDE_EFFECT>?,
    init: GraphBuilder<STATE, EVENT, SIDE_EFFECT>.() -> Unit
) = GraphBuilder(graph).apply(init).build()
