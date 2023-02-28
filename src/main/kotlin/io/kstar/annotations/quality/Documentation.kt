@file:Suppress("MemberVisibilityCanBePrivate", "unused")

package io.kstar.annotations.quality

import io.kstar.annotations.quality.Documentation.DOCUMENTED
import io.kstar.annotations.quality.Stability.STABILITY_UNDETERMINED
import io.kstar.annotations.quality.Testing.TESTING_NOT_NEEDED

/**
 * An evaluation of the quality of documentation for this class, as determined by a developer. Some classes need more
 * documentation, some less, so subjective opinion is necessary to determine documentation quality level.
 *
 * @author jonathanl (shibo)
 * @see TypeQuality
 */
@TypeQuality
(
    stability = STABILITY_UNDETERMINED,
    testing = TESTING_NOT_NEEDED,
    documentation = DOCUMENTED,
    reviewers = ["shibo"]
)
enum class Documentation
{
    /** Documentation is complete  */
    DOCUMENTED,

    /** Some documentation is available, but more is required  */
    DOCUMENTATION_INSUFFICIENT,

    /** No documentation is available  */
    UNDOCUMENTED,

    /** Documentation status has not been evaluated  */
    DOCUMENTATION_UNDETERMINED,

    /** The method is trivial and needs no documentation  */
    DOCUMENTATION_NOT_NEEDED;

    val isDocumented: Boolean get() = this == DOCUMENTED || this == DOCUMENTATION_NOT_NEEDED
}
