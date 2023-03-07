package io.kstar.core.messaging.messages

import com.telenav.kivakit.annotations.code.quality.TypeQuality

/**
 * The source of a [Severity]
 *
 * @author Jonathan Locke
 */
@UmlIncludeType(diagram = DiagramLogging::class)
@UmlIncludeType(diagram = DiagramMessaging::class)
@TypeQuality(stability = STABLE, testing = TESTING_NOT_NEEDED, documentation = DOCUMENTED)
interface Triaged
{
    /**
     * Returns the severity of this object
     */
    @UmlRelation(label = "has")
    fun severity(): Severity?
}