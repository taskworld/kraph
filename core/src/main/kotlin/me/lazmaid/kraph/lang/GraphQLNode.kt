package me.lazmaid.kraph.lang

abstract internal class GraphQLNode {
    var level = 0

    abstract fun print(prettyFormat: Boolean, previousLevel: Int): String

    fun getIndentString(level: Int) = "  ".repeat(level)
    fun getNewLineString(prettyFormat: Boolean) = if (prettyFormat) "\n" else "\\n"

    fun print(value: Map<String, Any?>, prettyFormat: Boolean): String {
        return value.entries.foldIndexed("") { index, acc, (k, v)->
            var newAcc = acc + "$k: ${convertToDataEntry(v).print(prettyFormat)}"
            if (index != value.entries.size - 1) {
                newAcc += ", "
            }
            newAcc
        }
    }

    private fun convertToArrayData(value: List<*>): DataEntry.ArrayData {
        return DataEntry.ArrayData(value.map(this::convertToDataEntry))
    }

    private fun convertToObjectData(map: Map<String, *>): DataEntry.ObjectData {
        return DataEntry.ObjectData(map.map {
            it.key to convertToDataEntry(it.value)
        })
    }

    private fun convertToDataEntry(value: Any?) =
            when(value) {
                is String -> {
                    DataEntry.StringData(value)
                }
                is Int -> {
                    DataEntry.NonDecimalNumberData(value.toLong())
                }
                is Long -> {
                    DataEntry.NonDecimalNumberData(value)
                }
                is Float, Double -> {
                    DataEntry.DecimalNumberData(value as Double)
                }
                is List<*> -> {
                    convertToArrayData(value)
                }
                is Map<*, *> -> {
                    convertToObjectData(value as Map<String, *>)
                }
                else -> {
                    throw RuntimeException("")
                }
            }
}

internal sealed class DataEntry {
    abstract fun print(prettyFormat: Boolean): String

    class NonDecimalNumberData(val value: Long) : DataEntry() {
        override fun print(prettyFormat: Boolean) = value.toString()
    }

    class DecimalNumberData(val value: Double) : DataEntry() {
        override fun print(prettyFormat: Boolean) = value.toString()
    }

    class StringData(val value: String) : DataEntry() {
        override fun print(prettyFormat: Boolean) =
                if (prettyFormat) {
                    "\"$value\""
                } else {
                    "\\\"$value\\\""
                }
    }

    class ArrayData(val values: List<DataEntry>) : DataEntry() {
        override fun print(prettyFormat: Boolean) =
                values.foldIndexed("[") { index, acc, item ->
                    var newAcc = acc + item.print(prettyFormat)
                    if (index != values.size - 1) {
                        newAcc += ", "
                    } else {
                        newAcc += "]"
                    }
                    newAcc
                }
    }

    class ObjectData(val values: List<Pair<String, DataEntry>>) : DataEntry() {
        override fun print(prettyFormat: Boolean) =
                values.foldIndexed("{") { index, acc, (k, v) ->
                    var newAcc = acc + "$k: ${v.print(prettyFormat)}"
                    if (index != values.size - 1) {
                        newAcc += ", "
                    } else {
                        newAcc += "}"
                    }
                    newAcc
                }
    }
}
