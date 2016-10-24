package com.taskworld.kraph.lang

/**
 * Created by VerachadW on 10/2/2016 AD.
 */

abstract internal class GraphQLNode {
    var level = 0

    abstract fun print(prettyFormat: Boolean, previousLevel: Int): String

    fun getIndentString(level: Int) = "\t".repeat(level)

    fun Map<String, Any>.print() =
            this.entries.foldIndexed("") { index, acc, entry ->
                var string = acc + "${entry.key}: ${
                when (entry.value) {
                    is String -> {
                        "\\\"${entry.value}\\\""
                    }
                    else -> {
                        entry.value
                    }
                }}"
                if (index != this.size - 1) {
                    string += ", "
                }
                string
            }
}

