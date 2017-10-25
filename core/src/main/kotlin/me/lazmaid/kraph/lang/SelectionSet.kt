package me.lazmaid.kraph.lang

/**
 * Created by VerachadW on 10/2/2016 AD.
 */

internal class SelectionSet(internal val fields: List<Field>) : GraphQLNode() {

    override fun print(
        prettyFormat: Boolean,
        escapeStrings: Boolean,
        previousLevel: Int
    ): String {
        val nl = getNewLineString(prettyFormat)
        if (prettyFormat) level = previousLevel + 1 else level = 0
        val fieldStr = fields.joinToString(nl) { getIndentString(level) + it.print(prettyFormat, escapeStrings, level) }
        return "{${ nl + fieldStr + nl + getIndentString(previousLevel) }}"
    }
}
