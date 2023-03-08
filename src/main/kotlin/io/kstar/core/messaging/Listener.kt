package io.kstar.core.messaging

import io.kstar.annotations.documentation.UmlIncludeType
import io.kstar.annotations.quality.Documentation.DOCUMENTED
import io.kstar.annotations.quality.Stability.*
import io.kstar.annotations.quality.Testing.*
import io.kstar.annotations.quality.TypeQuality
import io.kstar.internal.Diagrams.DiagramBroadcaster
import io.kstar.internal.Diagrams.DiagramListener
import io.kstar.internal.Diagrams.DiagramRepeater


/**
 * Handles messages through [.onMessage].
 *
 *
 * **Listening to Broadcasters**
 *
 *
 *
 * A listener can listen to a particular [Broadcaster] with [.listenTo]. Conversely, a listener
 * can be added to a broadcaster with [Broadcaster.addListener]. Both methods achieve the same result.
 *
 *
 *
 * **Convenience Methods and Logging**
 *
 *
 *
 * A number of convenience methods in the [Transceiver] superinterface make it easy to transmit common messages to
 * listeners. For example, [.problem] is inherited by [Broadcaster]s, [Repeater]s
 * and Components, making it easy to transmit [Problem] messages:
 *
 *
 * <pre>
 * problem("Unable to read $", file);</pre>
 *
 *
 *
 * **Repeater Chains**
 *
 *
 *
 * In the example below, EmployeeLoader is a [Repeater] which transmits a warning to all of its registered
 * listeners. The PayrollProcessor class is also a [Repeater] which listens to messages transmitted by the
 * EmployeeLoader and re-transmits them to its own listeners. Clients of the PayrollProcessor can listen to it in turn,
 * and they will receive the [Problem] transmitted EmployeeLoader, when it is repeated by the PayrollProcessor.
 *
 * <pre>
 * class EmployeeLoader extends BaseRepeater
 * {
 *
 * [...]
 *
 * problem("Unable to load $", employee);
 *
 * [...]
 * }
 *
 * class PayrollProcessor extends BaseRepeater
 * {
 *
 * [...]
 *
 * var loader = listenTo(new EmployeeLoader());
 *
 * [...]
 *
 * }
 *
 * var processor = LOGGER.listenTo(new PayrollProcessor());</pre>
 *
 *
 *
 * This pattern creates a chain of repeaters that terminates in one or more listeners. The final listener is often, but
 * not always a [Logger]:
 *
 *
 *
 *
 * &nbsp;&nbsp;&nbsp;&nbsp;**EmployeeLoader ==> PayrollProcessor ==> Logger**
 *
 * See:
 *
 *  * [State of the Art](https://state-of-the-art.org/broadcaster)
 *
 * @author Jonathan Locke
 * @see Broadcaster
 */
@UmlIncludeType(inDiagrams = [DiagramBroadcaster::class, DiagramRepeater::class, DiagramListener::class])
@TypeQuality
(
    stability = STABLE_EXTENSIBLE,
    testing = UNTESTED,
    documentation = DOCUMENTED
)
fun interface Listener : MessageTransceiver
{
    /**
     * Returns true if this listener doesn't do anything with the messages it gets
     */
    fun isDeaf(): Boolean
    {
        return false
    }

    /**
     * Registers this listener with the given broadcaster in being interested in transmitted messages
     *
     * @param broadcaster The broadcaster that should send to this listener
     * @param filter The message filter to apply
     * @return The broadcaster
     */
    fun <T : Broadcaster?> listenTo(broadcaster: T, filter: MessageFilter): T
    {
        broadcaster!!.addListener(this, filter)
        return broadcaster
    }

    /**
     * Registers this listener with the given broadcaster in being interested in transmitted messages
     *
     * @param broadcaster The broadcaster that should send to this listener
     * @return The broadcaster
     */
    fun <T : Broadcaster?> listenTo(broadcaster: T): T
    {
        broadcaster?.addListener(this) ?: warning("Null broadcaster")
        return broadcaster
    }

    /**
     * Functional interface method called when a message is received by this listener
     *
     * @param message The message
     */
    fun onMessage(message: Message?)

    /**
     * **Not public API**
     */
    override fun onReceive(message: Transmittable)
    {
        if (message is Message)
        {
            onMessage(message)
        }
    }

    companion object
    {
        /**
         * Returns a listener that writes the messages it hears to the console
         */
        fun consoleListener(): Listener
        {
            return console()
        }

        /**
         * Returns a listener that does nothing with messages. Useful only when you want to discard output from something
         */
        fun nullListener(): Listener
        {
            return object Listener()
            {

            }
        }

        /**
         * Returns a listener that throws exceptions
         */
        fun throwingListener(): Listener
        {
            return ThrowingListener()
        }
    }
}