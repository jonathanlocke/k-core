package io.kstar.core.messaging.messages.status.activity

import com.telenav.kivakit.annotations.code.quality.TypeQuality

/**
 * The current step had to discard some result to continue
 *
 * @author Jonathan Locke
 */
@UmlIncludeType(diagram = DiagramMessageType::class)
@TypeQuality(stability = STABLE, testing = TESTING_NOT_NEEDED, documentation = DOCUMENTED)
class StepIncomplete : Warning
{
    constructor(message: String?, vararg arguments: Any?) : this(Throwable(), message, *arguments)
    constructor(cause: Throwable?, message: String?, vararg arguments: Any?) : super(cause, message, arguments)
    constructor()

    /**
     * {@inheritDoc}
     */
    fun severity(): Severity
    {
        return MEDIUM
    }

    /**
     * {@inheritDoc}
     */
    fun status(): Status
    {
        return RESULT_INCOMPLETE
    }
}