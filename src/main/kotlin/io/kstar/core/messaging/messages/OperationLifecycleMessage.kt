package io.kstar.core.messaging.messages

import com.telenav.kivakit.annotations.code.quality.TypeQuality

/**
 * A message representing a state transition in the lifecycle of an operation. For example, an operation may have failed
 * or succeeded or someone may need to be alerted of a serious problem.
 *
 * @author Jonathan Locke
 * @see OperationStarted
 *
 * @see OperationSucceeded
 *
 * @see OperationFailed
 *
 * @see OperationHalted
 */
@UmlIncludeType(diagram = DiagramMessageType::class)
@TypeQuality(stability = STABLE, testing = TESTING_NOT_NEEDED, documentation = DOCUMENTED)
abstract class OperationLifecycleMessage : OperationMessage
{
    protected constructor()
    protected constructor(message: String?, vararg arguments: Any?) : super(message)
    {
        arguments(arguments)
    }

    /**
     * {@inheritDoc}
     */
    fun status(): Message.Status
    {
        return NOT_APPLICABLE
    }
}