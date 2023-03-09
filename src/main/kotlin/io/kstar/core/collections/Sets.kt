@file:Suppress("unused")

package io.kstar.core.collections

import io.kstar.annotations.quality.Documentation
import io.kstar.annotations.quality.Stability
import io.kstar.annotations.quality.Testing
import io.kstar.annotations.quality.TypeQuality
import io.kstar.core.collections.Collections.addIfNotNull
import io.kstar.receptors.objects.Factory
import java.util.*
import java.util.function.Function

/**
 * Set utility methods for working on sets
 *
 * @author jonathanl (shibo)
 */
@TypeQuality
(
    stability = Stability.STABLE_EXTENSIBLE,
    testing = Testing.UNTESTED,
    documentation = Documentation.DOCUMENTED
)
object Sets
{
    /**
     * Returns a copy of this set created by the given factory, where each value is cloned by the given
     * function.
     *
     * ```
     *  var copy = users.deepCopy(HashSet::new, User::new);
     * ```
     *
     * @param factory Creates a new set
     * @param cloner The function to clone values
     * @return A deep copy of the given set
     */
    fun <Value> Set<Value>.deepCopy(factory: Factory<MutableSet<Value>>,
                                    cloner: Function<Value, Value>): Set<Value>
    {
        val copy = factory.new()
        for (value in this)
        {
            copy.addIfNotNull(cloner.apply(value))
        }
        return copy.toSet()
    }
}