package me.lazmaid.kraph.lang

/**
 * Created by VerachadW on 10/2/2016 AD.
 */

abstract internal class GraphQLNode {
    var level = 0

    abstract fun print(prettyFormat: Boolean, previousLevel: Int): String

    fun getIndentString(level: Int) = "  ".repeat(level)
    fun getNewLineString(prettyFormat: Boolean) = if (prettyFormat) "\n" else "\\n"

    private fun printEscaped(value: Any?, prettyFormat: Boolean) =
            if (prettyFormat) {
                "\"$value\""
            } else {
                "\\\"$value\\\""
            }

    fun print(map: Map<String, Any>, prettyFormat: Boolean) =
            map.entries.foldIndexed("") { index, acc, entry ->
                var string = acc + "${entry.key}: ${
                when (entry.value) {
                    is String -> {
                        printEscaped(entry.value, prettyFormat)
                    }
                    is List<*> -> {
                        val valueList = entry.value as List<*>
                        valueList.map {
                            printEscaped(it as String, prettyFormat)
                        }
                    }
                    is KraphDataObject -> {
                        (entry.value as KraphDataObject).print()
                    }
                    else -> {
                        entry.value
                    }
                }}"
                if (index != map.size - 1) {
                    string += ", "
                }
                string
            }
}

class KraphDataObject : HashMap<String, Any>() {
    private fun printList(list: List<Any?>): String {
        list.fold("") { acc, item ->
            when (item) {
                is KraphDataObject -> {
                    item.print()
                }
                is List<*> -> {
                    printList(item)
                }
                else -> {
                    item
                }
            }
            acc
        }
    }

    fun print() =
            this.entries.forEach {
                when (it.value) {
                    is String -> {
                        it.value
                    }
                    is List<*> -> {
                        printList(it as List<Any?>)
                    }
                    is KraphDataObject -> {
                        (it.value as KraphDataObject).print()
                    }
                    else {
                    }
                }
            }
}

}
