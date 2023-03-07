package io.kstar.core.messaging.repeaters

import com.telenav.kivakit.annotations.code.quality.TypeQuality

/**
 * A stateful [Mixin] that can be used when a class can't extend [BaseRepeater] to implement the
 * [Repeater] interface.
 *
 * @author Jonathan Locke
 * @see Mixin
 */
@TypeQuality(stability = STABLE_EXTENSIBLE, testing = UNTESTED, documentation = DOCUMENTED)
interface RepeaterMixin : Repeater, Mixin
{
    /**
     * {@inheritDoc}
     */
    fun addListener(listener: Listener?, filter: Filter<Transmittable?>?)
    {
        repeater().addListener(listener, filter)
    }

    /**
     * {@inheritDoc}
     */
    fun clearListeners()
    {
        repeater().clearListeners()
    }

    /**
     * {@inheritDoc}
     */
    fun copyListenersFrom(broadcaster: Broadcaster?)
    {
        repeater().copyListenersFrom(broadcaster)
    }

    /**
     * {@inheritDoc}
     */
    fun debugCodeContext(): CodeContext?
    {
        return repeater().debugCodeContext()
    }

    /**
     * {@inheritDoc}
     */
    fun debugCodeContext(context: CodeContext?)
    {
        repeater().debugCodeContext(context)
    }

    /**
     * {@inheritDoc}
     */
    fun hasListeners(): Boolean
    {
        return repeater().hasListeners()
    }

    val isReceiving: Boolean
        /**
         * {@inheritDoc}
         */
        get() = repeater().isReceiving()
    val isTransmitting: Boolean
        /**
         * {@inheritDoc}
         */
        get() = repeater().isTransmitting()

    /**
     * {@inheritDoc}
     */
    fun listeners(): List<Listener?>?
    {
        return repeater().listeners()
    }

    /**
     * {@inheritDoc}
     */
    fun messageSource(source: Broadcaster?)
    {
        repeater().messageSource(source)
    }

    /**
     * {@inheritDoc}
     */
    fun messageSource(): Broadcaster?
    {
        return repeater().messageSource()
    }

    /**
     * {@inheritDoc}
     */
    fun ok(): Boolean
    {
        return repeater().ok()
    }

    /**
     * {@inheritDoc}
     */
    fun onMessage(message: Message?)
    {
        repeater().onMessage(message)
    }

    /**
     * {@inheritDoc}
     */
    fun <M : Transmittable?> receive(message: M): M
    {
        return repeater().receive(message)
    }

    /**
     * {@inheritDoc}
     */
    fun removeListener(listener: Listener?)
    {
        repeater().removeListener(listener)
    }

    /**
     * **Not public API**
     *
     *
     * Returns the [Repeater] implementation associated with this mixin
     */
    fun repeater(): Repeater
    {
        return mixin(RepeaterMixin::class.java) { BaseRepeater() }
    }

    /**
     * {@inheritDoc}
     */
    fun <T : Message?> transmit(message: T): T
    {
        return repeater().transmit(message)
    }
}