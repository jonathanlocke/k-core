package io.kstar.core.language

import io.kstar.annotations.quality.Documentation.DOCUMENTED
import io.kstar.annotations.quality.Stability.STABLE_EXTENSIBLE
import io.kstar.annotations.quality.Testing.TESTED
import io.kstar.annotations.quality.TypeQuality
import io.kstar.core.language.Booleans.isFalse
import io.kstar.core.language.Booleans.isTrue
import io.kstar.core.language.Objects.equalsAny

/**
 * Extension methods for [Boolean] values.
 *
 *  - [isFalse]
 *  - [isTrue]
 *
 * @author  Jonathan Locke
 */
@TypeQuality
(
    stability = STABLE_EXTENSIBLE,
    testing = TESTED,
    documentation = DOCUMENTED
)
object Booleans
{
    /**
     * Returns true if the string represents falsity (case-independent), meaning any of:
     *
     *  - disabled
     *  - false
     *  - no
     *  - off
     *
     */
    fun String.isFalse(): Boolean
    {
        return lowercase().equalsAny("disabled", "false", "no", "off")
    }

    /**
     * Returns true if the string represents truth (case-independent), meaning any of:
     *
     *
     *  - enabled
     *  - true
     *  - yes
     *  - on
     *
     */
    fun String.isTrue(): Boolean
    {
        return lowercase().equalsAny("enabled", "true", "yes", "on")
    }
}
