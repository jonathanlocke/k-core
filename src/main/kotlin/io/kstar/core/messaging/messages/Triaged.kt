@file:Suppress("unused")

package io.kstar.core.messaging.messages

import io.kstar.annotations.documentation.UmlIncludeType
import io.kstar.annotations.documentation.UmlRelation
import io.kstar.annotations.quality.Documentation.DOCUMENTED
import io.kstar.annotations.quality.Stability.*
import io.kstar.annotations.quality.Testing.TESTING_NOT_NEEDED
import io.kstar.annotations.quality.TypeQuality
import io.kstar.internal.Diagrams.DiagramLogging
import io.kstar.internal.Diagrams.DiagramMessaging

/**
 * The source of a [Severity]
 *
 * @author Jonathan Locke
 */
@UmlIncludeType(inDiagrams = [DiagramLogging::class, DiagramMessaging::class])
@TypeQuality
(
    stability = STABLE,
    testing = TESTING_NOT_NEEDED,
    documentation = DOCUMENTED
)
interface Triaged
{
    /**
     * Returns the severity of this object
     */
    @UmlRelation(label = "has")
    fun severity(): Severity?
}