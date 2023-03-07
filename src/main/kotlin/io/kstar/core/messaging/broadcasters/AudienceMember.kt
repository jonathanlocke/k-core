package io.kstar.core.messaging.broadcasters

import com.telenav.kivakit.annotations.code.quality.TypeQuality

/**
 * A filtered [Multicaster] listener
 *
 * @author Jonathan Locke
 */
@UmlIncludeType(diagram = DiagramRepeater::class)
@TypeQuality(stability = STABLE, testing = UNTESTED, documentation = DOCUMENTED, audience = AUDIENCE_INTERNAL)
internal class AudienceMember(listener: Listener, filter: Filter<Transmittable?>)
{
    /** The listener  */
    @UmlAggregation
    private val listener: Listener

    /** Any filter  */
    @UmlAggregation
    private val filter: Filter<Transmittable>

    /**
     * Creates an audience member for a [Broadcaster] that listens to messages from the given listener. The
     * messages are filtered by the given filter.
     *
     * @param listener The listener
     * @param filter The filter
     */
    init
    {
        this.listener = listener
        this.filter = filter
    }

    /**
     * {@inheritDoc}
     */
    override fun equals(`object`: Any?): Boolean
    {
        return if (`object` is AudienceMember)
        {
            listener === `object`.listener
        }
        else false
    }

    /**
     * {@inheritDoc}
     */
    override fun hashCode(): Int
    {
        return hash(listener)
    }

    /**
     * The listener
     */
    fun listener(): Listener
    {
        return listener
    }

    /**
     * If the given filter accepts the message, transmits it to the listener.
     */
    fun receive(message: Transmittable?)
    {
        if (filter.accepts(message))
        {
            listener.receive(message)
        }
    }

    override fun toString(): String
    {
        return "[Listener " + NamedObject.syntheticName(listener) + "]"
    }
}