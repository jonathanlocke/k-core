package io.kstar.core.messaging.messages.lifecycle

import com.telenav.kivakit.annotations.code.quality.TypeQuality

/**
 * A message representing the success of an operation
 *
 * @author Jonathan Locke
 */
@UmlIncludeType(diagram = DiagramMessageType::class)
@TypeQuality(stability = STABLE, testing = TESTING_NOT_NEEDED, documentation = DOCUMENTED)
class OperationSucceeded : OperationLifecycleMessage
{
    constructor() : super("OperationSucceeded")
    protected constructor(message: String?, vararg arguments: Any?) : super(message, arguments)

    /**
     * {@inheritDoc}
     */
    fun operationStatus(): OperationStatus
    {
        return SUCCEEDED
    }
}