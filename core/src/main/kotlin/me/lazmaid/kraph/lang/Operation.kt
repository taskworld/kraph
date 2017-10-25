package me.lazmaid.kraph.lang

/**
 * Created by VerachadW on 10/2/2016 AD.
 */

internal class Operation(
    internal val type: OperationType,
    internal val selectionSet: SelectionSet,
    internal val name: String? = null,
    internal val arguments: Argument? = null
) : GraphQLNode() {
    override fun print(
        prettyFormat: Boolean,
        escapeStrings: Boolean,
        previousLevel: Int
    ): String {
        val namePart = name?.let{ " $it" } ?: ""
        val argumentPart = arguments?.print(prettyFormat, escapeStrings, previousLevel)?.let{ " $it" } ?: ""
        return "${type.name.toLowerCase()}$namePart$argumentPart ${selectionSet.print(prettyFormat, escapeStrings, previousLevel)}"
    }
}

internal enum class OperationType {
    QUERY,
    MUTATION
}
