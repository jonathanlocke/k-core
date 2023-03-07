package io.kstar.core.messaging.messages.status.activity

import com.telenav.kivakit.annotations.code.quality.TypeQuality

/**
 * A message sent to indicate that an activity that might be monitored has occurred. For example, a resource (usually a
 * large resource) might have been allocated. This can be used to track, for example, memory consumption.
 *
 * @author Jonathan Locke
 */
@UmlIncludeType(diagram = DiagramMessageType::class)
@TypeQuality(stability = STABLE, testing = TESTING_NOT_NEEDED, documentation = DOCUMENTED)
class Step : Information
{
    constructor()
    constructor(message: String?, vararg arguments: Any?) : super(message, arguments)
    constructor(cause: Throwable?, message: String?, vararg arguments: Any?) : super(cause, message, arguments)

    /**
     * {@inheritDoc}
     */
    fun severity(): Severity
    {
        return LOW
    }

    /**
     * {@inheritDoc}
     */
    fun status(): Status
    {
        return SUCCEEDED
    }
}