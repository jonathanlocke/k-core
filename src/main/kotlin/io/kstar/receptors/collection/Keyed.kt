package io.kstar.receptors.collection

import io.kstar.annotations.quality.Documentation.DOCUMENTED
import io.kstar.annotations.quality.Stability.STABILITY_UNDETERMINED
import io.kstar.annotations.quality.Testing.TESTING_NOT_NEEDED
import io.kstar.annotations.quality.TypeQuality

/**
 * An object which can retrieve a value given a key.
 *
 * @param <Key> The type of key
 * @param <Value> The type of value
 * @author  Jonathan Locke
</Value></Key> */
@TypeQuality
(
    stability = STABILITY_UNDETERMINED,
    testing = TESTING_NOT_NEEDED,
    documentation = DOCUMENTED
)
fun interface Keyed<Key, Value>
{
    /**
     * Returns the value for the given key, or null if there is none
     */
    operator fun get(key: Key): Value?
}
