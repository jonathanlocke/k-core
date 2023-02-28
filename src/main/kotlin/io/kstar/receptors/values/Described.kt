@file:Suppress("unused")

package io.kstar.receptors.values

/**
 * Interface for objects that have a description
 *
 * @author Jonathan Locke
 */
interface Described
{
    /**
     * Returns the description of this object
     *
     * @return the description
     */
    fun description(): String
}
