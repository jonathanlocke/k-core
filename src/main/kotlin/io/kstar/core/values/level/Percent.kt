@file:Suppress("MemberVisibilityCanBePrivate", "unused")

package io.kstar.core.values.level

import io.kstar.annotations.documentation.UmlIncludeType
import io.kstar.annotations.quality.Documentation.DOCUMENTED
import io.kstar.annotations.quality.Stability.STABLE_EXTENSIBLE
import io.kstar.annotations.quality.Testing.UNTESTED
import io.kstar.annotations.quality.TypeQuality
import io.kstar.core.language.Doubles.format
import io.kstar.core.language.Doubles.inRange
import io.kstar.core.values.level.Percent.Companion.parsePercent
import io.kstar.core.values.level.Percent.Companion.percent
import io.kstar.core.values.strings.Strip.stripTrailing
import io.kstar.internal.Diagrams
import io.kstar.receptors.code.NullableCode
import io.kstar.receptors.code.ProblemHandler
import io.kstar.receptors.code.ProblemHandler.Companion.throwProblem
import io.kstar.receptors.numeric.Numeric
import io.kstar.receptors.numeric.Numeric.Companion.div
import io.kstar.receptors.numeric.Numeric.Companion.minus
import io.kstar.receptors.numeric.Numeric.Companion.plus
import io.kstar.receptors.numeric.Numeric.Companion.times

/**
 * A percentage of any range (not only from 0 to 100%). A percent object can be created with [.percent],
 * passing in a percentage, like 50 or 90 to get 50% or 90%.
 *
 * **Creation**
 *
 *  * [parsePercent]
 *  * [percent]
 *
 * **Properties**
 *
 *  * [percentage]
 *
 * **Operators**
 *
 *  * [plus]
 *  * [minus]
 *  * [times]
 *  * [div]
 *
 * **Operations**
 *
 *  * [inverse]
 *
 *  * [incremented]
 *  * [decremented]
 *  * [reciprocal]
 *
 * **Tests**
 *
 *  * [isZero]
 *  * [isNonZero]
 *
 * **Comparison**
 *
 *  * [compareTo]
 *
 * **Range**
 *
 *  * [maximum]
 *  * [minimum]
 *  * [inRange]
 *
 * **Sequences**
 *
 *  * [asSequence]
 *  * [asInts]
 *  * [asLongs]
 *
 * **Conversions**
 *
 *  * [asUnitValue]
 *  * [asZeroToOne]
 *  * [asLevel]
 *
 *  * [asByte]
 *  * [asChar]
 *  * [asDouble]
 *  * [asFloat]
 *  * [asInt]
 *  * [asLong]
 *  * [asShort]
 *  * [asUInt]
 *  * [asULong]
 *
 * @author Jonathan Locke
 */
@UmlIncludeType(inDiagrams = [Diagrams.DiagramCount::class])
@TypeQuality
(
    stability = STABLE_EXTENSIBLE,
    testing = UNTESTED,
    documentation = DOCUMENTED
)
class Percent
(
    /** The percentage */
    private val percentage: Double

) : Numeric<Percent>
{
    /**
     * Returns this percent as a level between 0 and 1
     */
    fun asLevel(): Level = Level(asZeroToOne())

    /**
     * This percent as a unit value, potentially greater than 1 or less than 0
     *
     * @return This percentage divided by 100
     */
    fun asUnitValue(): Double = percentage / 100.0

    /**
     * Returns this percentage as a value from 0 to 1, both inclusive. If the percentage
     * is greater than 100%, the value 1.0 is returned. If the percentage is less than 0%,
     * the value 0.0 is returned
     */
    fun asZeroToOne(): Double
    {
        return (percentage / 100.0).inRange(0.0..1.0)
    }

    /**
     * This percent as a distance from 100, for example the inverse of 25% is 75%.
     */
    fun inverse(): Percent
    {
        return Percent(100.0 - asZeroToOne())
    }

    fun percentage(): Double = percentage

    override fun onNew(scalar: Double): Percent = Percent(scalar)
    override fun equals(other: Any?): Boolean = if (other is Level) percentage == other.asDouble() else false
    override fun hashCode(): Int = java.lang.Double.hashCode(percentage)
    override fun toString(): String = percentage.format(1) + "%"
    override fun asDouble(): Double = percentage

    override fun maximum(): Percent = percent(Double.MAX_VALUE)
    override fun minimum(): Percent = percent(-Double.MAX_VALUE)

    companion object
    {
        /**
         * Returns a [Percent] for the given text, which should be a scalar from 0.0 to 100.0, inclusive.
         * If lenient is true, values less than 0.0 and greater than 100.0 are allowed.
         *
         * If the text cannot be parsed, the given problem handler is called, allowing the caller to
         * choose how the problem is handled. See [ProblemHandler] for details.
         *
         * @param text The text to parse
         * @param lenient If true, allows values outside the range from 0 to 100
         * @param handler The reporter to call if something goes wrong
         * @return The parsed value
         * @throws IllegalStateException Thrown if the string is not a binary number
         * @see ProblemHandler
         */
        fun parsePercent(text: String, lenient: Boolean = false, handler: ProblemHandler<Percent> = throwProblem()): Percent?
        {
            return NullableCode {
                val percentage = text.stripTrailing("%").toDouble()
                if (lenient || percentage in 0.0..1.0) Percent(percentage) else null
            }.runOr {
                handler.reportProblem("Invalid percentage $text")
            }
        }

        /**
         * Creates a [Percent] object
         *
         * @param percentage The percent on a scale from 0 to 100 (but it can be greater or less, like 200%)
         * @return The percent
         */
        fun percent(percentage: Double): Percent
        {
            return Percent(percentage)
        }
    }
}