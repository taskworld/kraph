package me.lazmaid.kraph.test

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import me.lazmaid.kraph.lang.Argument
import me.lazmaid.kraph.lang.Field
import me.lazmaid.kraph.lang.SelectionSet
import me.lazmaid.kraph.lang.relay.CursorConnection
import me.lazmaid.kraph.lang.relay.Edges
import me.lazmaid.kraph.lang.relay.InputArgument
import me.lazmaid.kraph.lang.relay.PageInfo
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it

/**
 * Created by VerachadW on 10/3/2016 AD.
 */
class RelayPrintSpek : Spek({
    describe("Relay InputArgument print function") {
        given("id as argument and value as 1") {
            val node = InputArgument(mapOf("id" to 1))
            it("should print (input: { id: 1 })") {
                assertThat(node.print(false, 0), equalTo("(input: { id: 1 })"))
            }
        }
        given("name as argument and value as John Doe") {
            val node = InputArgument(mapOf("name" to "John Doe"))
            it("should print (input: { name: \\\"John Doe\\\" })") {
                assertThat(node.print(false, 0), equalTo("(input: { name: \\\"John Doe\\\" })"))
            }
        }
        given("name as argument and value as John Doe with pretty format enabled") {
            val node = InputArgument(mapOf("name" to "John Doe"))
            it("should print (input: { name: \"John Doe\" })") {
                assertThat(node.print(true, 0), equalTo("(input: { name: \"John Doe\" })"))
            }
        }
    }
    describe("Relay Edges print function") {
        given("edges with addtional field id") {
            val node = Edges(SelectionSet(listOf(Field("title"))), additionalField = listOf(Field("id")))
            it("should print edges { node { title } id }") {
                assertThat(node.print(false, 0), equalTo("edges { node { title } id }"))
            }
        }
        given("edges with id and title inside node object") {
            val node = Edges(SelectionSet(listOf(Field("id"), Field("title"))))
            it("should print edges { node { id title } }") {
                assertThat(node.print(false, 0), equalTo("edges { node { id title } }"))
            }
        }
    }
    describe("Relay PageInfo print function") {
        given("default pageInfo with hasNextPage and hasPreviousPage") {
            val node = PageInfo(SelectionSet(listOf(Field("hasNextPage"),Field("hasPreviousPage"))))
            it("should print pageInfo { hasNextPage hasPreviousPage }") {
                assertThat(node.print(false, 0), equalTo("pageInfo { hasNextPage hasPreviousPage }"))
            }
        }
    }
    describe("Relay Cursor Connection print function") {
        given("cursor cursorConnection named notes with title in node object and only 10 items") {
            val selectionSet = SelectionSet(listOf(Edges(SelectionSet(listOf(Field("title"))))))
            val argsNode = Argument(mapOf("first" to 10))
            val node = CursorConnection("notes", argsNode, selectionSet)
            it("should print notes(first: 10) { edges { node { title } } }") {
                assertThat(node.print(false, 0), equalTo("notes(first: 10) { edges { node { title } } }"))
            }
        }
        given("cursor cursorConnection named notes with title in node object and only next 10 items after cursor named 'abcd1234'") {
            val selectionSet = SelectionSet(listOf(Edges(SelectionSet(listOf(Field("title"))))))
            val argsNode = Argument(mapOf("first" to 10, "after" to "abcd1234"))
            val node = CursorConnection("notes", argsNode, selectionSet)
            it("should print notes(first: 10, before: \"abcd1234\") { edges { node { title } } }") {
                assertThat(node.print(false, 0), equalTo("notes(first: 10, after: \\\"abcd1234\\\") { edges { node { title } } }"))
            }
        }
        given("cursor cursorConnection named notes with title in node object and only last 10 items") {
            val selectionSet = SelectionSet(listOf(Edges(SelectionSet(listOf(Field("title"))))))
            val argsNode = Argument(mapOf("last" to 10))
            val node = CursorConnection("notes", argsNode, selectionSet)
            it("should print notes(last: 10) { edges { node { title } } }") {
                assertThat(node.print(false, 0), equalTo("notes(last: 10) { edges { node { title } } }"))
            }
        }
        given("cursor cursorConnection named notes with title in node object and only last 10 items before cursor named 'abcd1234'") {
            val selectionSet = SelectionSet(listOf(Edges(SelectionSet(listOf(Field("title"))))))
            val argsNode = Argument(mapOf("last" to 10, "before" to "abcd1234"))
            val node = CursorConnection("notes", argsNode, selectionSet)
            it("should print notes(last: 10, before: \"abcd1234\") { edges { node { title } } }") {
                assertThat(node.print(false, 0), equalTo("notes(last: 10, before: \\\"abcd1234\\\") { edges { node { title } } }"))
            }
        }
        given("cursor cursorConnection named notes with PageInfo object") {
            val pageNode = PageInfo(SelectionSet(listOf(Field("hasNextPage"), Field("hasPreviousPage"))))
            val selectionSet = SelectionSet(listOf(Edges(SelectionSet(listOf(Field("title")))), pageNode))
            val argsNode = Argument(mapOf("first" to 10))
            val node = CursorConnection("notes", argsNode, selectionSet)
            it("should print notes(first: 10) { edges { node { title } } pageInfo { hasNextPage hasPreviousPage } }") {
                assertThat(node.print(false, 0), equalTo("notes(first: 10) { edges { node { title } } pageInfo { hasNextPage hasPreviousPage } }"))
            }
        }
    }
})
