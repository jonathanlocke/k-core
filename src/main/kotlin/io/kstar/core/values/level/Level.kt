@file:Suppress("MemberVisibilityCanBePrivate")

package io.kstar.core.values.level

import io.kstar.annotations.documentation.UmlIncludeType
import io.kstar.annotations.quality.Documentation.DOCUMENTED
import io.kstar.annotations.quality.Stability.STABLE
import io.kstar.annotations.quality.Testing.TESTING_NOT_NEEDED
import io.kstar.annotations.quality.TypeQuality
import io.kstar.core.language.Doubles.inRange
import io.kstar.core.values.level.Level.Companion.level
import io.kstar.core.values.level.Level.Companion.parseLevel
import io.kstar.internal.Diagrams.DiagramValue
import io.kstar.receptors.code.NullableCode
import io.kstar.receptors.code.ProblemHandler
import io.kstar.receptors.code.ProblemHandler.Companion.throwProblem
import io.kstar.receptors.numeric.Numeric
import io.kstar.receptors.numeric.Numeric.Companion.div
import io.kstar.receptors.numeric.Numeric.Companion.minus
import io.kstar.receptors.numeric.Numeric.Companion.plus
import io.kstar.receptors.numeric.Numeric.Companion.times

/**
 * A level from 0 to 1.
 *
 * **Creation**
 *
 *  * [parseLevel]
 *  * [level]
 *
 * **Properties**
 *
 *  * [value]
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
 *  * [asZeroToOne]
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
@Suppress("unused")
@UmlIncludeType(inDiagrams = [DiagramValue::class])
@TypeQuality
(
    stability = STABLE,
    testing = TESTING_NOT_NEEDED,
    documentation = DOCUMENTED
)
open class Level
(
    /** The level, between 0 and 1, inclusive */
    private val value: Double

) : Numeric<Level>
{
    override fun isValidScalar(scalar: Double): Boolean = scalar in 0.0..1.0
    override fun onNew(scalar: Double): Level = Level(scalar)

    /**
     * Returns this level as a percentage between 0 and 100
     */
    fun asPercent(): Percent = Percent(value * 100.0)

    /**
     * Returns this level as a value from 0.0 to 1.0, inclusive
     */
    fun asZeroToOne(): Double = value

    /**
     * Returns the distance of this weight from 1.0
     */
    open fun inverse(): Level = new(1.0 - asZeroToOne())

    fun value() = value

    override fun equals(other: Any?): Boolean = if (other is Level) value == other.value else false
    override fun hashCode(): Int = java.lang.Double.hashCode(value)
    override fun toString(): String = value.toString()
    override fun asDouble(): Double = value

    override fun minimum(): Level = minimumLevel()
    override fun maximum(): Level = maximumLevel()

    companion object
    {
        /** The minimum level */
        fun minimumLevel(): Level = level(0.0)

        /** The minimum level */
        fun maximumLevel(): Level = level(1.0)

        /** Creates a [Level] */
        fun level(level: Double) = Level(level)

        /**
         * Returns a [Level] for the given text, which should be a scalar from 0.0 to 1.0, inclusive.
         * If lenient is true, values less than 0 are resolved to zero, and levels greater than one are
         * resolved to one. If the text cannot be parsed, the given problem handler is called, allowing
         * the caller to choose how the problem is handled. See [ProblemHandler] for details.
         *
         * @param text The text to parse
         * @param lenient Brings values outside the zero to one range into range
         * @param handler The reporter to call if something goes wrong
         * @return The parsed value
         * @throws IllegalStateException Thrown if the string is not a binary number
         * @see ProblemHandler
         */
        fun parseLevel(text: String, lenient: Boolean = false, handler: ProblemHandler<Level> = throwProblem()): Level?
        {
            return NullableCode<Level> {
                val level = text.toDouble()
                if (level in 0.0..1.0)
                {
                    Level(level)
                }
                if (lenient)
                {
                    Level(level.inRange(0.0..1.0))
                }
                null
            }.runOr {
                handler.reportProblem("Invalid level $text")
            }
        }
    }
}
