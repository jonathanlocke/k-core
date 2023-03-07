package io.kstar.core.messaging.filters

import com.telenav.kivakit.annotations.code.quality.TypeQuality

/**
 * A message filter that matches messages with the given severity, or worse.
 *
 * @author Jonathan Locke
 */
@TypeQuality(stability = STABLE, testing = UNTESTED, documentation = DOCUMENTED)
class MessagesWithSeverityOf(minimumSeverity: Severity) : MessageFilter
{
    /** The minimum severity  */
    private val minimumSeverity: Severity

    init
    {
        this.minimumSeverity = minimumSeverity
    }

    /**
     * {@inheritDoc}
     */
    fun accepts(value: Message): Boolean
    {
        return value.severity().isGreaterThanOrEqualTo(minimumSeverity)
    }

    override fun toString(): String
    {
        return minimumSeverity.name()
    }
}