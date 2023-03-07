package io.kstar.core.messaging.filters

import com.telenav.kivakit.annotations.code.quality.TypeQuality

/**
 * A filter that matches messages equal to or subclassing the given type.
 *
 * @author Jonathan Locke
 */
@TypeQuality(stability = STABLE, testing = UNTESTED, documentation = DOCUMENTED)
class MessagesOfType<MessageType : Message?>(
        /** The type of message to include  */
        private val type: Class<MessageType>) : Filter<MessageType>
{
    /**
     * {@inheritDoc}
     */
    fun accepts(message: MessageType?): Boolean
    {
        return message?.getClass()?.isAssignableFrom(type) ?: false
    }

    override fun toString(): String
    {
        return type.simpleName
    }
}