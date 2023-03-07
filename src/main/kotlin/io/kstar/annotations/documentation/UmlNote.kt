@file:Suppress("unused")

package io.kstar.annotations.documentation

import io.kstar.annotations.documentation.UmlAlignment.RIGHT
import kotlin.annotation.AnnotationRetention.RUNTIME
import kotlin.annotation.AnnotationTarget.*
import kotlin.reflect.KClass

/**
 * Adds a callout note to the annotated class, interface or method.
 *
 * The text for the note is provided with [value] and may include simple HTML tags per the PlantUML specification.
 * The [align] method provides any alignment for the callout. By default, notes are right-aligned.
 *
 * See:
 *
 *  * [Lexakai Documentation Tool](https://www.lexakai.org)
 *
 * @author Jonathan Locke
 */
@Repeatable
@Retention(RUNTIME)
@Target(CLASS, FUNCTION, PROPERTY_GETTER, PROPERTY_SETTER)
annotation class UmlNote
(
    /**
     * The note text
     */
    val value: String,

    /**
     * Returns any diagram(s) that this annotation is specific to, or all diagrams if omitted
     */
    val inDiagrams: Array<KClass<out UmlDiagramIdentifier>> = [UmlDiagramAll::class],

    /**
     * The alignment of the note
     */
    val align: UmlAlignment = RIGHT
)
