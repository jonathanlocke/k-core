@file:Suppress("unused")

package io.kstar.annotations.documentation

import kotlin.annotation.AnnotationRetention.RUNTIME
import kotlin.annotation.AnnotationTarget.*

/**
 * Puts the annotated method or property in the designated method group (even if heuristics suggest another group).
 * Method groups are delineated and labeled with the text provided by [value] in the method list of the UML
 * display for the containing type.
 *
 * See:
 *
 *  * [Lexakai Documentation Tool](https://www.lexakai.org)
 *
 * @author Jonathan Locke
 */
@Repeatable
@Retention(RUNTIME)
@Target(FUNCTION, PROPERTY_GETTER, PROPERTY_SETTER)
annotation class UmlMemberGroup
(
    /**
     * Returns the name of this method group
     */
    val value: String = ""
)
