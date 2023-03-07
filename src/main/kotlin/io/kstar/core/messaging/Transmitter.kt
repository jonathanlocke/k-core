package io.kstar.core.messaging

import io.kstar.annotations.documentation.UmlIncludeType
import io.kstar.annotations.documentation.UmlRelation
import io.kstar.core.internal.Diagrams.DiagramMessaging

/**
 * A transmitter of [Transmittable] messages
 *
 * If the [isTransmitting] method returns true, then a call to [transmit] will result in
 * a call to [onTransmit].
 *
 * @author Jonathan Locke
 * @see Transmittable
 */
@UmlIncludeType(inDiagrams = [DiagramMessaging::class])
@UmlRelation(label = "transmits", referent = Transmittable::class)
interface Transmitter
{
    /**
     * Returns true if this transmitter is enabled
     */
    fun isTransmitting(): Boolean = true

    /**
     * Turns off transmission of messages by this transmitter
     */
    fun silence() = enableTransmission(false)

    /**
     * Turns the transmission of messages by this transmitter on or off
     */
    fun enableTransmission(enable: Boolean = true)

    /**
     * Implementation that transmits a message
     *
     * @param message The message to transmit
     */
    fun onTransmit(message: Transmittable)
    {
    }

    /**
     * **Not public API**
     *
     * If this transmitter is enabled, passes the message to [onTransmit] to transmit it
     *
     * @param message The message
     * @param <MessageType> The type of message
     * @return The message
     */
    fun <T : Transmittable> transmit(message: T): T
    {
        if (isTransmitting())
        {
            onTransmit(message)
        }
        return message
    }
}