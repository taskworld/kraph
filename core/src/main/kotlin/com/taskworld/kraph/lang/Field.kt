package com.taskworld.kraph.lang

/**
 * Created by VerachadW on 9/19/2016 AD.
 */

internal open class Field(internal val name: String, internal val arguments: Argument? = null, internal var selectionSet: SelectionSet? = null) : GraphQLNode() {
    override fun print(): String {
        val selectionSetPart = selectionSet?.let { " " + it.print() } ?: ""
        return indent + "$name${arguments?.print() ?: ""}$selectionSetPart"
    }
}




