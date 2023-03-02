@file:Suppress("unused", "UNCHECKED_CAST", "UnusedReceiverParameter")

package io.kstar.core.language

import io.kstar.core.language.Throw.fail

class Numbers
{
    /**
     * Returns the maximum value for the given number type
     */
    fun <T : Number> maximum(type: Class<T>): T
    {
        return when (type.kotlin)
        {
            Double::class -> Double.MAX_VALUE as T
            Float::class -> Float.MAX_VALUE as T
            Long::class -> Long.MAX_VALUE as T
            Int::class -> Int.MAX_VALUE as T
            Short::class -> Short.MAX_VALUE as T
            Byte::class -> Byte.MAX_VALUE as T

            else -> fail("Unsupported numeric type: $type")
        }
    }

    /**
     * Returns the maximum value for the given number type
     */
    fun <T : Number> minimum(type: Class<T>): T
    {
        return when (type.kotlin)
        {
            Double::class -> Double.MIN_VALUE as T
            Float::class -> Float.MIN_VALUE as T
            Long::class -> Long.MIN_VALUE as T
            Int::class -> Int.MIN_VALUE as T
            Short::class -> Short.MIN_VALUE as T
            Byte::class -> Byte.MIN_VALUE as T

            else -> fail("Unsupported numeric type: $type")
        }
    }

    /**
     * Returns the given value cast to the given number type
     */
    fun <T : Number> Number.to(type: Class<T>): T
    {
        return when (type.kotlin)
        {
            Long::class -> this.toLong() as T
            Int::class -> this.toInt() as T
            Short::class -> this.toShort() as T
            Byte::class -> this.toByte() as T
            Double::class -> this.toDouble() as T
            Float::class -> this.toFloat() as T

            else -> throw IllegalStateException("Unsupported numeric type: $type")
        }
    }
}
