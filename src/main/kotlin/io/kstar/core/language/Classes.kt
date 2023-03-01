@file:Suppress("unused")

package io.kstar.core.language

object Classes
{
    fun <T : Any> T.className(): String
    {
        return this::class.simpleName ?: "Unknown"
    }

    fun <T : Any> T.qualifiedClassName(): String
    {
        return this::class.qualifiedName ?: "Unknown"
    }
}
