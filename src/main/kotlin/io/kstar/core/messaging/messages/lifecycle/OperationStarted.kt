package io.kstar.core.messaging.messages.lifecycle

import com.telenav.kivakit.annotations.code.quality.TypeQuality

/**
 * An operation has started
 *
 * @author Jonathan Locke
 */
@UmlIncludeType(diagram = DiagramMessageType::class)
@TypeQuality(stability = STABLE, testing = TESTING_NOT_NEEDED, documentation = DOCUMENTED)
class OperationStarted : OperationLifecycleMessage
{
    constructor() : super("OperationStarted")
    protected constructor(message: String?, vararg arguments: Any?) : super(message, arguments)

    /**
     * {@inheritDoc}
     */
    fun operationStatus(): OperationStatus
    {
        return STARTED
    }
}