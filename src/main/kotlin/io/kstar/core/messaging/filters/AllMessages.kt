package io.kstar.core.messaging.filters

import com.telenav.kivakit.annotations.code.quality.TypeQuality

/**
 * A filter that accepts all messages
 *
 * @author Jonathan Locke
 */
@TypeQuality(stability = STABLE, testing = TESTING_NOT_NEEDED, documentation = DOCUMENTED)
class AllMessages : MessageFilter
{
    fun accepts(message: Transmittable?): Boolean
    {
        return true
    }
}