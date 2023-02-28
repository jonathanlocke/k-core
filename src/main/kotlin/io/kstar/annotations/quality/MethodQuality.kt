@file:Suppress("unused")

package io.kstar.annotations.quality

import io.kstar.annotations.quality.Audience.*
import io.kstar.annotations.quality.Documentation.*
import io.kstar.annotations.quality.Stability.*
import io.kstar.annotations.quality.Testing.*
import kotlin.annotation.AnnotationRetention.*
import kotlin.annotation.AnnotationTarget.*

/**
 * Indicates the intended audience, documentation completeness and testing sufficiency of a method.
 * The [Lexakai](https://www.lexkai.org/) documentation tool uses this annotation to add code quality metrics to
 * automatically updated *README.md* markup files.
 *
 * **Example**
 *
 * ```
 * class MyClass
 * {
 *     @MethodQuality
 *     (
 *         audience = INTERNAL,
 *         documentation = DOCUMENTED,
 *         testing = TESTED
 *     )
 *     void myMethod()
 *     {
 *         [...]
 *     }
 * }
 * ```
 *
 * @author jonathanl (shibo)
 */
@Retention(SOURCE)
@Target
(
    FUNCTION,
    PROPERTY,
    PROPERTY_GETTER,
    PROPERTY_SETTER,
    CONSTRUCTOR
)
@TypeQuality
(
    audience = PUBLIC,
    documentation = DOCUMENTED,
    stability = STABLE
)
annotation class MethodQuality
(
    /** The audience for the annotated code. By default, all types are public */
    val audience: Audience = PUBLIC,

    /**
     * The subjective documentation quality, as evaluated by a developer. Until explicitly evaluated and
     * annotated, a type's [documentation] defaults to [DOCUMENTATION_UNDETERMINED].
     */
    val documentation: Documentation = DOCUMENTATION_UNDETERMINED,

    /**
     * Returns the level of testing provided based versus the level needed, as evaluated by a developer. Until
     * explicitly evaluated and annotated, a type's [testing] defaults to [TESTING_UNDETERMINED].
     */
    val testing: Testing = TESTING_UNDETERMINED
)