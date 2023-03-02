@file:Suppress("unused")

package io.kstar.core.language

object Arrays
{
    /**
     * Converts the given byte array to a hex string
     */
    fun ByteArray.asHexadecimalString(): String
    {
        val builder = StringBuilder()
        for (at in this)
        {
            builder.append(String.format("%02d", at.toInt() and 0xff))
        }
        return builder.toString()
    }

    /**
     * Returns the concatenation of the two given arrays, as an array.
     */
    @Suppress("UNCHECKED_CAST")
    fun <Value> Array<Value>.appending(that: Array<Value>): Array<Value>
    {
        val concatenated = java.lang.reflect.Array.newInstance(javaClass.componentType, size + that.size) as Array<Value>
        System.arraycopy(this, 0, concatenated, 0, size)
        System.arraycopy(that, 0, concatenated, size, that.size)
        return concatenated
    }
}
