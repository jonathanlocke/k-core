package io.kstar.core.messaging.messages.lifecycle

import com.telenav.kivakit.annotations.code.quality.TypeQuality

/**
 * A problem with severity high enough to halt the current operation. If the problem is not severe enough,
 * [Problem] should be used instead.
 *
 * @author Jonathan Locke
 */
@UmlIncludeType(diagram = DiagramMessageType::class)
@TypeQuality(stability = STABLE, testing = TESTING_NOT_NEEDED, documentation = DOCUMENTED)
class OperationHalted : OperationLifecycleMessage
{
    constructor()
    constructor(message: String?, vararg arguments: Any?) : super(message)
    {
        cause(Throwable("OperationHalted error"))
        arguments(arguments)
    }

    constructor(cause: Throwable?, message: String?, vararg arguments: Any?) : super(message)
    {
        cause(cause)
        arguments(arguments)
    }

    /**
     * {@inheritDoc}
     */
    fun operationStatus(): OperationStatus
    {
        return HALTED
    }

    /**
     * {@inheritDoc}
     */
    fun severity(): Severity
    {
        return HIGH
    }
}