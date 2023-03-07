package io.kstar.core.messaging

import io.kstar.annotations.documentation.UmlIncludeType
import io.kstar.annotations.documentation.UmlRelation
import io.kstar.core.internal.Diagrams.DiagramMessaging


/**
 * A receiver of  [Transmittable] messages with similar function to a [Consumer], a sink, a callback or a
 * target.
 *
 *
 *
 * If the [.isReceiving] method returns true, then a call to [.receive] will result in a
 * call to [.onReceive].
 *
 *
 * @author Jonathan Locke
 * @see Transmittable
 */
@UmlIncludeType(inDiagrams = [DiagramMessaging::class])
@UmlRelation(label = "receives", referent = Transmittable::class)
fun interface Receiver
{
    /**
     * Returns true if this receiver is receiving
     */
    fun isReceiving(): Boolean = true

    /**
     * Method that receives a message
     *
     * @param message The message
     */
    fun onReceive(message: Transmittable)

    /**
     * **Not public API**
     *
     * If this transceiver is enabled, passes the message to [onReceive]
     *
     * @param message The transmittable message
     * @return The message
     */
    fun <T : Transmittable> receive(message: T): T
    {
        if (isReceiving())
        {
            onReceive(message)
        }
        return message
    }
}