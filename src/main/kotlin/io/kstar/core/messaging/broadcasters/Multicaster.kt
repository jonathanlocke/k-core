package io.kstar.core.messaging.broadcasters

import com.telenav.kivakit.annotations.code.quality.TypeQuality

/**
 * A multicaster is a broadcaster which can have more than one listener. As with any broadcaster, listeners can be added
 * to a multicaster with [.addListener] and can be cleared with [.clearListeners].
 * MessageTransceiver can be broadcast to all listeners with [.transmit].
 *
 *
 * If a class is already extending some other base class (and since Java does not support mix-ins) an instance of
 * [Multicaster] can be aggregated and its methods delegated to implement the [Broadcaster] interface This
 * is not automatic in Java, but requires a minimal amount of code:
 * <pre>
 * public class A extends B implements Repeater&lt;Message&gt;
 * {
 * private final BaseRepeater&lt;Message&gt; repeater = new BaseRepeater&lt;&gt;();
 *
 * public void broadcast(Message message)
 * {
 * this.repeater.broadcast(message);
 * }
 *
 * public void addListener(Listener&lt;Message&gt; listener)
 * {
 * this.repeater.addListener(listener);
 * }
 *
 * public void clearListeners()
 * {
 * this.repeater.clearListeners();
 * }
 *
 * public boolean hasListeners()
 * {
 * return this.repeater.hasListeners();
 * }
 * }
</pre> *
 *
 * @author Jonathan Locke
 * @see Broadcaster
 *
 * @see Listener
 */
@Suppress("unused", "SpellCheckingInspection")
@UmlIncludeType(diagram = DiagramRepeater::class)
@TypeQuality(stability = STABLE, testing = UNTESTED, documentation = DOCUMENTED)
open class Multicaster : Broadcaster
{
    /** This multi-caster audience  */
    @UmlAggregation
    @Transient
    private val audience: MutableList<AudienceMember> = ArrayList()

    /** The class context for any debug object associated with this multicaster  */
    @Transient
    private val debugClassContext: Class<*>

    /** The code context for any debug object associated with this multicaster  */
    @Transient
    private var debugCodeContext: CodeContext? = null

    /** Lock for synchronizing access to the audience across threads  */
    @Transient
    private var lock: ReadWriteLock? = null

    /** The name of this object  */
    @Transient
    private var objectName: String?

    /** Any broadcaster that is sending messages through this multicaster  */
    @Transient
    private var source: Broadcaster? = null
    /**
     * {@inheritDoc}
     */
    /** True if this multicaster is enabled to transmit  */
    @Transient
    var isTransmitting = false
        private set

    constructor(objectName: String?, debugClassContext: Class<*>)
    {
        this.objectName = objectName
        this.debugClassContext = debugClassContext
        debugCodeContext(CodeContext(debugClassContext))
    }

    constructor(debugClassContext: Class<*>)
    {
        objectName = syntheticName(this)
        this.debugClassContext = debugClassContext
        debugCodeContext(CodeContext(debugClassContext))
    }

    protected constructor(objectName: String?)
    {
        this.objectName = objectName
        debugClassContext = javaClass
        debugCodeContext(CodeContext(javaClass))
    }

    protected constructor()
    {
        objectName = syntheticName(this)
        debugClassContext = javaClass
        debugCodeContext(CodeContext(javaClass))
    }

    protected constructor(that: Multicaster)
    {
        objectName = that.objectName
        source = that.source
        debugCodeContext = that.debugCodeContext
        debugClassContext = that.debugClassContext
        lock = that.lock
        isTransmitting = that.isTransmitting
        audience.addAll(that.audience)
        assert(debugCodeContext != null)
        assert(debugCodeContext.typeName() != null)
    }

