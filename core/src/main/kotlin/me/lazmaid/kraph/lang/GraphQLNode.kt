package me.lazmaid.kraph.lang

abstract internal class GraphQLNode {
    var level = 0

    abstract fun print(prettyFormat: Boolean, escapeStrings: Boolean, previousLevel: Int): String

    fun getIndentString(level: Int) = "  ".repeat(level)
    fun getNewLineString(prettyFormat: Boolean) = if (prettyFormat) "\n" else " "

    fun print(value: Map<String, Any?>, prettyFormat: Boolean, escapeStrings: Boolean) =
        value.entries.joinToString(", ") { (k, v) ->
            "$k: ${convertToDataEntry(v).print(prettyFormat, escapeStrings)}"
        }

    private fun convertToArrayData(value: List<*>): DataEntry.ArrayData =
        DataEntry.ArrayData(value.map(this::convertToDataEntry))

    private fun convertToObjectData(map: Map<String, *>): DataEntry.ObjectData =
        DataEntry.ObjectData(map.map {
            it.key to convertToDataEntry(it.value)
        })

    @Suppress("UNCHECKED_CAST")
    private fun convertToDataEntry(value: Any?) =
            when(value) {
                is String   -> DataEntry.StringData(value)
                is Int      -> DataEntry.NonDecimalNumberData(value.toLong())
                is Long     -> DataEntry.NonDecimalNumberData(value)
                is Float    -> DataEntry.DecimalNumberData(value.toDouble())
                is Boolean  -> DataEntry.BooleanData(value)
                is Double   -> DataEntry.DecimalNumberData(value)
                is List<*>  -> convertToArrayData(value)
                is Map<*,*> -> convertToObjectData(value as Map<String, *>)
                else        -> throw RuntimeException("Unsupported Type")
            }
}

internal fun String.wrappedWithQuotes(shouldBeEscaped: Boolean) =
        if (shouldBeEscaped) {
            "\"$this\""
        } else {
            "\\\"$this\\\""
        }
