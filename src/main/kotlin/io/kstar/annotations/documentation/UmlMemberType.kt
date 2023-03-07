@file:Suppress("unused")

package io.kstar.annotations.documentation

/**
 * Designates a type of member
 *
 * @author Jonathan Locke
 */
enum class UmlMemberType
{
    PUBLIC_MEMBERS,         // fun
    OPEN_METHODS,           // open fun
    OVERRIDDEN_METHODS,     // override fun
    PROTECTED_MEMBERS       // protected fun
}
