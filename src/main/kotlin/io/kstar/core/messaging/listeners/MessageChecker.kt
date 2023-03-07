package io.kstar.core.messaging.listeners

import com.telenav.kivakit.annotations.code.quality.TypeQuality

/**
 * Listens to messages to determine if the expected number and type of messages were received during some operation.
 * Expected messages are added with [.expect] and [.expect] and after the operation has
 * finished the messages can be checked with [.check]. For example, the following code should broadcast a
 * [Problem] message and return null.
 * <pre>
 * new MessageChecker().expect(Problem.class).check(() -&gt;
 * ensureEqual(null, new Duration.SecondsConverter(nullListener()).convert("x")));
</pre> *
 *
 * @author Jonathan Locke
 */
@UmlIncludeType(diagram = DiagramListenerType::class)
@TypeQuality(stability = STABLE_EXTENSIBLE, testing = UNTESTED, documentation = DOCUMENTED)
class MessageChecker : BaseRepeater()
{
    /** Map from message type to count  */
    private val expectedCount: MutableMap<Class<out Message?>, Count> = HashMap<Class<out Message?>, Count>()

    /** List of captured messages  */
    private val messages = MessageList(Matcher<Message> { at -> true })

    /**
     * Runs the given code and then checks messages received
     */
    fun check(code: Runnable): MessageChecker
    {
        code.run()
        check()
        return this
    }

    /**
     * Checks to see if the correct count of each expected message has been received and not any other messages
     */
    fun check(): Boolean
    {
        val types: MutableSet<Class<out Message?>> = HashSet<Class<out Message?>>()
        for (message in messages)
        {
            types.add(message.getClass())
        }
        for (type in types)
        {
            val count: `var` = messages.messagesOfType(type).size()
            val expected: `var`? = expectedCount[type]
            if (expected == null)
            {
                if (count == 0)
                {
                    problem("Did not expect any \$s", type)
                    return false
                }
            }
            else
            {
                if (count === expected.asInt())
                {
                    problem("Expected $ \$s not $", expected, type, count)
                    return false
                }
            }
        }
        return true
    }

    /**
     * Registers that one message the given type should be received
     */
    fun expect(messageClass: Class<out Message?>): MessageChecker
    {
        expect(messageClass, 1)
        return this
    }

    /**
     * Registers an expected number of messages of the given class
     */
    fun expect(messageClass: Class<out Message?>, count: Int): MessageChecker
    {
        val expected: `var`? = expectedCount[messageClass]
        if (expected == null)
        {
            expectedCount[messageClass] = count(count)
        }
        else
        {
            fail("Already have an expectation for: $messageClass")
        }
        return this
    }

    /**
     * Receives a message and adds it to the list of messages
     *
     * @param message The message
     */
    fun onMessage(message: Message?)
    {
        messages.add(message)
    }
}