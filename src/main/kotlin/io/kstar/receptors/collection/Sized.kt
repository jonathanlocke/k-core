package io.kstar.receptors.collection

import io.kstar.annotations.quality.Documentation.DOCUMENTED
import io.kstar.annotations.quality.Stability.STABILITY_UNDETERMINED
import io.kstar.annotations.quality.Testing.TESTING_NOT_NEEDED
import io.kstar.annotations.quality.TypeQuality
import io.kstar.core.values.Count

/**
 * An object (usually some sort of collection or store of values) which has a [.size]. The object can be
 * [.isEmpty] if the size is zero or [.isNonEmpty] if the size is non-zero.
 *
 * @author  Jonathan Locke
 */
@TypeQuality
(
    stability = STABILITY_UNDETERMINED,
    testing = TESTING_NOT_NEEDED,
    documentation = DOCUMENTED
)
interface Sized : Emptiness
{
    /**
     * Returns true if the size of this object is zero
     */
    override fun isEmpty(): Boolean = size() == 0

    /**
     * Gets the size of this object
     *
     * @return The size of this object
     */
    fun size(): Int

    fun count(): Count = Count(size())
}
