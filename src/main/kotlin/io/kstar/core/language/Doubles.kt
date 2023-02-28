@file:Suppress("unused", "UnusedReceiverParameter")

package io.kstar.core.language

object Doubles
{
    fun Double.format(digits: Int): String = java.lang.String.format("%.${digits}f", this)
}
