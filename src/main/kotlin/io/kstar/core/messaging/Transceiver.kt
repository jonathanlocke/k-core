package io.kstar.core.messaging

import io.kstar.annotations.documentation.UmlIncludeType
import io.kstar.annotations.documentation.UmlNote
import io.kstar.annotations.documentation.UmlRelation
import io.kstar.core.internal.Diagrams.DiagramBroadcaster
import io.kstar.core.internal.Diagrams.DiagramListener
import io.kstar.core.internal.Diagrams.DiagramLogging
import io.kstar.receptors.values.Named

/**
 * Functionality that is common to [Broadcaster]s, [Listener]s, [Repeater]s and potentially other
 * classes that are involved in handling messages. The [.isTransmitting] method returns true if transmitting is
 * enabled. The [.isReceiving] method returns true if receiving is enabled. When enabled, the
 * [.receive] method calls [.onReceive] to allow the subclass to handle the
 * message.
 *
 *
 * **Convenience Methods**
 *
 *
 * The following convenience methods call [.receive] with the appropriate class of message.
 *
 *
 *  * announce*() - The sends a formatted [Announcement] message to this [Transceiver]
 *  * trace*() - The sends a formatted [Trace] message to this [Transceiver]
 *  * information*() - The sends a formatted [Information] message to this [Transceiver]
 *  * narrate() - The sends a formatted [Narration] message to this [Transceiver]
 *  * warning*() - The sends a formatted [Warning] message to this [Transceiver]
 *  * glitch*() - The sends a formatted [Glitch] message to this [Transceiver]/li>
 *  * quibble*() - The sends a formatted [Quibble] message to this [Transceiver]/li>
 *  * problem*() - The listener handles a [Problem] message
 *  * fatal*() - The sends a formatted [FatalProblem] message to this [Transceiver]
 *  * halted*() - The sends a formatted [OperationHalted] message to this [Transceiver]
 *
 *
 *
 *
 * Because [Broadcaster]s, [Listener]s and [Repeater]s are debug transceivers, they inherit all the
 * methods in this class. This means that a subclass of [BaseRepeater] can simply call a trace() or glitch()
 * method and it will automatically be gated by the functionality of [Debug]. This makes it especially easy to
 * declare and control debug tracing. In the example below, the trace() statement can be enabled by running the
 * application with -DKIVAKIT_DEBUG=EmployeeLoader. See @[Debug] for more details on the KIVAKIT_DEBUG syntax.
 *
 *
 *
 * **Debug Tracing Example**
 *
 * <pre>
 * class EmployeeLoader extends BaseRepeater
 * {
 *
 * [...]
 *
 * trace("Loaded $ employees", employees.size());
 *
 * }
</pre> *  * @author Jonathan Locke
 *
 * @see Listener
 *
 * @see Broadcaster
 *
 * @see Message
 *
 * @see Trace
 *
 * @see Information
 *
 * @see Warning
 *
 * @see Glitch
 *
 * @see Problem
 *
 * @see OperationHalted
 */
@Suppress("unused", "SpellCheckingInspection")
@UmlIncludeType(inDiagrams = [DiagramLogging::class, DiagramBroadcaster::class, DiagramListener::class])
@UmlRelation(label = "delegates to", referent = Debug::class)
@UmlNote("Functionality common to transmitters and receivers")
interface Transceiver : Named, Receiver, Transmitter
{
    override fun onTransmit(message: Transmittable)
    {
        receive(message)
    }
}