package me.lazmaid.kraph

import me.lazmaid.kraph.lang.*
import me.lazmaid.kraph.lang.relay.*

/**
 * Created by VerachadW on 9/19/2016 AD.
 */

typealias FieldBlock = Kraph.FieldBuilder.() -> Unit
typealias CursorBlock = Kraph.CursorSelectionBuilder.() -> Unit
typealias NodeBlock = Kraph.NodeBuilder.() -> Unit

class Kraph(f: Kraph.() -> Unit) {

    internal lateinit var document: Document

    init {
        f.invoke(this)
    }

    fun query(name: String? = null, builder: FieldBlock) {
        val set = createSelectionSet("query", builder)
        document = Document(Operation(OperationType.QUERY, selectionSet = set, name = name))
    }

    fun mutation(name: String? = null, builder: FieldBlock) {
        val set = createSelectionSet("mutation", builder)
        document = Document(Operation(OperationType.MUTATION, selectionSet = set, name = name))
    }

    private fun createSelectionSet(name: String, f: FieldBlock): SelectionSet {
        val builder = FieldBuilder().apply(f)
        val set = SelectionSet(builder.fields)
        if (set.fields.isEmpty()) {
            throw NoFieldsInSelectionSetException("No field elements inside \"$name\" block")
        }
        return set
    }

    fun toGraphQueryString() = document.operation.print(true, 0)
    fun toRequestString() = document.print(false, 0)

    inner open class FieldBuilder {
        internal val fields = arrayListOf<Field>()

        fun fieldObject(name: String, args: Map<String, Any>? = null, builder: FieldBlock) {
            addField(name, args, builder)
        }

        fun field(name: String, args: Map<String, Any>? = null, builder: FieldBlock? = null) {
            addField(name, args, builder)
        }

        fun cursorConnection(name: String, first: Int = -1, last: Int = -1,
                             before: String? = null, after: String? = null,
                             builder: CursorBlock) {
            val argsMap = linkedMapOf<String, Any>()
            if (first != -1) argsMap.put("first", first)
            if (last != -1) argsMap.put("last", last)
            before?.let { argsMap.put("before", it) }
            after?.let { argsMap.put("after", it) }

            if (argsMap.isEmpty()) {
                throw IllegalArgumentException("There must be at least 1 argument for Cursor Connection")
            }

            val selection = CursorSelectionBuilder()
            builder.invoke(selection)

            fields += CursorConnection(name, Argument(argsMap), SelectionSet(selection.fields))
        }

        fun func(name: String, args: Map<String, Any>, builder: FieldBlock) {
            fields += Mutation(name, InputArgument(args), createSelectionSet(name, builder))
        }

        protected fun addField(name: String, args: Map<String, Any>? = null, builder: FieldBlock? = null) {
            val argNode = args?.let(::Argument)
            val selectionSet = builder?.let {
                createSelectionSet(name, builder)
            }
            fields += Field(name, arguments = argNode, selectionSet = selectionSet)
        }
    }

    inner class CursorSelectionBuilder : FieldBuilder() {

        fun edges(builder: NodeBlock) {
            val node = NodeBuilder()
            builder.invoke(node)
            fields += Edges(node.selectionSet, node.fields)
        }

        fun pageInfo(f: FieldBuilder.() -> Unit) {
            val pageSelection = createSelectionSet("pageInfo", f)
            if (!pageSelection.fields.map { it.name }.any { it in arrayOf("hasNextPage", "hasPreviousPage") }) {
                throw NoFieldsInSelectionSetException("Selection Set must contain hasNextPage and/or hasPreviousPage field")
            }
            fields += PageInfo(pageSelection)
        }
    }

    inner class NodeBuilder: FieldBuilder() {
        internal lateinit var selectionSet: SelectionSet
        fun node(builder: FieldBuilder.() -> Unit) {
            selectionSet = createSelectionSet("node", builder)
        }
    }

}
