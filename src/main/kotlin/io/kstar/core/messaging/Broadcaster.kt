package io.kstar.core.messaging

import com.telenav.kivakit.annotations.code.quality.TypeQuality
import java.util.function.Consumer

/**
 * Broadcasts a message to zero or more listeners via [.transmit]. Listeners can be added with
 * [.addListener] and can be cleared with [.clearListeners]. A number of convenience methods in
 * the [Transceiver] superclass  make it easier to broadcast specific messages.
 *
 *
 * **Convenience Methods**
 *
 *
 *
 * A number of convenience methods in the [Transceiver] superinterface make it easy to transmit specific common
 * messages. [Repeater]s are [Broadcaster]s (as well as [Listener]s) which inherit the methods in this
 * class as well as those in [Transceiver]. For details on how to take advantage of this in object design, see
 * [Repeater].
 *
 *
 * @author Jonathan Locke
 * @see Transceiver
 *
 * @see Broadcaster
 *
 * @see Repeater
 *
 * @see Listener
 *
 * @see Message
 *
 * @see [State
](https://state-of-the-art.org.broadcaster) */
@Suppress("unused")
@UmlIncludeType(diagram = DiagramBroadcaster::class)
@UmlIncludeType(diagram = DiagramRepeater::class)
@UmlRelation(label = "transmits", referent = Listener::class, refereeCardinality = "1", referentCardinality = "*")
@TypeQuality(stability = STABLE_EXTENSIBLE, testing = UNTESTED, documentation = DOCUMENTED)
interface Broadcaster : MessageTransceiver
{
    /**
     * Adds a listener to this broadcaster that wants to receive future messages. This is the mirror method of
     * [Listener.listenTo] as both methods achieve the same thing.
     *
     *
     *
     * *Note: adding the same listener two or more times will not replace the original listener. To do that, call
     * [.clearListeners] before calling this method*
     *
     *
     * @param listener Listener to broadcast to. Implementations should ignore null listeners.
     * @param filter The filter to apply
     */
    /**
     * Adds a listener to this broadcaster that wants to receive future messages.
     *
     *
     *
     * *Note: adding the same listener two or more times will not replace the original listener. To do that, call
     * [.clearListeners] before calling this method*
     *
     */
    @JvmOverloads
    fun addListener(listener: Listener?, filter: Filter<Transmittable?>? = acceptAll())

    /**
     * Removes all listeners from this broadcaster
     */
    fun clearListeners()

    /**
     * Copies the listeners of another broadcaster
     */
    fun copyListenersFrom(that: Broadcaster)
    {
        that.listeners().forEach(Consumer { listener: Listener? -> addListener(listener) })
    }

    /**
     * Returns true if this broadcaster has any listeners
     */
    fun hasListeners(): Boolean

    /**
     * Returns the listeners to this broadcaster
     */
    fun listeners(): List<Listener?>

    /**
     * **Not public API**
     */
    fun messageSource(): Broadcaster?

    /**
     * **Not public API**
     */
    fun messageSource(parent: Broadcaster?)

    /**
     * A broadcaster handles a message by transmitting it
     */
    override fun onReceive(message: Transmittable?)
    {
        transmit(message)
    }

    /**
     * Allows subclass to transmit message to listeners
     */
    override fun onTransmit(message: Transmittable?)
    {
        receive(message)
    }

    /**
     * Allows subclass to process a message after it is transmitted
     *
     * @param message The message
     */
    fun onTransmitted(message: Transmittable?)
    {
    }

    /**
     * Allows subclass to process a message before it is transmitted
     */
    fun onTransmitting(message: Transmittable?)
    {
    }

    /**
     * Removes the given listener from this broadcaster
     */
    fun removeListener(listener: Listener?)

    /**
     * Causes this broadcaster to broadcast only to the null listener
     */
    fun silence()
    {
        clearListeners()
        addListener(nullListener())
    }

    /**
     * **Not public API**
     *
     *
     *
     * Transmits the given message by calling the subclass
     *
     */
    fun <M : Transmittable?> transmit(message: M): M
    {
        onTransmitting(message)
        onTransmit(message)
        onTransmitted(message)
        return message
    }

    /**
     * **Not public API**
     *
     *
     *
     * Broadcasts the given message to any listeners in the audience of this broadcaster
     *
     */
    fun <T : Message?> transmit(message: T?): T?
    {
        transmit(message as Transmittable?)
        return message
    }

    /**
     * **Not public API**
     *
     *
     *
     * Broadcasts the given messages to any listeners in the audience of this broadcaster
     *
     *
     * @param messages The messages to broadcast
     */
    fun transmitAll(messages: Iterable<Transmittable?>)
    {
        messages.forEach(Consumer<Transmittable> { message: Transmittable? -> this.transmit(message) })
    }
}