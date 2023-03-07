package io.kstar.core.messaging.listeners

import com.telenav.kivakit.annotations.code.quality.TypeQuality

/**
 * A [Listener], such as [MessageList] that is able to report how many of different kinds of messages it has
 * seen.
 *
 *
 * **Counting Message by Message Type**
 *
 *
 *
 * To get a count of a specific message type like [Warning] or [Problem], the method [.count]
 * can be used:
 *
 *
 * <pre>
 * var warnings = count(Warning.class)
 * var problems = count(Problem.class)</pre>
 *
 *
 *
 * To get the count of messages with a status worse than or equal to a given message type:
 *
 *
 * <pre>
 * var errors = countWorseThanOrEqualTo(Problem.class)</pre>
 *
 *
 * **Message Statistics**
 *
 *
 * To display message statistics to a user, the [.statistics] method returns a [StringList]
 * detailing how many of each message type were found:
 *
 * <pre>
 * var statistics = statistics(Warning.class, Problem.class)</pre>
 *
 *
 * **Counting MessageTransceiver by Status**
 *
 *
 *
 * The [.count] method returns the number of messages of a given [Message.Status] type it
 * has seen. For example, messages that represent failure would have the status type [Message.Status.FAILED]:
 *
 *
 * <pre>
 * var failed = count(FAILED)</pre>
 *
 * @author Jonathan Locke
 * @see Message
 *
 * @see Message.Status
 *
 * @see MessageList
 */
@Suppress("unused")
@UmlIncludeType(diagram = DiagramListenerType::class)
@TypeQuality(stability = STABLE_EXTENSIBLE, testing = UNTESTED, documentation = DOCUMENTED)
interface MessageCounter : Listener
{
    /**
     * Returns the total number of messages with the given status
     *
     * @return The message count
     */
    fun count(type: Message.Status): Count

    /**
     * Returns the total number of messages of the given type
     *
     * @return The message count
     */
    fun count(type: Class<out Message?>): Count

    /**
     * Returns the number of messages that are worse than or equal to the given status
     *
     * @return The message count
     */
    fun countWorseThanOrEqualTo(status: Message.Status?): Count

    /**
     * Returns the count of messages worse than or equal to the status of the given message type
     *
     * @param type The message type
     * @return The count of messages
     */
    fun countWorseThanOrEqualTo(type: Class<out Message?>?): Count?
    {
        return countWorseThanOrEqualTo(messageForType(type).status())
    }
    /**
     * Returns true if there are one or more messages worse than or equal to the given message type
     *
     * @return True for failure, false otherwise
     */
    /**
     * Returns true if there are one or more message(s) with a status worse than or equal to [Problem]
     *
     * @return True for failure, false otherwise
     */
    @JvmOverloads
    fun failed(type: Class<out Message?>? = Problem::class.java): Boolean
    {
        return countWorseThanOrEqualTo(type).isNonZero()
    }

    fun hasProblems(): Boolean
    {
        return problems().isNonZero()
    }

    /**
     * If the counted message represent failure, as determined by [.failed], throws an
     * [IllegalStateException] with the statistics for messages.
     */
    fun ifFailedThrow()
    {
        check(!failed()) {
            """
     Failed:
     
     ${statistics(Problem::class.java, Quibble::class.java, Warning::class.java).bulleted(2)}
     """.trimIndent()
        }
    }

    fun problems(): Count
    {
        return countWorseThanOrEqualTo(Problem::class.java)
    }

    /**
     * Returns the counts of each message with the given statuses
     *
     * @return Statistics for the given list of operation step types
     */
    fun statistics(vararg statuses: Message.Status?): StringList?
    {
        val statistics: `var` = StringList()
        for (status in statuses)
        {
            statistics.append(rightAlign(status.name(), 24, ' '))
            statistics.append(": ")
            statistics.append(count(status).asCommaSeparatedString())
        }
        return statistics
    }

    /**
     * Returns the counts of each of the given messages
     *
     * @param types The message types to include in the statistics
     * @return A string list with counts for each listed message type
     */
    fun statistics(vararg types: Class<out Message?>?): StringList?
    {
        val statistics: `var` = StringList()
        for (type in types)
        {
            val count: `var` = count(type)
            if (count != null)
            {
                statistics.append(rightAlign(pluralizeEnglish(simpleName(type)), 24, ' ')
                                  + ": " + count.asCommaSeparatedString())
            }
        }
        return statistics
    }

    /**
     * Returns true if there are no messages worse than or equal to [Problem]
     *
     * @return True for success, false otherwise
     */
    fun succeeded(): Boolean
    {
        return !failed()
    }
}