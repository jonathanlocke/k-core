package io.kstar.annotations.change

import io.kstar.annotations.quality.*
import io.kstar.annotations.quality.Documentation.*
import io.kstar.annotations.quality.Stability.*
import io.kstar.annotations.quality.Testing.*
import kotlin.annotation.AnnotationRetention.*
import kotlin.annotation.AnnotationTarget.*

/**
 * Indicates an (incompatible) code change to a type.
 *
 * **Example**
 *
 * ```
 * @TypeChange
 * (
 *     change = TYPE_RENAMED,
 *     previousName = "myOldMethod",
 *     version = "1.8.0"
 * )
 * class MyClass
 * {
 *     [...]
 * }
 * ```
 *
 * @author jonathanl (shibo)
 */
@Suppress("unused")
@Retention(SOURCE)
@Target(CLASS)
@Repeatable
@TypeQuality
(
    stability = STABILITY_UNDETERMINED,
    testing = TESTING_NOT_NEEDED,
    documentation = DOCUMENTED,
    reviewers = ["shibo"]
)
annotation class TypeChange
(
    /** The change */
    val change: Change,

    /** The type that was renamed or moved */
    val previousName: String = "",

    /** The type that was renamed or moved */
    val previousLocation: String = "",

    /** Returns the version when the change occurred */
    val version: String
)
{
    /**
     * The type of change that occurred
     */
    @TypeQuality
    (
        stability = STABLE_EXTENSIBLE,
        testing = TESTING_NOT_NEEDED,
        documentation = DOCUMENTED
    )
    enum class Change
    {
        /** The annotated type was added  */
        TYPE_ADDED,

        /** The annotated type was renamed from its [previousName] */
        TYPE_RENAMED,

        /** The annotated type was moved from its [previousLocation] */
        TYPE_MOVED
    }
}