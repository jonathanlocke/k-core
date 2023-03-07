@file:Suppress("unused")

package io.kstar.receptors.objects

import io.kstar.annotations.quality.Documentation.*
import io.kstar.annotations.quality.Stability.*
import io.kstar.annotations.quality.Testing.*
import io.kstar.annotations.quality.TypeQuality

/**
 * Listens to a model for changes in its internal state.
 *
 * @author Jonathan Locke
 */
@TypeQuality
(
    stability = STABLE,
    testing = TESTING_NOT_NEEDED,
    documentation = DOCUMENTED
)
fun interface ChangeListener<Model>
{
    /**
     * Called when a value changes
     *
     * @param model The model
     */
    fun onChanged(model: Model)
}
