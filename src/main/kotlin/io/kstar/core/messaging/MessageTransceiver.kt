package io.kstar.core.messaging

import io.kstar.annotations.quality.Documentation.*
import io.kstar.annotations.quality.Stability.STABLE_EXTENSIBLE
import io.kstar.annotations.quality.Testing.*
import io.kstar.annotations.quality.TypeQuality
import io.kstar.core.messaging.Debug.Companion.registerDebug
import io.kstar.core.messaging.context.CodeContext
import io.kstar.core.messaging.messages.status.Announcement
import io.kstar.core.messaging.messages.status.FatalProblem

/**
 * Methods that transmit different kinds of messages.
 *
 * @author Jonathan Locke
 */
@Suppress("unused")
@TypeQuality
(
    stability = STABLE_EXTENSIBLE,
    testing = TESTING_NOT_NEEDED,
    documentation = DOCUMENTED
)
interface MessageTransceiver : Transceiver
{
    /**
     * Sends a formatted [Announcement] message to this [Transceiver]
     *
     * @param text The message to format
     * @param arguments The arguments
     * @return The message
     */
    fun announce(text: String?, vararg arguments: Any?): Announcement?
    {
        return transmit(Announcement(text, arguments))
    }

    /**
     * Returns debug object for this
     */
    fun debug(): Debug
    {
        return registerDebug(debugClassContext(), this)
    }

    /**
     * Returns the class where this transceiver is
     */
    @UmlExcludeMember
    fun debugClassContext(): Class<*>?
    {
        return javaClass
    }

    /**
     * **Not public API**
     *
     * @return The context of this broadcaster in code
     */
    @UmlExcludeMember
    fun debugCodeContext(): CodeContext?
    {
        return CodeContext(debugClassContext())
    }

    /**
     * **Not public API**
     *
     * @param context The context in code
     */
    @UmlExcludeMember
    fun debugCodeContext(context: CodeContext?)
    {
    }

    /**
     * Throws a formatted [FatalProblem] message as an [IllegalStateException]
     *
     * @param text The message to format
     * @param arguments The arguments
     * @return The message
     */
    fun <T> fatal(text: String?, vararg arguments: Any?): T?
    {
        val problem = FatalProblem(text, arguments)
        transmit(problem)
        problem.throwMessage()
        return null
    }

    /**
     * Throws a formatted [FatalProblem] message as an [IllegalStateException]
     *
     * @param cause The cause of the fatal problem
     * @param text The message to format
     * @param arguments The arguments
     * @param <T> The type of message
     * @return The message
    </T> */
    fun <T> fatal(cause: Throwable?, text: String?, vararg arguments: Any?): T?
    {
        val problem = FatalProblem(cause, text, arguments)
        transmit(problem)
        problem.throwMessage()
        return null
    }

    /**
     * Sends a formatted [Glitch] message to this [Transceiver]
     *
     * @param text The message to format
     * @param arguments The arguments
     * @return The message
     */
    fun glitch(text: String?, vararg arguments: Any?): Glitch?
    {
        return transmit(Glitch(text, arguments))
    }

    /**
     * Sends a formatted [Glitch] message to this [Transceiver]
     *
     * @param cause The cause of the fatal problem
     * @param text The message to format
     * @param arguments The arguments
     * @return The message
     */
    fun glitch(cause: Throwable?, text: String?, vararg arguments: Any?): Glitch?
    {
        return if (isDebugOn)
        {
            transmit(Glitch(cause, text, arguments))
        }
        else null
    }

    /**
     * Sends a formatted [OperationHalted] message to this [Transceiver]
     *
     * @param text The message to format
     * @param arguments The arguments
     * @return The message
     */
    fun halted(text: String?, vararg arguments: Any?): OperationHalted?
    {
        return transmit(OperationHalted(text, arguments))
    }

    /**
     * Sends a formatted [OperationHalted] message to this [Transceiver]
     *
     * @param cause The cause of the fatal problem
     * @param text The message to format
     * @param arguments The arguments
     * @return The message
     */
    fun halted(cause: Throwable?, text: String?, vararg arguments: Any?): OperationHalted?
    {
        return transmit(OperationHalted(cause, text, arguments))
    }

    /**
     * Runs the given code if debug is turned on for this [Transceiver]
     *
     * @param code The code to run if debug is off
     */
    fun ifDebug(code: Runnable)
    {
        if (isDebugOn)
        {
            code.run()
        }
    }

