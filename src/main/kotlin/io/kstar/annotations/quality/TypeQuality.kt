@file:Suppress("unused")

package io.kstar.annotations.quality

import io.kstar.annotations.quality.Audience.*
import io.kstar.annotations.quality.Documentation.*
import io.kstar.annotations.quality.Stability.*
import io.kstar.annotations.quality.Testing.*
import kotlin.annotation.AnnotationRetention.*
import kotlin.annotation.AnnotationTarget.*

/**
 * Indicates the intended audience, subjective stability, documentation completeness, testing sufficiency and code
 * review process of a type (class, record or interface). The [Lexakai](https://www.lexkai.org/)
 * documentation tool uses this annotation to add code quality metrics to automatically updated *README.md* markup
 * files.
 *
 * **Example**
 *
 * ```
 * @TypeQuality
 * (
 *     audience = INTERNAL,
 *     documentation = DOCUMENTED,
 *     stability = STABLE,
 *     reviews = 1,
 *     reviewers = ["shibo"]
 * )
 * class MyClass
 * {
 *     [...]
 * }
 * ```
 *
 * @author jonathanl (shibo)
 */
@Retention(SOURCE)
@Target(CLASS)
@TypeQuality
(
    audience = PUBLIC,
    stability = STABLE,
    documentation = DOCUMENTED
)
annotation class TypeQuality
(
    /** Returns the audience for the annotated code. By default, all types are [PUBLIC]. */
    val audience: Audience = PUBLIC,

    /**
     * Returns the subjective documentation quality, as evaluated by a developer. Until explicitly
     * evaluated and annotated, a type's [documentation] defaults to [DOCUMENTATION_UNDETERMINED].
     */
    val documentation: Documentation = DOCUMENTATION_UNDETERMINED,

    /**
     * Returns the subjective likelihood of future code stability, as evaluated by a developer. Until explicitly
     * evaluated and annotated, a type's [stability] defaults to [STABILITY_UNDETERMINED].
     */
    val stability: Stability = STABILITY_UNDETERMINED,

    /**
     * Returns the level of testing provided based versus the level needed, as evaluated by a developer. Until
     * explicitly evaluated and annotated, a type's [testing] defaults to [TESTING_UNDETERMINED].
     */
    val testing: Testing = TESTING_UNDETERMINED,

    /** Returns an array of reviewers, denoted by name, email or username. */
    val reviewers: Array<String> = []
)