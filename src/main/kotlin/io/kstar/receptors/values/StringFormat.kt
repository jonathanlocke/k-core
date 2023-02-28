@file:Suppress("unused")

package io.kstar.receptors.values

import io.kstar.annotations.quality.Documentation
import io.kstar.annotations.quality.Stability
import io.kstar.annotations.quality.Testing
import io.kstar.annotations.quality.TypeQuality
import io.kstar.core.language.Objects.equalsAny

/**
 * The type of format for a string
 */
@TypeQuality
(
    stability = Stability.STABLE_EXTENSIBLE,
    testing = Testing.TESTING_NOT_NEEDED,
    documentation = Documentation.DOCUMENTED
)
enum class StringFormat
{
    /** A format suitable for debug tracing  */
    DEBUG,

    /** A format with only characters valid across filesystems  */
    FILESYSTEM,

    /** A format for display in a browser  */
    HTML,

    /** A format most useful for programmatic use  */
    PROGRAMMATIC,

    /** A generic text format  */
    TEXT,

    /** The format produced by calling toString()  */
    TO_STRING,

    /** A format suitable for user presentation, allowing for multiple lines  */
    USER_MULTILINE,

    /** A format suitable for user presentation, allowing only single lines  */
    USER_SINGLE_LINE,

    /** A format suitable for display with a UI label  */
    USER_LABEL,

    /** A compact format  */
    COMPACT,

    /** A format suitable for log output  */
    LOG;

    val isHtml: Boolean get() = equals(HTML)
    val isText: Boolean get() = equals(TEXT)
    val isUser: Boolean get() = equalsAny(HTML, USER_LABEL, USER_MULTILINE, USER_SINGLE_LINE)
}
