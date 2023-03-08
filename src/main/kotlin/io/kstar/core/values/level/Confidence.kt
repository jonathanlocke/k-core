@file:Suppress("unused")

package io.kstar.core.values.level

import io.kstar.annotations.documentation.UmlIncludeType
import io.kstar.core.language.Try.tryOr
import io.kstar.internal.Diagrams.DiagramCount
import io.kstar.receptors.code.ProblemHandler

/**
 * Level of certainty in the accuracy of something. Confidence objects can be constructed with
 * [Confidence.confidence].
 *
 * @author Jonathan Locke
 */
@UmlIncludeType(inDiagrams = [DiagramCount::class])
class Confidence(val level: Double) : Level(level)
{
    fun asUnsignedByte(): Int
    {
        return (asZeroToOne() * 255).toInt()
    }

    override fun toString(): String
    {
        return String.format("%.1f", asZeroToOne())
    }

    override fun onNew(scalar: Double): Confidence = Confidence(scalar)

    companion object
    {
        val HIGH = Confidence(0.9)
        val MEDIUM = Confidence(0.5)
        val LOW = Confidence(0.25)
        val NO = Confidence(0.0)

        fun confidence(value: Double): Confidence = Confidence(value)
        fun confidenceForByte(level: Byte): Confidence = Confidence(level.toDouble() / Byte.MAX_VALUE)
        fun confidenceForInt(value: Int): Confidence = Confidence(value / 255.0)

        /**
         * Returns a [Confidence] for the given text, which should be a scalar from 0.0 to 1.0, inclusive.
         *
         * If the text cannot be parsed, the given problem handler is called, allowing the caller to
         * choose how the problem should be handled. See [ProblemHandler] for details.
         *
         * @param text The text to parse
         * @param handler The reporter to call if something goes wrong
         * @return The parsed value
         * @throws IllegalStateException Thrown if the string is not a binary number
         * @see ProblemHandler
         */
        fun parseConfidence(text: String, handler: ProblemHandler<Confidence> = ProblemHandler.throwProblem()): Confidence?
        {
            return tryOr({ Confidence(text.toDouble()) })
            {
                handler.reportProblem("Invalid confidence $text")
            }
        }
    }
}