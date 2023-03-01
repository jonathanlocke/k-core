@file:Suppress("unused")

package io.kstar.annotations.change

import io.kstar.annotations.quality.*
import io.kstar.annotations.quality.Documentation.*
import io.kstar.annotations.quality.Stability.*
import kotlin.annotation.AnnotationRetention.*
import kotlin.annotation.AnnotationTarget.*

/**
 * Indicates an (incompatible) code change to a method.
 *
 *
 * **Example**
 *
 * ```
 * class MyClass
 * {
 *     @MethodChange
 *     (
 *         change = METHOD_ADDED,
 *         version = "1.8.0"
 *     )
 *     void myNewMethod()
 *     {
 *         [...]
 *     }
 *
 *     @MethodChange
 *     (
 *         change = METHOD_RENAMED,
 *         previously = "void myOldMethod()"
 *         version = "1.8.0"
 *     )
 *     void myRenamedMethod()
 *     {
 *         [...]
 *     }
 * }
 * ```
 *
 * @author  Jonathan Locke
 */
@Retention(SOURCE)
@Target
(
    FUNCTION,
    PROPERTY,
    PROPERTY_GETTER,
    PROPERTY_SETTER
)
@Repeatable
@TypeQuality
(
    stability = STABILITY_UNDETERMINED,
    documentation = DOCUMENTED,
    reviewers = ["Jonathan Locke"]
)
annotation class MethodChange
(
    /** The change */
    val change: Change,

    /** Any previous name of the method, if it was renamed */
    val previousName: String = "",

    /** Any previous signature of the method, if its signature changed */
    val previousSignature: String = "",

    /** The version when the change occurred */
    val version: String
)
{
    /** The type of change that occurred */
    @TypeQuality
    (
        stability = STABLE_EXTENSIBLE,
        documentation = DOCUMENTED
    )
    enum class Change
    {
        /** The annotated method was added  */
        METHOD_ADDED,

        /** The annotated method was renamed from [previousName] */
        METHOD_RENAMED,

        /** The annotated method changed signature from [previousSignature] */
        METHOD_CHANGED_SIGNATURE
    }
}