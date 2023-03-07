@file:Suppress("unused")

package io.kstar.annotations.documentation

import kotlin.annotation.AnnotationRetention.RUNTIME
import kotlin.annotation.AnnotationTarget.*
import kotlin.reflect.KClass

/**
 * Includes the annotated field, method or constructor in all diagrams that include the
 * enclosing type
 *
 * See:
 *
 *  * [Lexakai Documentation Tool](https://www.lexakai.org)
 *
 * @author Jonathan Locke
 */
@Repeatable
@Retention(RUNTIME)
@Target(FIELD, FUNCTION, PROPERTY_GETTER, PROPERTY_SETTER, CONSTRUCTOR)
annotation class UmlIncludeMember
(
    /**
     * Returns any diagrams that the annotated member should be included in, by default all diagrams
     */
    val inDiagrams: Array<KClass<out UmlDiagramIdentifier>> = [UmlDiagramAll::class],
)
