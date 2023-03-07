@file:Suppress("unused")

package io.kstar.annotations.documentation

import io.kstar.annotations.documentation.UmlMemberType.*
import kotlin.annotation.AnnotationRetention.RUNTIME
import kotlin.annotation.AnnotationTarget.CLASS
import kotlin.reflect.KClass

/**
 * Indicates that the annotated type should be included in a UML diagram. For full details, see the
 * [Lexakai Documentation Tool](https://www.lexakai.org)
 *
 * **Diagram Name**
 *
 * The name of the diagram is derived from the class provided by [inDiagram] by taking the class name and
 * converting it to lowercase hyphenated form. For example, the class `MyDiagram` would have the diagram
 * name *my-diagram*.
 *
 * **Automatic Method Groups**
 *
 * Method groups can be automatically determined based on simple pattern matching. The [automaticMemberGroups]
 * value, which is true by default, determines if methods should be automatically grouped.
 *
 * **Inclusions**
 *
 * Related types, supertypes and members can be included with:
 *
 *  * [includeMembers] - The specified kinds of members will be included in the diagram
 *                       (default includes public and overridable members)
 *
 * **Explicit Relations**
 *
 * UML relations can be explicitly defined with @[UmlRelation] annotation(s) provided by [relations].
 * Relations are explicit when defined in this way, otherwise they may be inferred from member types.
 *
 * See:
 *
 *  * [Lexakai Documentation Tool](https://www.lexakai.org)
 *
 * @author Jonathan Locke
 * @see UmlRelation
 */
@Retention(RUNTIME)
@Target(CLASS)
@Repeatable
annotation class UmlIncludeType
(
    /**
     * The diagram(s) that the annotated type should be included in
     */
    val inDiagrams: Array<KClass<out UmlDiagramIdentifier>> = [UmlDiagramAll::class],

    /**
     * The kinds of members to include in the given diagram
     */
    val includeMembers: Array<out UmlMemberType> = [PUBLIC_MEMBERS, OPEN_METHODS],

    /**
     * Returns true if members should be grouped automatically based on guesses about method names
     */
    val automaticMemberGroups: Boolean = true,

    /**
     * Returns explicit UML relations for this diagram only
     */
    val relations: Array<UmlRelation> = []
)
