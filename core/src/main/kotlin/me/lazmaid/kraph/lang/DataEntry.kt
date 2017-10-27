package me.lazmaid.kraph.lang

/**
 * Created by vwongsawangt on 8/6/2017 AD.
 */

internal sealed class DataEntry {
    abstract fun print(prettyFormat: Boolean, escapeStrings: Boolean): String

    class NonDecimalNumberData(private val value: Long) : DataEntry() {
        constructor(value: Int) : this(value.toLong())

        override fun print(prettyFormat: Boolean, escapeStrings: Boolean) = value.toString()
    }

    class DecimalNumberData(private val value: Double) : DataEntry() {
        override fun print(prettyFormat: Boolean, escapeStrings: Boolean) = value.toString()
    }

    class BooleanData(private val value: Boolean) : DataEntry() {
        override fun print(prettyFormat: Boolean, escapeStrings: Boolean) = value.toString()
    }

    class StringData(private val value: String) : DataEntry() {
        override fun print(prettyFormat: Boolean, escapeStrings: Boolean) =
                if (escapeStrings) {
                    "\\\"$value\\\""
                } else {
                    "\"$value\""
                }
    }

    class ArrayData(private val values: List<DataEntry>) : DataEntry() {
        override fun print(prettyFormat: Boolean, escapeStrings: Boolean) =
            "[${ values.joinToString(", ") { it.print(prettyFormat, escapeStrings) } }]"
    }

    class ObjectData(private val values: List<Pair<String, DataEntry>>) : DataEntry() {
        override fun print(prettyFormat: Boolean, escapeStrings: Boolean) =
            "{${ values.joinToString(", ") { (k, v) ->
                "${k}: ${v.print(prettyFormat, escapeStrings)}"
            } }}"
    }
}
