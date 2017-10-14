package me.lazmaid.kraph.lang.relay

import me.lazmaid.kraph.lang.Argument
import me.lazmaid.kraph.lang.Field
import me.lazmaid.kraph.lang.SelectionSet


/**
 * Created by VerachadW on 10/2/2016 AD.
 */

internal class CursorConnection(name: String, argument: Argument, selectionSet: SelectionSet) : Field(name, alias = null, arguments = argument, selectionSet = selectionSet)

internal class Edges(nodeSelectionSet: SelectionSet, additionalField: List<Field> = listOf()) : Field("edges", alias = null, selectionSet = SelectionSet(listOf(Field("node", alias = null, selectionSet = nodeSelectionSet)) + additionalField))

internal class PageInfo (pageSelectionSet: SelectionSet) : Field("pageInfo", alias = null, selectionSet = pageSelectionSet)

