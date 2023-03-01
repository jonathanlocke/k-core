@file:Suppress("unused")

package io.kstar.receptors.values

fun interface ValueToString<Value>
{
    companion object
    {
        fun <T> callToString(): ValueToString<T> = ValueToString { value -> value.toString() }
    }

    fun toString(value: Value): String
}
