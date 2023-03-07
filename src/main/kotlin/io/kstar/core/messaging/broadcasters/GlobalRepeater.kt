package io.kstar.core.messaging.broadcasters

import com.telenav.kivakit.core.messaging.Broadcaster

/**
 * Broadcasts messages to the global listener
 *
 * @author Jonathan Locke
 */
@Suppress("unused")
interface GlobalRepeater : Repeater
{
    fun addListener(listener: Listener?, filter: Filter<Transmittable?>?)
    {
    }

    fun clearListeners()
    {
    }

    fun hasListeners(): Boolean
    {
        return true
    }

    val isRepeating: Boolean
        get() = true

    fun listeners(): List<Listener?>?
    {
        return list(globalListener())
    }

    fun messageSource(parent: Broadcaster?)
    {
    }

    fun messageSource(): Broadcaster?
    {
        return this
    }

    fun ok(): Boolean
    {
        return true
    }

    fun onMessage(message: Message)
    {
        transmit<Transmittable>(message)
    }

    fun removeListener(listener: Listener?)
    {
    }

    fun <T : Transmittable?> transmit(transmittable: T): T
    {
        if (transmittable is Message)
        {
            globalListener().receive(transmittable)
        }
        return transmittable
    }
}