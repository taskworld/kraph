package com.taskworld.kraph.lang

/**
 * Created by VerachadW on 10/2/2016 AD.
 */

internal class SelectionSet(internal val fields: List<Field>) : GraphQLNode() {

    override fun print(): String {
        return "{\\n${
            fields.fold("") { acc, node ->
                acc + node.print() + "\\n"
            }
        }}"
    }
}