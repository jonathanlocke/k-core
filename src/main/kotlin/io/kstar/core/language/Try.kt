@file:Suppress("unused")

package io.kstar.core.language

import io.kstar.receptors.code.Code

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
     * Runs the given [Code], always running the given [Runnable] afterwards
     */
    fun <T> tryFinally(code: Code<T>, after: Runnable): T
    {
        return try
        {
            code.run()
        }
        finally
        {
            after.run()
        }
    }

    /**
     * Attempts to run the given code. If the code throws an exception it is wrapped in a
     * [RuntimeException], which is rethrown with the given message
     *
     * @return The return value of the code, or null
     */
    fun <T> tryCatchRethrow(code: Code<T>, message: String): T
    {
        return try
        {
            code.run()
        }
        catch (e: Exception)
        {
            throw RuntimeException(message, e)
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
