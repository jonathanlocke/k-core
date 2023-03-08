package io.kstar.core.messaging

import io.kstar.annotations.documentation.UmlIncludeType
import io.kstar.annotations.quality.Documentation.*
import io.kstar.annotations.quality.Stability.*
import io.kstar.annotations.quality.Testing.*
import io.kstar.annotations.quality.TypeQuality
import io.kstar.internal.Diagrams.DiagramRepeater
import javax.sound.midi.Receiver

/**
 * A repeater is both a [Listener] and a [Broadcaster], receiving messages in
 * [.receive] and rebroadcasting them to its own listeners with [.transmit].
 *
 *
 * A variety of convenient methods are accessible when an object implements [Repeater] by extending
 * [BaseRepeater]. In the example below, EmployeeLoader is a repeater which transmits a warning to all of its
 * registered listeners. The PayrollProcessor class is also a [Repeater] which listens to messages transmitted by
 * the EmployeeLoader and re-transmits them to its own listeners. Clients of the PayrollProcessor can listen to it in
 * turn and they will receive the warning transmitted EmployeeLoader, when it is repeated by the PayrollProcessor. This
 * pattern creates a chain of repeaters that terminates in one or more listeners. The final listener is often, but not
 * always a logger. The base Application class in kivakit-application, for example, is a [Repeater] which logs the
 * messages it receives by default.
 *
 *
 *
 * **Repeater Example**
 *
 * <pre>
 * class EmployeeLoader extends BaseRepeater
 * {
 *
 * [...]
 *
 * warning("Unable to load $", employee);
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
 * var processor = listenTo(new PayrollProcessor());
 *
</pre> *
 *
 * @author Jonathan Locke
 * @see Broadcaster
 *
 * @see Listener
 */
@UmlIncludeType(inDiagrams = [DiagramRepeater::class])
@TypeQuality
(
    stability = STABLE_EXTENSIBLE,
    testing = TESTING_NOT_NEEDED,
    documentation = DOCUMENTED
)
interface Repeater : Listener, Broadcaster, Receiver
{
    val isRepeating: Boolean
        /**
         * True if this repeater is repeating messages it receives
         */
        get() = true

    /**
     * Returns true if this repeater has not received any failure message, as determined by calling
     * [Message.isFailure].
     *
     * @return True if no failure has occurred.
     */
    fun ok(): Boolean

    /**
     * Handles any received messages by re-broadcasting them
     */
    override fun onReceive(message: Transmittable)
    {
        if (isRepeating)
        {
            transmit(message)
        }
    }

    /**
     * **Not public API**
     *
     *
     *
     * Repeaters re-broadcast messages they receive
     *
     */
    fun <M : Transmittable?> receive(message: M): M
    {
        onReceive(message)
        return message
    }
}