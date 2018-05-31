package me.lazmaid.kraph.lang

import me.lazmaid.kraph.KraphVariable

/**
 * Created by OinkIguana on 10/25/2017 AD.
 */

internal class Variables {

    val variables = mutableMapOf<String, KraphVariable>()

    fun print(): String? = variables
            .takeIf { it.isNotEmpty() }
            ?.let {
                it.entries.joinToString(separator = ",", prefix = "{", postfix = "}") { "\"${it.key}\": ${it.value.jsonValue}" }
            }

    fun asArgument(): Argument? =
            variables.takeIf { it.isNotEmpty() }
                    ?.let { Argument(it.values.map { it.dollarName to it.type }.toMap()) }
}