    /**
     * Sends a formatted [Information] message to this [Transceiver]
     *
     * @param text The message to format
     * @param arguments The arguments
     * @return The message
     */
    fun information(text: String?, vararg arguments: Any?): Information?
    {
        return transmit(Information(text, arguments))
    }

    val isDebugOn: Boolean
        /**
         * Returns true if debugging is on for this [Transceiver]
         */
        get() = debug().isDebugOn

    /**
     * Sends a formatted [Narration] message to this [Transceiver]
     *
     * @param text The message to format
     * @param arguments The arguments
     * @return The message
     */
    fun narrate(text: String?, vararg arguments: Any?): Narration?
    {
        return transmit(Narration(text, arguments))
    }

    /**
     * Sends a formatted [Problem] message to this [Transceiver]
     *
     * @return The message
     */
    fun problem(text: String?, vararg arguments: Any?): Problem?
    {
        return transmit(Problem(text, arguments))
    }

    /**
     * Sends a formatted [Problem] message to this [Transceiver]
     *
     * @param cause The cause of the fatal problem
     * @param text The message to format
     * @param arguments The arguments
     * @return The message
     */
    fun problem(cause: Throwable?, text: String?, vararg arguments: Any?): Problem?
    {
        return transmit(Problem(cause, text, arguments))
    }

    /**
     * Broadcasts a problem if the given value is null
     *
     * @param value The value to check
     * @param text The message to format
     * @param arguments The arguments
     * @return The value
     */
    fun <T> problemIfNull(value: T?, text: String?, vararg arguments: Any?): T?
    {
        if (value == null)
        {
            problem(text, *arguments)
        }
        return value
    }

    /**
     * Sends a formatted [Quibble] message to this [Transceiver]
     *
     * @param text The message to format
     * @param arguments The arguments
     * @return The message
     */
    fun quibble(text: String?, vararg arguments: Any?): Quibble?
    {
        return transmit(Quibble(text, arguments))
    }

    /**
     * Sends a formatted [Quibble] message to this [Transceiver]
     *
     * @param cause The cause of the fatal problem
     * @param text The message to format
     * @param arguments The arguments
     * @return The message
     */
    fun quibble(cause: Throwable?, text: String?, vararg arguments: Any?): Quibble?
    {
        return if (isDebugOn)
        {
            transmit(Quibble(cause, text, arguments))
        }
        else null
    }

    /**
     * Broadcasts a quibble if the given value is null
     *
     * @param value The value to check
     * @param text The message to format
     * @param arguments The arguments
     * @return The value
     */
    fun <T> quibbleIfNull(value: T?, text: String?, vararg arguments: Any?): T?
    {
        if (value == null)
        {
            quibble(text, *arguments)
        }
        return value
    }

    /**
     * Sends a formatted [Trace] message to this [Transceiver]
     *
     * @param text The message to format
     * @param arguments The arguments
     * @return The message
     */
    fun trace(text: String?, vararg arguments: Any?): Trace?
    {
        return if (isDebugOn)
        {
            transmit(Trace(text, arguments))
        }
        else null
    }

    /**
     * Sends a formatted [Trace] message to this [Transceiver]
     *
     * @param cause The cause of the fatal problem
     * @param text The message to format
     * @param arguments The arguments
     * @return The message
     */
    fun trace(cause: Throwable?, text: String?, vararg arguments: Any?): Trace?
    {
        return if (isDebugOn)
        {
            transmit(Trace(cause, text, arguments))
        }
        else null
    }

    /**
     * Sends a formatted [Warning] message to this [Transceiver]
     *
     * @param text The message to format
     * @param arguments The arguments
     * @return The message
     */
    fun warning(text: String?, vararg arguments: Any?): Warning?
    {
        return transmit(Warning(text, arguments))
    }

    /**
     * Sends a formatted [Warning] message to this [Transceiver]
     *
     * @param cause The cause of the fatal problem
     * @param text The message to format
     * @param arguments The arguments
     * @return The message
     */
    fun warning(cause: Throwable?, text: String?, vararg arguments: Any?): Warning?
    {
        return transmit(Warning(cause, text, arguments))
    }

    /**
     * Broadcasts a warning if the given value is null
     *
     * @param value The value to check
     * @param text The message to format
     * @param arguments The arguments
     * @return The value
     */
    fun <T> warningIfNull(value: T?, text: String?, vararg arguments: Any?): T?
    {
        if (value == null)
        {
            warning(text, *arguments)
        }
        return value
    }
}