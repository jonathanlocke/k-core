package io.kstar.core.messaging.listeners

import com.telenav.kivakit.annotations.code.quality.TypeQuality

/**
 * A list of messages that listens for and adds incoming messages. Only messages that are accepted by a [Matcher]
 * (or [Filter] subclass) are added.
 *
 *
 * **List Methods**
 *
 *
 *
 * This list of messages can be rebroadcast with [Broadcaster.transmitAll]. A filtered list of messages
 * can be retrieved with [.matching] and a list of formatted messages with [.formatted].
 *
 *
 *
 * **Superinterface Methods**
 *
 *
 *
 * The superinterface [MessageCounter] provides methods that count messages and collect statistics.
 *
 *
 *
 * **Factory Methods**
 *
 *
 *  * [.emptyMessageList]
 *
 *
 *
 * **Counting Messages**
 *
 *
 *  * [.count]
 *  * [.count]
 *  * [.countWorseThanOrEqualTo]
 *  * [.countWorseThanOrEqualTo]
 *
 *
 *
 * **Filtering**
 *
 *
 *  * [.matching]
 *  * [.messagesOfType]
 *
 *
 *
 * **Rebroadcasting**
 *
 *
 *  * [.broadcastTo]
 *
 *
 * @author Jonathan Locke
 */
@Suppress("unused")
@UmlIncludeType(diagram = DiagramListenerType::class)
@TypeQuality(stability = STABLE_EXTENSIBLE, testing = UNTESTED, documentation = DOCUMENTED)
open class MessageList(maximumSize: Maximum?, filter: Matcher<Message?>) : ObjectList<Message?>(maximumSize), MessageCounter
{
    /** The message filter to include messages in this list  */
    private val filter: Matcher<Message?>

    @JvmOverloads
    constructor(filter: Matcher<Message?> = acceptAll()) : this(MAXIMUM, filter)

    init
    {
        this.filter = filter
    }

    /**
     * Transmits all the messages in this list to the given listener
     */
    fun broadcastTo(listener: Listener?)
    {
        forEach(listener::receive)
    }

    /**
     * Runs the given code. If it throws an exception, it is captured in this message list as a problem.
     *
     * @param code The code to execute
     * @param message The problem message to use if something goes wrong
     * @param arguments The arguments to the problem message
     * @return The value if the code executed without throwing and exception, otherwise null
     */
    fun <T> captureMessages(code: UncheckedCode<T>, message: String?, vararg arguments: Any?): T?
    {
        return try
        {
            code.run()
        }
        catch (e: Exception)
        {
            problem(message, arguments)
            null
        }
    }

    /**
     * {@inheritDoc}
     */
    fun copy(): MessageList
    {
        val copy = super.copy() as MessageList
        copy.filter = filter
        return copy
    }

    /**
     * {@inheritDoc}
     */
    override fun count(status: Message.Status): Count
    {
        var count = 0
        for (message in this)
        {
            if (message.status() === status)
            {
                count++
            }
        }
        return Count.count(count)
    }

    /**
     * {@inheritDoc}
     */
    override fun count(type: Class<out Message?>): Count
    {
        var count = 0
        for (message in this)
        {
            if (type.isAssignableFrom(message.getClass()))
            {
                count++
            }
        }
        return Count.count(count)
    }

    /**
     * {@inheritDoc}
     */
    override fun countWorseThanOrEqualTo(status: Message.Status?): Count
    {
        var count = 0
        for (message in this)
        {
            if (message.status().isWorseThanOrEqualTo(status))
            {
                count++
            }
        }
        return Count.count(count)
    }

    /**
     * {@inheritDoc}
     */
    override fun equals(`object`: Any?): Boolean
    {
        // Local fields are not considered
        return super.equals(`object`)
    }

    /**
     * The messages in this list, formatted
     */
    fun formatted(): StringList
    {
        val messages = StringList(maximumSize())
        for (message in this)
        {
            messages.add(message.formatted())
        }
        return messages
    }

    /**
     * {@inheritDoc}
     */
    fun matching(filter: Matcher<Message?>?): MessageList
    {
        return super.matching(filter) as MessageList
    }

    /**
     * @param type The message type
     * @return The messages of the given type
     */
    fun messagesOfType(type: Class<out Message?>): MessageList
    {
        val messages = MessageList()
        for (`object` in this)
        {
            if (type.isAssignableFrom(`object`.getClass()))
            {
                messages.add(`object`)
            }
        }
        return messages
    }

    /**
     * When we hear a message, add it to the list.
     *
     * @param message The message to add
     */
    open fun onMessage(message: Message?)
    {
        if (filter.matches(message))
        {
            add(message)
        }
    }

    /**
     * {@inheritDoc}
     */
    fun onNewInstance(): MessageList
    {
        return MessageList(filter)
    }

    override fun toString(): String
    {
        return if (isEmpty()) "[No issues]" else formatted().prefixedWith(bullet()).titledBox("Issues")
    }

    fun with(vararg value: Message?): MessageList
    {
        return super.with(value) as MessageList
    }

    fun with(value: Iterable<Message?>?): MessageList
    {
        return super.with(value) as MessageList
    }

    fun with(value: Collection<Message?>?): MessageList
    {
        return super.with(value) as MessageList
    }

    fun without(matcher: Matcher<Message?>?): MessageList
    {
        return super.without(matcher) as MessageList
    }

    fun without(message: Message?): MessageList
    {
        return super.without(message) as MessageList
    }

    fun without(that: Collection<Message?>?): MessageList
    {
        return super.without(that) as MessageList
    }

    fun without(that: Array<Message?>?): MessageList
    {
        return super.without(that) as MessageList
    }

    protected fun onNewCollection(): MessageList
    {
        return MessageList()
    }

    protected fun onNewList(): MessageList
    {
        return MessageList()
    }

    companion object
    {
        private val EMPTY: MessageList = object : MessageList()
        {
            override fun onMessage(message: Message?)
            {
                unsupported("The message list returned by empty() is read-only")
            }
        }

        fun captureMessages(broadcaster: Broadcaster, code: Runnable): MessageList
        {
            val issues = MessageList()
            issues.listenTo(broadcaster)
            try
            {
                code.run()
            }
            catch (e: Exception)
            {
                issues.problem(e, "Code threw exception")
            }
            finally
            {
                broadcaster.removeListener(issues)
            }
            return issues
        }

        fun emptyMessageList(): MessageList
        {
            return EMPTY
        }
    }
}