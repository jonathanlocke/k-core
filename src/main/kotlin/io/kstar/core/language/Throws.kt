@file:Suppress("unused")

package io.kstar.core.language

object Throws
{
    fun illegalArgument(message: String = ""): Nothing
    {
        throw IllegalArgumentException(message)
    }
}