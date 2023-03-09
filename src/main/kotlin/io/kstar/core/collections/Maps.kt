@file:Suppress("unused")

package io.kstar.core.collections

import io.kstar.annotations.quality.Documentation.DOCUMENTED
import io.kstar.annotations.quality.Stability.STABLE_EXTENSIBLE
import io.kstar.annotations.quality.Testing.UNTESTED
import io.kstar.annotations.quality.TypeQuality
import io.kstar.receptors.objects.Factory
import java.util.function.Function

/**
 * Methods for working with maps.
 *
 * @author jonathanl (shibo)
 */
@TypeQuality
(
    stability = STABLE_EXTENSIBLE,
    testing = UNTESTED,
    documentation = DOCUMENTED
)
object Maps
{
    /**
     * Returns a copy of this map created by the given factory, where each value is cloned by the given
     * function. Map keys are not cloned.
     *
     * ```
     * var copy = users.deepCopy(LinkedHashMap::new, User::new);
     * ```
     *
     * @param factory Creates a new map
     * @param cloner The function to clone values
     * @return A deep copy of the given map
     */
    fun <Key, Value> Map<Key, Value>.deepCopy(factory: Factory<MutableMap<Key, Value>>,
                                              cloner: Function<Value, Value>): Map<Key, Value>
    {
        val copy = factory.new()
        for (key in keys)
        {
            val value = this[key]
            if (value != null)
            {
                copy[key] = cloner.apply(value)
            }
        }
        return copy.toMap()
    }
}
