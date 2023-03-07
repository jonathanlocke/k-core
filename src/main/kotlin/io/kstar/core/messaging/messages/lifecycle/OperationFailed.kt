package io.kstar.core.messaging.messages.lifecycle

import com.telenav.kivakit.annotations.code.quality.TypeQuality

/**
 * An operation has terminated in failure
 *
 * @author Jonathan Locke
 */
@UmlIncludeType(diagram = DiagramMessageType::class)
@TypeQuality(stability = STABLE, testing = TESTING_NOT_NEEDED, documentation = DOCUMENTED)
class OperationFailed : OperationLifecycleMessage
{
    constructor() : super("OperationFailed")
    protected constructor(message: String?, vararg arguments: Any?) : super(message, arguments)

    /**
     * {@inheritDoc}
     */
    fun operationStatus(): OperationStatus
    {
        return FAILED
    }
}