package me.lazmaid.kraph.lang

import javax.xml.crypto.Data

/**
 * Created by vwongsawangt on 8/6/2017 AD.
 */

internal sealed class DataEntry {
    abstract fun print(prettyFormat: Boolean, escapeStrings: Boolean): String

    class NonDecimalNumberData(val value: Long) : DataEntry() {
        override fun print(prettyFormat: Boolean, escapeStrings: Boolean) = value.toString()
    }

    class DecimalNumberData(val value: Double) : DataEntry() {
        override fun print(prettyFormat: Boolean, escapeStrings: Boolean) = value.toString()
    }

    class BooleanData(val value: Boolean) : DataEntry() {
        override fun print(prettyFormat: Boolean, escapeStrings: Boolean) = value.toString()
    }

    class StringData(val value: String) : DataEntry() {
        override fun print(prettyFormat: Boolean, escapeStrings: Boolean) =
                if (escapeStrings) {
                    "\\\"$value\\\""
                } else {
                    "\"$value\""
                }
    }

    class ArrayData(val values: List<DataEntry>) : DataEntry() {
        override fun print(prettyFormat: Boolean, escapeStrings: Boolean) =
            "[${ values.joinToString(", ") { it.print(prettyFormat, escapeStrings) } }]"
    }

    class ObjectData(val values: List<Pair<String, DataEntry>>) : DataEntry() {
        override fun print(prettyFormat: Boolean, escapeStrings: Boolean) =
            "{${ values.joinToString(", ") { (k, v) ->
                "${k}: ${v.print(prettyFormat, escapeStrings)}"
            } }}"
    }
}
