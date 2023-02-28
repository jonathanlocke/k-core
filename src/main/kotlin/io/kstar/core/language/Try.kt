@file:Suppress("unused")

package io.kstar.core.language

import io.kstar.receptors.code.Code
import java.lang.Runnable

object Try
{
    /**
     * Attempts to run the given code, returning null if an exception is thrown
     *
     * @return The return value of the code, or null
     */
    fun <T> tryCatch(code: Code<T>): T?
    {
        return try
        {
            code.run()
        }
        catch (e: Exception)
        {
            null
        }
    }

    /**
     * Attempts to run the given code, returning null if an exception is thrown
     *
     * @return The return value of the code, or null
     */
    fun <T> tryCatchOr(code: Code<T>, default: T): T
    {
        return try
        {
            code.run()
        }
        catch (e: Exception)
        {
            default
        }
    }

    /**
     * Attempts to run the given code, returning true if it succeeded, and false if an
     * exception was thrown
     *
     * @return True if the code ran successfully
     */
    fun tryRunning(code: Runnable): Boolean
    {
        return try
        {
            code.run()
            true
        }
        catch (e: Exception)
        {
            false
        }
    }

    /**
     * Attempts to run the given code, returning true if it succeeded, and false if an
     * exception was thrown
     *
     * @return True if the code ran successfully
     */
    fun <T> tryRunning(code: Code<T>): Boolean
    {
        return try
        {
            code.run()
            true
        }
        catch (e: Exception)
        {
            false
        }
    }
}
