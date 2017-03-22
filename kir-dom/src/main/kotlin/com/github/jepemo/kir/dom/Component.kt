package com.github.jepemo.kir.dom

import kotlinx.html.Tag

/**
 * Components represents the web reusable parts.
 *
 * They have a lifecycle: they are instantiated, mounted, rendered,
 * and eventually updated, unmounted, and destroyed.
 */
abstract class Component : Tag {
    /**
     * This method is invoked only once, before rendering occurs for the first time.
     */
    open fun componentWillMount(): Unit  {

    }

    /**
     * This method is invoked only once, after rendering occurs for the first time
     */
    open fun componentDidMount(): Unit {

    }

    /**
     * This method is invoked after re-rendering occurs
     */
    open fun componentDidUpdate(): Unit {

    }

    /**
     * This method is invoked, after the decision has been made to re-render.
     */
    open fun componentWillUpdate() : Unit {

    }

    /**
     * Based on the next values of props and state, a component may decide to
     * re-render or not to re-render.
     */
    open fun shouldComponentUpdate() : Boolean = true

    /**
     * This method is called, assuming shouldComponentUpdate returned true
     */
    abstract fun render (): String
}
