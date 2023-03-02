@file:Suppress("unused")

package io.kstar.receptors.numeric

/**
 * Interface to an object that is numbered. For example, a file is numbered from line 1 to N
 *
 * @author Jonathan Locke
 */
interface Numbered
{
    fun number(): Int
}
