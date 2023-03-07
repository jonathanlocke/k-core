package io.kstar.core.messaging.repeaters

import com.telenav.kivakit.annotations.code.quality.TypeQuality

/**
 * A multicasting repeater which repeats all the messages it receives to a set of listeners. A base repeater is a
 * convenient way of implementing the [Repeater] interface by extension.
 *
 *
 *
 * If a class is already extending some other base class a stateful [RepeaterMixin] can be used:
 *
 *
 * <pre>
 * public class A extends B implements RepeaterTrait
 * {
 * }
</pre> *
 *
 * @author Jonathan Locke
 * @see Listener
 *
 * @see Multicaster
 *
 * @see Repeater
 *
 * @see RepeaterMixin
 *
 * @see Mixin
 */
@UmlIncludeType(diagram = DiagramRepeater::class)
@TypeQuality(stability = STABLE_EXTENSIBLE, testing = UNTESTED, documentation = DOCUMENTED)
open class BaseRepeater : Multicaster, Repeater
{
    /** The number of failures that this repeater has seen  */
    @Transient
    private val failures: AtomicInteger = AtomicInteger()

    constructor(objectName: String?, classContext: Class<*>?) : super(objectName, classContext)
    {
        checkInheritance()
    }

    constructor(classContext: Class<*>?) : super(classContext)
    {
        checkInheritance()
    }

    constructor()
    {
        checkInheritance()
    }

    protected constructor(objectName: String?) : super(objectName)
    {
        checkInheritance()
    }

    /**
     * Returns true if this repeater has not received any failure message, as determined by calling
     * [Message.isFailure].
     *
     * @return True if no failure has occurred.
     */
    fun ok(): Boolean
    {
        return failures.get() == 0
    }

    open fun onMessage(message: Message?)
    {
    }

    /**
     * When a message is received, calls [.onMessage] and then if [.isRepeating] returns true,
     * calls [.transmit].
     *
     * @param transmittable The message to repeat
     */
    fun onReceive(transmittable: Transmittable)
    {
        // Cast the message,
        val message: `var` = transmittable as Message

        // and if it represents a failure,
        if (message.isFailure())
        {
            // increment the number of failures
            failures.getAndIncrement()
        }

        // Process the message normally,
        onMessage(message)

        // then if we are enabled for repeating,
        if (isRepeating())
        {
            // re-transmit the message.
            transmit(message)
        }
    }

    private fun checkInheritance()
    {
        ensure(this !is RepeaterMixin, "A class extending BaseRepeater cannot also have a RepeaterMixin")
    }
}