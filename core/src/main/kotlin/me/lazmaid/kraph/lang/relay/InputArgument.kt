package me.lazmaid.kraph.lang.relay

import me.lazmaid.kraph.lang.Argument

/**
 * Created by VerachadW on 10/2/2016 AD.
 */

internal class InputArgument(args: Map<String, Any>) : Argument(args) {
    override fun print(
        prettyFormat: Boolean,
        escapeStrings: Boolean,
        previousLevel: Int
    ): String {
        return "(input: { ${print(args, prettyFormat, escapeStrings)} })"
    }
}