    /**
     * {@inheritDoc}
     */
    fun addListener(listener: Listener, filter: Filter<Transmittable?>)
    {
        ensure(listener !== this, "Cannot listen to yourself")

        // If the listener to this multicaster is a broadcaster,
        if (listener is Broadcaster)
        {
            // then we are the parent of that broadcaster. This information is used to provide the listener
            // chain when it doesn't terminate correctly.
            (listener as Broadcaster).messageSource(this)
        }
        ensureNotNull(listener)
        lock().write {
            val receiver = AudienceMember(listener, filter)
            if (!audience.contains(receiver))
            {
                audience.add(receiver)
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    fun clearListeners()
    {
        lock().write(audience::clear)
    }

    /**
     * {@inheritDoc}
     */
    fun debugClassContext(): Class<*>
    {
        return debugClassContext
    }

    /**
     * {@inheritDoc}
     */
    fun debugCodeContext(): CodeContext?
    {
        return debugCodeContext
    }

    /**
     * {@inheritDoc}
     */
    fun debugCodeContext(context: CodeContext?)
    {
        debugCodeContext = context
    }

    /**
     * {@inheritDoc}
     */
    fun hasListeners(): Boolean
    {
        return lock().read { !audience.isEmpty() }
    }

    val isDeaf: Boolean
        /**
         * Returns true if no listeners can hear any messages
         */
        get() = lock().read {
            for (receiver in audience)
            {
                if (!receiver.listener().isDeaf())
                {
                    return@read false
                }
            }
            true
        }

    /**
     * Returns the chain of broadcasters that leads to this [Multicaster] in reversed order so this broadcaster is
     * at the top of the stack.
     */
    fun listenerChain(): StringList
    {
        val chain = stringList()
        var at = this as Broadcaster
        while (at.messageSource() != null)
        {
            val owner = Mixins.owner(at)
            if (owner != null)
            {
                at = owner
            }
            var name = simpleName(at.getClass())
            if (at.listeners().isEmpty())
            {
                name += " (No Listener)"
            }
            chain.add(name)
            at = at.messageSource()
        }
        return chain.reversed()
    }

    /**
     * Returns an indented tree of listeners to this multi-caster
     */
    fun listenerTree(): String
    {
        val builder = IndentingStringBuilder(TEXT, indentation(4))
        listenerTree(builder)
        return builder.toString()
    }

    /**
     * Returns all listeners to this object
     */
    fun listeners(): List<Listener>
    {
        return lock().read {
            val list = ArrayList<Listener>()
            for (member in audience)
            {
                list.add(member.listener())
            }
            list
        }
    }

    /**
     * {@inheritDoc}
     */
    fun messageSource(): Broadcaster?
    {
        return source
    }

    /**
     * {@inheritDoc}
     */
    fun messageSource(source: Broadcaster?)
    {
        this.source = source
    }

    /**
     * {@inheritDoc}
     */
    fun objectName(name: String?)
    {
        objectName = name
    }

    /**
     * {@inheritDoc}
     */
    fun objectName(): String
    {
        return if (objectName != null) objectName!! else syntheticName(this)
    }

    /**
     * Removes the given listener from receiving broadcast messages
     */
    fun removeListener(listener: Listener)
    {
        lock().write { audience.remove(AudienceMember(listener, null)) }
    }

    /**
     * {@inheritDoc}
     */
    fun <M : Transmittable?> transmit(message: M): M
    {
        lock().read {
            // If there is an audience,
            if (!audience.isEmpty())
            {
                // add this broadcaster's context to the message, if possible,
                if (message is OperationMessage)
                {
                    (message as OperationMessage).context(debugCodeContext)
                }

                // then, for each member of the audience,
                for (member in audience)
                {
                    try
                    {
                        // hand them the message.
                        member.receive(message)
                    }
                    catch (e: ThrowingListenerException)
                    {
                        // If we get an exception of this special type, it was thrown
                        // by ThrowingListener and so it should not be trapped here
                        // because the intent of ThrowingListener is to throw an
                        // exception when given a failure message.
                        throw e
                    }
                    catch (e: Exception)
                    {
                        // By trapping all other exceptions, we ensure that all members
                        // of the audience receive the message, even if a prior listener
                        // threw an exception. This is important because the listeners
                        // may not be of equal importance to the program. It would be
                        // undesirable for an exception in a trivial piece of code to
                        // cause a message to be dropped that is important to a key piece
                        // of code.
                        LOGGER.problem(e, "When "
                                          + objectName()
                                          + " tried to deliver a message to "
                                          + member
                                          + ", the listener threw an exception")
                    }
                }
            }
            else
            {
                // If there is no receiver for this message, and it can be logged,
                if (message is Message)
                {
                    // then log it (to the global logger).
                    globalListener().receive(message as Message)
                }

                // If the KIVAKIT_NO_LISTENER_ERROR system property is set to true,
                if (isTrue(operatingSystem().systemPropertyOrEnvironmentVariable("KIVAKIT_NO_LISTENER_ERROR", "false")))
                {
                    // throw an error to flag lost messages.
                    throw NoListenerError("No listener found (-DKIVAKIT_NO_LISTENER_ERROR=false will suppress this error):\n\n$", listenerChain()
                        .numbered()
                        .indented(4))
                }
            }
        }
        return message
    }

    /**
     * {@inheritDoc}
     */
    fun transmitting(transmitting: Boolean)
    {
        isTransmitting = transmitting
    }

    private fun listenerTree(builder: IndentingStringBuilder)
    {
        lock().read {
            builder.appendLine(objectName())
            builder.indent()
            for (receiver in audience)
            {
                if (receiver.listener() is Multicaster)
                {
                    (receiver.listener() as Multicaster).listenerTree(builder)
                }
                else
                {
                    builder.appendLine(receiver.listener().objectName())
                }
            }
            builder.unindent()
        }
    }

    private fun lock(): ReadWriteLock?
    {
        if (lock == null)
        {
            lock = ReadWriteLock()
        }
        return lock
    }

    companion object
    {
        /** Console logger for serious messaging problems  */
        private val LOGGER: Logger = ConsoleLogger()
    }
}