@file:Suppress("unused")

package io.kstar.annotations.documentation

import kotlin.annotation.AnnotationRetention.*
import kotlin.annotation.AnnotationTarget.*
import kotlin.reflect.KClass


/**
 * This annotation is used to indicate that a field or property references an aggregate object (an object that is
 * *dependent* on the referring field's class). By default, the aggregation relation arrow is included in all diagrams
 * without a label. A label can be added with [label] and the type and cardinality of the referent can be specified with
 * [referent] and [referentCardinality]. If omitted, they are not included.
 *
 * See:
 *
 *  * [Lexakai Documentation Tool](https://www.lexakai.org)
 *
 * @author Jonathan Locke
 */
@Repeatable
@Retention(RUNTIME)
@Target(FIELD, PROPERTY_GETTER)
annotation class UmlAggregation
(
    /**
     * Returns any diagram(s) that this annotation is specific to, or all diagrams if omitted
     */
    val inDiagrams: Array<KClass<out UmlDiagramIdentifier>> = [UmlDiagramAll::class],

    /**
     * Returns the association label
     */
    val label: String = "",

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