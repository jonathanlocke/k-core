@file:Suppress("unused")

package io.kstar.receptors.strings

fun interface ValueToString<Value>
{
    companion object
    {
        fun <T> callToString(): ValueToString<T> = ValueToString { value -> value.toString() }
    }

    fun toString(value: Value): String
}
