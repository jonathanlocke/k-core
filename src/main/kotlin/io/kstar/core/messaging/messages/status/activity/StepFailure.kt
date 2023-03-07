package io.kstar.core.messaging.messages.status.activity

import com.telenav.kivakit.annotations.code.quality.TypeQuality

/**
 * The current step failed to produce any useful result
 *
 * @author Jonathan Locke
 */
@UmlIncludeType(diagram = DiagramMessageType::class)
@TypeQuality(stability = STABLE, testing = TESTING_NOT_NEEDED, documentation = DOCUMENTED)
class StepFailure : Problem
{
    constructor(message: String?, vararg arguments: Any?) : this(null, message, *arguments)
    constructor(cause: Throwable?, message: String?, vararg arguments: Any?) : super(cause, message, arguments)
    constructor()

    /**
     * {@inheritDoc}
     */
    fun severity(): Severity
    {
        return HIGH
    }

    /**
     * {@inheritDoc}
     */
    fun status(): Status
    {
        return FAILED
    }
}