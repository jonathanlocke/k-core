package io.kstar.core.messaging.context

import com.telenav.kivakit.annotations.code.quality.TypeQuality

/**
 * Holds stack trace information
 *
 * @author Jonathan Locke
 */
@Suppress("unused")
@TypeQuality(stability = STABLE_EXTENSIBLE, testing = UNTESTED, documentation = DOCUMENTED)
class StackTrace(
    /** The message associated with this trace  */
    private val message: String?, elements: Array<StackTraceElement>) : Sized, StringFormattable
{
    /**
     * This has to exist because Josh forgot to put a no-arg constructor on StackTraceElement in the JDK. Without that,
     * it isn't serializable with Kryo.
     *
     * @author Jonathan Locke
     */
    @Suppress("unused")
    class StackFrame
    {
        /** The Java file  */
        private var file: String? = null

        /** The line number  */
        private var line = 0

        /** The method  */
        private var method: String? = null

        /** The simple type name  */
        private var type: String? = null

        constructor(element: StackTraceElement)
        {
            type = element.className
            method = element.methodName
            line = element.lineNumber
            file = element.fileName
        }

        protected constructor()

        /**
         * Returns the full description of this frame
         */
        fun full(): String
        {
            return ("    at " + type + "." + method
                    + "(" + file + ":" + line + ")")
        }

        /**
         * Returns a simplified description of this frame
         */
        fun simplified(): String?
        {
            // Make type human-readable
            val type = type()

            // If the type is not stack trace itself
            if (!type.equals(StackTrace::class.java.name))
            {
                // return the frame as a simplified string
                val context = "($file:$line)"
                return "  " + rightAlign(context, 40, ' ') + " " + method
            }

            // We don't want to include this frame
            return null
        }

        private fun type(): String
        {
            return type!!.replace('$', '.')
        }
    }

    /** The stack trace that caused this one  */
    private var cause: StackTrace? = null

    /** The type of exception thrown  */
    private var exceptionType: String? = null

    /** The stack frames in this trace  */
    private val frames: Array<StackFrame?>

    /** The fully qualified exception type name  */
    private var fullExceptionType: String? = null

    init
    {
        frames = arrayOfNulls(elements.size)
        var index = 0
        for (element in elements)
        {
            frames[index++] = StackFrame(element)
        }
    }

    @JvmOverloads
    constructor(throwable: Throwable? = Throwable()) : this(throwable!!.message, throwable.stackTrace)
    {
        if (throwable.cause != null)
        {
            cause = StackTrace(throwable.cause)
        }
        fullExceptionType = throwable.javaClass.name
        exceptionType = simpleName(throwable.javaClass)
    }

    /**
     * {@inheritDoc}
     */
    fun asString(format: Format): String
    {
        return when (format)
        {
            HTML -> toHtmlString()
            else -> toString()
        }
    }

    /**
     * Returns the stack trace that caused this one
     */
    fun cause(): StackTrace?
    {
        return cause
    }

    /**
     * {@inheritDoc}
     */
    fun size(): Int
    {
        return frames.size
    }

    /**
     * Returns this trace as an HTML string
     */
    fun toHtmlString(): String
    {
        val builder = StringBuilder()
        builder.append("<p><b><font color='#808080'>").append(message).append("</font></b></p>")
        for (frame in frames)
        {
            builder.append("&nbsp;&nbsp;at ").append(frame).append("<br/>")
        }
        if (cause != null)
        {
            builder.append(cause!!.toHtmlString())
        }
        return builder.toString().replaceAll("\\$", ".")
    }

    /**
     * {@inheritDoc}
     */
    override fun toString(): String
    {
        val simple = "true".equals(System.getProperty("KIVAKIT_SIMPLIFIED_STACK_TRACES"), ignoreCase = true)
        return trace(!simple)
    }

    /**
     * Returns the text for the top of stack
     */
    fun top(): String
    {
        return frames[0]!!.full()
    }

    private fun message(): String
    {
        return if (message == null) "" else ": $message"
    }

    private fun trace(full: Boolean): String
    {
        val builder = IndentingStringBuilder(indentation(if (full) 4 else 2))
        if (full)
        {
            builder.appendLine("Exception in thread \"" + Thread.currentThread().name + "\""
                               + fullExceptionType + message())
        }
        else
        {
            builder.appendLine(rightAlign("($exceptionType)", 40, ' '))
        }
        var index = 0
        val limit = 60
        val include = limit / 2
        var omitted = false
        for (frame in frames)
        {
            val omit = index > include && index < frames.size - include
            if (omit)
            {
                if (!omitted)
                {
                    builder.appendLine("  ... (" + (frames.size - limit) + " frames omitted)")
                    omitted = true
                }
            }
            else
            {
                builder.appendLine(if (full) frame.full() else frame.simplified())
            }
            index++
        }
        if (cause != null)
        {
            builder.appendLine(cause.toString())
        }
        return builder.toString().replaceAll("\\$", ".")
    }

    companion object
    {
        /**
         * Returns stack traces for all threads
         */
        fun allThreads(): Map<Thread, StackTrace>
        {
            val traces: MutableMap<Thread, StackTrace> = HashMap()
            for (entry in Thread.getAllStackTraces().entries)
            {
                traces[entry.getKey()] = StackTrace(null, entry.getValue())
            }
            return traces
        }
    }
}