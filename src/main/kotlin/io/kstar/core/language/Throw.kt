@file:Suppress("unused")

package io.kstar.core.language

object Throw
{
    fun illegalArgument(message: String = ""): Nothing
    {
        throw IllegalArgumentException(message)
    }

    fun illegalArgument(cause: Exception, message: String = ""): Nothing
    {
        throw IllegalArgumentException(message, cause)
    }

    fun illegalState(message: String = ""): Nothing
    {
        throw IllegalStateException(message)
    }

    fun illegalState(cause: Exception, message: String = ""): Nothing
    {
        throw IllegalStateException(message, cause)
    }

    fun fail(message: String = ""): Nothing
    {
        throw RuntimeException(message)
    }
}