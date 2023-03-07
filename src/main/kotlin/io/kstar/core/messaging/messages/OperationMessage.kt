package io.kstar.core.messaging.messages

import com.telenav.kivakit.annotations.code.quality.TypeQuality

/**
 * Base implementation of the [Message] interface. Represents a message destined for a [Listener] such as a
 * [Logger] with arguments which can be interpolated if the message is formatted with
 * [.formatted]. All [OperationMessage]s have the attributes defined in [Message].
 *
 *
 * For messages that might be sent to frequently, [.maximumFrequency] can be used to specify that the
 * receiver only handle the message every so often. [Log]s support this feature, so it is possible to easily tag a
 * message as something to only log once in a while.
 *
 *
 * **Formatting**
 *
 *
 *  * [.formatted]
 *  * [.formatted]
 *
 *
 *
 * **Properties**
 *
 *
 *  * [.arguments]
 *  * [.arguments]
 *  * [.cause]
 *  * [.cause]
 *  * [.context]
 *  * [.context]
 *  * [.created]
 *  * [.created]
 *  * [.description]
 *  * [.importance]
 *  * [.maximumFrequency]
 *  * [.maximumFrequency]
 *  * [.messageForType]
 *  * [.severity]
 *  * [.stackTrace]
 *  * [.stackTrace]
 *
 *
 *
 * **Conversions**
 *
 *
 *  * [.asString]
 *  * [.asException]
 *
 *
 * @see Message
 *
 * @see Log
 *
 * @see Listener
 *
 * @see Frequency
 *
 * @see Severity
 */
@Suppress("unused")
@UmlIncludeType(diagram = DiagramMessageType::class)
@UmlExcludeSuperTypes([Named::class])
@TypeQuality(stability = STABLE_EXTENSIBLE, testing = TESTING_NOT_NEEDED, documentation = DOCUMENTED)
abstract class OperationMessage : Named, Message
{
    /** Formatting arguments  */
    private var arguments: Array<Any>

    /** Any exception that was a cause of this message  */
    @Transient
    private var cause: Throwable? = null

    /** The code context that transmitted this message  */
    private var context: CodeContext? = null

    /** The time this message was created  */
    private var created: Time = now()

    /** Any formatted message string  */
    private var formattedMessage: String? = null

    /** The maximum frequency at which this message should be transmitted  */
    private var maximumFrequency: Frequency? = null

    /** The message text  */
    private var message: String? = null

    /** Any associated stack trace  */
    private var stackTrace: StackTrace? = null

    protected constructor(message: String?)
    {
        this.message = message
        messages().put(name(), this)
    }

    protected constructor()
    {
        messages().put(name(), this)
    }

    /**
     * {@inheritDoc}
     */
    fun arguments(): Array<Any>
    {
        return arguments
    }

    /**
     * Sets the formatting arguments for this message.
     *
     * @param arguments The arguments
     */
    fun arguments(arguments: Array<Any>)
    {
        this.arguments = arguments
    }

    /**
     * {@inheritDoc}
     */
    fun asException(): MessageException
    {
        return MessageException(this)
    }

    /**
     * {@inheritDoc}
     */
    fun asString(format: Format): String
    {
        return when (format)
        {
            else -> formatted()!!
        }
    }

    /**
     * {@inheritDoc}
     */
    fun cause(): Throwable?
    {
        return cause
    }

    /**
     * {@inheritDoc}
     */
    fun cause(cause: Throwable?): OperationMessage
    {
        this.cause = cause
        return this
    }

    /**
     * {@inheritDoc}
     */
    fun context(): CodeContext?
    {
        return context
    }

    /**
     * Sets the code context for this message
     */
    fun context(context: CodeContext?)
    {
        if (this.context == null)
        {
            this.context = context
        }
    }

    /**
     * {@inheritDoc}
     */
    fun created(): Time
    {
        return created
    }

    /**
     * Sets the time of creation for this message
     */
    fun created(created: Time)
    {
        this.created = created
    }

    /**
     * Returns the formatted message without any stack trace information
     */
    fun description(): String
    {
        return format(message, arguments)
    }

    /**
     * {@inheritDoc}
     */
    override fun equals(`object`: Any): Boolean
    {
        return if (`object` is OperationMessage)
        {
            areEqualPairs(
                this.javaClass, `object`.javaClass,
                created, `object`.created,
                message, `object`.message,
                this.stackTrace, `object`.stackTrace,
                arguments, `object`.arguments)
        }
        else false
    }

    /**
     * Returns the fully formatted message including stack trace information
     */
    open fun formatted(vararg formats: MessageFormat?): String?
    {
        if (formattedMessage == null)
        {
            try
            {
                if (reentrancy.enter() === REENTERED && DETECT_REENTRANCY)
                {
                    formattedMessage = "Re-entrant message formatting detected. This could result in infinite recursion: '$message'"
                }
                else
                {
                    formattedMessage = format(message, arguments)
                }
            }
            finally
            {
                reentrancy.exit()
            }
        }
        return formattedMessage
    }

    /**
     * {@inheritDoc}
     */
    override fun hashCode(): Int
    {
        return Hash.hashMany(javaClass, created, message, stackTrace, arguments)
    }

    /**
     * {@inheritDoc}
     */
    fun importance(): Importance
    {
        return importanceOfMessage(javaClass)
    }

    /**
     * {@inheritDoc}
     */
    fun maximumFrequency(): Frequency?
    {
        return maximumFrequency
    }

    /**
     * Sets the maximum frequency at which this message can be transmitted
     */
    fun maximumFrequency(maximumFrequency: Frequency?): OperationMessage
    {
        this.maximumFrequency = maximumFrequency
        return this
    }

    /**
     * Sets the message text
     */
    fun messageForType(message: String?)
    {
        this.message = message
    }

    /**
     * {@inheritDoc}
     */
    open fun severity(): Severity?
    {
        return NONE
    }

    /**
     * {@inheritDoc}
     */
    fun stackTrace(): StackTrace?
    {
        if (stackTrace == null)
        {
            val cause: `var` = cause()
            if (cause != null)
            {
                stackTrace = StackTrace(cause)
            }
        }
        return stackTrace
    }

    /**
     * Sets the stack trace for this message
     */
    fun stackTrace(stackTrace: StackTrace?): OperationMessage
    {
        this.stackTrace = stackTrace
        return this
    }

    /**
     * {@inheritDoc}
     */
    fun text(): String?
    {
        return message
    }

    /**
     * {@inheritDoc}
     */
    override fun toString(): String
    {
        return formatted()!!
    }

    companion object
    {
        private val reentrancy: ReentrancyTracker = ReentrancyTracker()

        /** This flag can be helpful in detecting infinite recursion of message formatting  */
        private const val DETECT_REENTRANCY = false
    }
}