package com.onegravity.state

abstract class ContextImpl<E: Event, C : Context<E>, P: Any?, R : Any?>(
    graph: Graph<State<E, C, P, R>, Event, Any>
) : Context<E> {

    private val stateMachine = createStateMachine(graph) {
        onTransition {
            (it as? Transition.Valid)?.run {
                val fromState = fromState.javaClass.simpleName
                val toState = toState.javaClass.simpleName
                val event = event.javaClass.simpleName
                println("   $event triggered transition $fromState -> $toState")
            }
        }
    }

    fun getState() = stateMachine.getState()

     override fun transition(event: E) {
        stateMachine.transition(event)
     }

}
