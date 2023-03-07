package io.kstar.core.messaging.listeners

import com.telenav.kivakit.annotations.code.quality.TypeQuality

/**
 * Listens to [Message]s of a given type. All problems are logged. All failure messages result in an exception
 * being thrown. All other messages are ignored.
 *
 * @author Jonathan Locke
 * @see Multicaster
 *
 * @see ThrowingListenerException
 */
@UmlIncludeType(diagram = DiagramListenerType::class)
@TypeQuality(stability = STABLE, testing = UNTESTED, documentation = DOCUMENTED)
class ThrowingListener : Listener
{
    fun onMessage(message: Message)
    {
        // If the message is a failure,
        if (message.isFailure())
        {
            // we throw a special exception that circumvents the trapping of exceptions
            // in Multicaster, which otherwise would ensure that all listeners get a
            // message delivered.
            throw ThrowingListenerException(message)
        }
    }
}