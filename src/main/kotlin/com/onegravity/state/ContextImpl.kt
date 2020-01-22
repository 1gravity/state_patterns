package com.onegravity.state

abstract class ContextImpl<C : Context, P: Any?, R : Any?>(
    graph: StateMachine.Graph<State<C, P, R>, Event, Any>
) : Context {

    private val stateMachine = StateMachine.create(graph) {
        onTransition {
            (it as? StateMachine.Transition.Valid)?.run {
                val fromState = fromState.javaClass.simpleName
                val toState = toState.javaClass.simpleName
                val event = event.javaClass.simpleName
                println("   $event triggered transition $fromState -> $toState")
            }
        }
    }

    fun getState() = stateMachine.state

    override fun transition(event: Event) {
        stateMachine.transition(event)
    }

}
