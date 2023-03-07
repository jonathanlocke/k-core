@file:Suppress("unused")

package io.kstar.annotations.documentation

import kotlin.annotation.AnnotationRetention.RUNTIME
import kotlin.annotation.AnnotationTarget.*
import kotlin.reflect.KClass


/**
 * Includes the specified types in the diagram for the annotated type.
 *
 * See:
 *
 *  * [Lexakai Documentation Tool](https://www.lexakai.org)
 *
 * @author Jonathan Locke
 */
@Retention(RUNTIME)
@Target(CLASS)
@Repeatable
annotation class UmlIncludeRelatedType
(
    /**
     * A related type to include in the diagram of the annotated type
     */
    val value: KClass<*>,

    /**
     * Returns any diagram(s) that this annotation is specific to, or all diagrams if omitted
     */
    val inDiagrams: Array<KClass<out UmlDiagramIdentifier>> = [UmlDiagramAll::class],
)
