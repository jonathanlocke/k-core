package io.kstar.core.messaging.filters

import com.telenav.kivakit.annotations.code.quality.TypeQuality

/**
 * A message filter that matches failure messages
 *
 * @author Jonathan Locke
 */
@TypeQuality(stability = STABLE, testing = UNTESTED, documentation = DOCUMENTED)
class FailureMessages : MessageFilter
{
    /**
     * {@inheritDoc}
     */
    fun accepts(value: Message): Boolean
    {
        return value.isFailure()
    }
}