@file:Suppress("unused")

package io.kstar.annotations.documentation

/**
 * This interface is used as a base interface for diagram identifier types (see [UmlIncludeType.inDiagram]). Diagram
 * identifiers are normally placed in a package called *internal.lexakai* so they are easy to find, and they should start
 * with the word "Diagram". For example:
 *
 * ```
 * interface ApplicationDiagram : UmlDiagramIdentifier
 * {
 * }
 *
 * @UmlIncludeType(diagram = ApplicationDiagram.class)
 * class MyApplication
 * {
 *     [...]
 * }
 * ```
 *
 * See:
 *
 *  * [Lexakai Documentation Tool](https://www.lexakai.org)
 *
 * @author Jonathan Locke
 */
interface UmlDiagramIdentifier
