package io.kstar.core.messaging

import com.telenav.kivakit.annotations.code.quality.TypeQuality

/**
 * Specifies what kind of formatting to apply to a message
 *
 * @author Jonathan Locke
 */
@TypeQuality(stability = STABLE_EXTENSIBLE, testing = TESTING_NOT_NEEDED, documentation = DOCUMENTED)
enum class MessageFormat
{
    /** The message should be formatted  */
    FORMATTED,

    /** The message should not be formatted  */
    UNFORMATTED
}