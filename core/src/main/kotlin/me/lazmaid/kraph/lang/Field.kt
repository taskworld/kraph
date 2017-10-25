package me.lazmaid.kraph.lang

/**
 * Created by VerachadW on 9/19/2016 AD.
 */

internal open class Field(
    internal val name: String,
    internal val arguments: Argument? = null,
    internal var selectionSet: SelectionSet? = null
) : GraphQLNode() {
    override fun print(
        prettyFormat: Boolean,
        escapeStrings: Boolean,
        previousLevel: Int
    ): String {
        val selectionSetPart = selectionSet?.print(prettyFormat, escapeStrings, previousLevel)?.let{ " $it" } ?: ""
        val argumentsPart = arguments?.print(prettyFormat, escapeStrings, previousLevel)?.let{ " $it" } ?: ""
        return "$name$argumentsPart$selectionSetPart"
    }
}
