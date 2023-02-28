@file:Suppress("unused")

package io.kstar.core.language

object Doubles
{
    fun Double.format(digits: Int = 1) = "%.${digits}f".format(this)
}
