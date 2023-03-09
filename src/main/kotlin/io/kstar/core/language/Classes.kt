@file:Suppress("unused")

package io.kstar.core.language

import io.kstar.core.values.strings.CaseFormat.camelCaseToHyphenated
import kotlin.reflect.KClass

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

    /**
     * WebServer.class -> "web-server"
     *
     * @return The simple name of the given type in hyphenated form
     */
    fun KClass<*>.hyphenatedName(): String
    {
        return simpleName?.camelCaseToHyphenated() ?: "Unknown"
    }
}
