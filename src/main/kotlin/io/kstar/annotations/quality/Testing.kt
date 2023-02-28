@file:Suppress("MemberVisibilityCanBePrivate", "unused")

package io.kstar.annotations.quality

import io.kstar.annotations.quality.Documentation.DOCUMENTED
import io.kstar.annotations.quality.Stability.STABILITY_UNDETERMINED
import io.kstar.annotations.quality.Testing.TESTING_NOT_NEEDED

/**
 * The quality of tests for a given class, as evaluated by a developer. This is different from a simple code coverage
 * metric because there are many methods and even whole classes that do not need full testing. Some don't need testing
 * at all, for example, most interfaces.
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
enum class Testing
{
    /** All needed tests are implemented  */
    TESTED,

    /** No tests are needed  */
    TESTING_NOT_NEEDED,

    /** Some tests are implemented, but more are required  */
    TESTING_INSUFFICIENT,

    /** No tests have been implemented  */
    UNTESTED,

    /** Testing status has not been evaluated  */
    TESTING_UNDETERMINED;

    val isTested: Boolean get() = this == TESTED || this == TESTING_NOT_NEEDED

    val isUntested: Boolean get() = !isTested
}