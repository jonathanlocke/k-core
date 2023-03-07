@file:Suppress("unused")

package io.kstar.annotations.documentation

import kotlin.annotation.AnnotationRetention.*
import kotlin.annotation.AnnotationTarget.*
import kotlin.reflect.KClass

/**
 * This annotation is used to indicate that the annotated type, field or method has a relation with the type specified
 * by [referent]. The relation can be labeled by providing a value for [label]. The cardinality of the
 * referee (the annotated type or enclosing type) may be specified with [refereeCardinality] and the
 * cardinality of the referent can be specified with [referentCardinality]. The relation is displayed in all
 * UML diagrams, unless a diagram is specified with [inDiagrams].
 *
 * See:
 *
 *  * [Lexakai Documentation Tool](https://www.lexakai.org)
 *
 * @author Jonathan Locke
 */
@Retention(RUNTIME)
@Repeatable
@Target(CLASS, FIELD, FUNCTION, PROPERTY_GETTER, PROPERTY_SETTER)
annotation class UmlRelation
(
    /**
     * Returns any diagram(s) that this annotation is specific to, or all diagrams if omitted
     */
    val inDiagrams: Array<KClass<out UmlDiagramIdentifier>> = [UmlDiagramAll::class],

    /**
     * Returns the relation association label
     */
    val label: String,

    /**
     * Returns the cardinality of the "from" end of the relation
     */
    val refereeCardinality: String = "",

    /**
     * Returns an optional explicit type to refer to, if one cannot be deduced from element this annotation is applied
     * to. For fields and methods, this value is generally not necessary as the member type will be used.
     */
    val referent: KClass<*> = Void::class,

    /**
     * Returns the cardinality of the "to" end of the relation
     */
    val referentCardinality: String = ""
)