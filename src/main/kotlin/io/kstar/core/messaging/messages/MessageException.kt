package io.kstar.core.messaging.messages

import com.telenav.kivakit.annotations.code.quality.TypeQuality

/**
 * An exception thrown when exception chaining might not be desired.
 *
 * @author Jonathan Locke
 */
@Suppress("unused")
@TypeQuality(stability = STABLE, testing = TESTING_NOT_NEEDED, documentation = DOCUMENTED)
open class MessageException(message: Message) : RuntimeException()
{
    private override val message: Message

    init
    {
        this.message = message
    }

    fun message(): Message
    {
        return message
    }

    override fun toString(): String
    {
        return message.toString()
    }
}