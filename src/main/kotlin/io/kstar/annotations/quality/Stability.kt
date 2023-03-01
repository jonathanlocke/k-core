@file:Suppress("MemberVisibilityCanBePrivate", "unused")

package io.kstar.annotations.quality

import io.kstar.annotations.quality.Audience.PUBLIC
import io.kstar.annotations.quality.Documentation.DOCUMENTED
import io.kstar.annotations.quality.Stability.STABILITY_UNDETERMINED

/**
 * The level of stability for a class or interface, as evaluated by a developer. This is different from a measure of
 * past source code change because it is future-looking. It is based on the *anticipated* level of
 * *incompatible* change in the future.
 *
 * @author  Jonathan Locke
 * @see TypeQuality
 */
@TypeQuality
(
    stability = STABILITY_UNDETERMINED,
    documentation = DOCUMENTED,
    audience = PUBLIC,
    reviewers = ["Jonathan Locke"]
)
enum class Stability
{
    /** The annotated type is not expected to change  */
    STABLE,

    /**
     * The type is not expected to change, except that new methods may be added. Adding new methods to a type can
     * sometimes cause problems with compatibility if a method with the same name already exists in a subtype.
     */
    STABLE_EXTENSIBLE,

    /**
     * It is expected that this type will change incompatibly in the future. Using this type may require some
     * refactoring or other code adaptation in the future.
     */
    UNSTABLE,

    /** The stability of this type requires more evaluation  */
    STABILITY_UNDETERMINED;

    val isStable: Boolean get() = this == STABLE || this == STABLE_EXTENSIBLE

    val isUnstable: Boolean get() = !isStable
}
