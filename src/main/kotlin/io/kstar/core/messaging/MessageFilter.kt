package io.kstar.core.messaging

import com.telenav.kivakit.annotations.code.quality.TypeQuality

/**
 * Filters [Transmittable] objects down to [Message]s only
 *
 * @author Jonathan Locke
 */
@UmlIncludeType(diagram = DiagramMessaging::class)
@TypeQuality(stability = STABLE_EXTENSIBLE, testing = TESTING_NOT_NEEDED, documentation = DOCUMENTED)
interface MessageFilter : Filter<Transmittable?>
{
    fun accepts(message: Transmittable?): Boolean
    {
        // If the transmittable is a message,
        return if (message is Message)
        {
            // then return, whether or not we accept it.
            accepts(message as Message?)
        }
        else false
    }

    /**
     * Returns true if the message is accepted by this filter
     */
    fun accepts(message: Message?): Boolean
    {
        return false
    }
}