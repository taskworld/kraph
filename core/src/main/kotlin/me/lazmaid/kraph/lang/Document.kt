package me.lazmaid.kraph.lang

/**
 * Created by VerachadW on 10/2/2016 AD.
 */

internal class Document(internal val operation: Operation) : GraphQLNode() {
    internal val variables: Variables = Variables()
    override fun print(
        prettyFormat: Boolean,
        escapeStrings: Boolean,
        previousLevel: Int
    ): String {
        val operationNamePart = operation.name?.let{ "\"$it\"" }
        val variablesPart = variables.print()
        return "{\"query\": \"${operation.print(prettyFormat, escapeStrings, previousLevel)}\", \"variables\": $variablesPart, \"operationName\": $operationNamePart}"
    }
}
