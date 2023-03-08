package io.kstar.core.messaging

import com.telenav.kivakit.annotations.code.quality.TypeQuality
import io.kstar.annotations.documentation.UmlIncludeRelatedType
import io.kstar.annotations.documentation.UmlIncludeType
import io.kstar.annotations.documentation.UmlRelation
import io.kstar.core.messaging.Message.OperationStatus
import io.kstar.core.messaging.Message.Status
import io.kstar.internal.Diagrams.DiagramBroadcaster
import io.kstar.internal.Diagrams.DiagramListener
import io.kstar.internal.Diagrams.DiagramMessaging

/**
 * An interface to retrieve the basic attributes of a message, find out what it means and format it as text. A message's
 * text is un-formatted when it is created. The message text and any arguments must be passed to [Formatter] to
 * produce a final formatted message, which can be retrieved with [.formatted]. This is a useful
 * design because it is cheaper to construct messages if the formatting is delayed until the formatted string is need,
 * for example, when a log message is added to a formatted text log.
 *
 *
 * **Message Attributes**
 *
 *  * [.text] - The un-formatted text of the message
 *  * [.arguments] - Arguments that can be applied to the message text when formatting it
 *  * [.cause] - Any exception associated with the message
 *  * [.created] - The time that the message was created
 *  * [.severity] - The severity of the message
 *  * [.operationStatus] - The operation that the message relates to, if any
 *  * [.status] - The status of a step in an ongoing operation that the message relates to, if any
 *
 *
 *
 * **Types of MessageTransceiver**
 *
 *
 * Different types of messages relate to different aspects of a running program. MessageTransceiver relating to a larger goal
 * of the program, such as converting a file or computing a route are *operation lifecycle* messages and extend
 * [OperationLifecycleMessage]. MessageTransceiver that relate to the smaller steps required to achieve an operation are
 * *status* messages and extend [OperationStatusMessage]. If the [.operationStatus] method returns a non-null
 * value, the message relates to the operation lifecycle. If the [.status] method returns a non-null value the
 * message relates to the most recent step in the current operation.
 *
 *
 * Each class implementing [Message] will define one or the other of these two return values. For example,
 * [OperationHalted] is a lifecycle message which means that the current operation has transitioned to the
 * [OperationStatus.HALTED] state. [Warning] relates to a step in an operation which [Status.SUCCEEDED],
 * even if it did so imperfectly. Inspection of message classes will reveal what their meaning is with respect to
 * operations and the steps used to complete them.
 *
 *
 * **Operation Lifecycle MessageTransceiver**
 *
 *
 * An [OperationStatus] lifecycle message relates to a state change in the status of an operation. Operations start,
 * run and then complete with some kind of result:
 *
 *  * [OperationStatus.STARTED] - The operation has started
 *  * [OperationStatus.RUNNING] - The operation is running
 *  * [OperationStatus.SUCCEEDED] - The operation completed successfully
 *  * [OperationStatus.FAILED] - The operation completed but did not succeed in meeting its goal
 *  * [OperationStatus.HALTED] - The operation was unable to complete
 *
 *
 *
 * **Operation Status MessageTransceiver**
 *
 *
 * A [Status] message relates to the result of executing a step in a larger operation:
 *
 *  * [Status.SUCCEEDED] - The step succeeded and the message is reporting progress: [Step], [Information], [StepSuccess], [Trace]
 *  * [Status.COMPLETED] - The step completed and produced a result but there was an actual or potential negative effect that should be noted: {[Warning]}
 *  * [Status.RESULT_COMPROMISED] - The step completed successfully amd data was not discarded, but the result may be partly invalid: [Glitch]
 *  * [Status.RESULT_INCOMPLETE] - The step completed but some aspect of the result had to be discarded: [StepIncomplete]
 *  * [Status.PROBLEM] - The step didn't complete correctly because something needs attention: [Problem]
 *  * [Status.FAILED] - The step did not complete or did not produce any usable result: [Alert], [CriticalAlert], [StepFailure]
 *
 *
 * @author Jonathan Locke
 */
@Suppress("unused")
@UmlIncludeRelatedType(DiagramBroadcaster::class)
@UmlIncludeRelatedType(DiagramMessaging::class)
@UmlIncludeRelatedType(DiagramListener::class)
@UmlRelation(label = "formats with", diagram = DiagramMessaging::class, referent = Formatter::class)
@TypeQuality(stability = STABLE, testing = UNTESTED, documentation = DOCUMENTED)
interface Message : Transmittable, Triaged, StringFormattable, Named
{
    /**
     * The meaning of a message if it represents a state change in the lifecycle of an operation
     */
    @UmlIncludeType(diagram = DiagramMessaging::class)
    enum class OperationStatus
    {
        /** This message does not represent an operation lifecycle state transition  */
        NOT_APPLICABLE,

        /** The operation has started  */
        STARTED,

        /** The operation is executing  */
        RUNNING,

        /** The operation succeeded  */
        SUCCEEDED,

        /** The operation completed but did not succeed  */
        FAILED,

        /** The operation halted before completion due to an unrecoverable failure  */
        HALTED;

        fun failed(): Boolean
        {
            return this == FAILED || this == HALTED
        }

        fun halted(): Boolean
        {
            return this == FAILED || this == HALTED
        }

        fun succeeded(): Boolean
        {
            return this == SUCCEEDED
        }
    }

    /**
     * The meaning of a message if it relates to the current step in an ongoing operation
     */
    @UmlIncludeType(diagram = DiagramMessaging::class)
    enum class Status
    {
        /** This message does not represent a step in an ongoing operation  */
        NOT_APPLICABLE,

        /** The step succeeded and produced a result without any significant negative effect to forward progress  */
        SUCCEEDED,

        /**
         * The step completed and the result was not compromised but some actual or potential minor negative effect is
         * being reported
         */
        COMPLETED,

        /** The step succeeded but some aspect of the result was compromised, but the result is not incomplete  */
        RESULT_COMPROMISED,

        /** The result is incomplete because something had to be discarded  */
        RESULT_INCOMPLETE,

        /** The step didn't complete correctly because something needs attention  */
        PROBLEM,

        /** The step did not produce any correct result  */
        FAILED;

        fun failed(): Boolean
        {
            return this == FAILED || this == PROBLEM
        }

        /**
         * @return True if the status is above the given status in terms of negative effect
         */
        fun isWorseThan(minimum: Status): Boolean
        {
            return ordinal > minimum.ordinal
        }

        /**
         * @return True if the status is above the given status in terms of negative effect
         */
        fun isWorseThanOrEqualTo(minimum: Status): Boolean
        {
            return ordinal >= minimum.ordinal
        }

        fun succeeded(): Boolean
        {
            return this == SUCCEEDED
        }
    }

    /**
     * Returns message arguments used to produce formatted message
     */
    fun arguments(): Array<Any?>?

    /**
     * Returns this message as an exception
     */
    fun asException(): RuntimeException?

    /**
     * Returns any cause or null if none
     */
    fun cause(): Throwable?

    /**
     * **Not public API**
     *
     *
     *
     * Sets the cause for this message
     *
     */
    fun cause(cause: Throwable?): Message?

    /**
     * Returns the context where this message was created
     */
    fun context(): CodeContext?

    /**
     * Returns the time at which this message was created
     */
    fun created(): Time?

    /**
     * Returns the formatted message without stack trace information
     */
    fun description(): String?

    /**
     * Returns formatted message, including any stack trace information
     */
    fun formatted(vararg format: MessageFormat?): String?
    fun formatted(): String?
    {
        return formatted(FORMATTED)
    }

    /**
     * Returns the importance of this message, without respect to severity
     */
    @UmlRelation(label = "message importance")
    fun importance(): Importance
    val isFailure: Boolean
        get() = status().failed() || operationStatus().failed()

    @UmlExcludeMember
    fun isMoreImportantThan(type: Class<out Message?>?): Boolean
    {
        return importance().isGreaterThan(Importance.importanceOfMessage(type))
    }

    @UmlExcludeMember
    fun isMoreImportantThanOrEqualTo(type: Class<out Message?>?): Boolean
    {
        return importance().isGreaterThanOrEqualTo(Importance.importanceOfMessage(type))
    }

    /**
     * Returns true if the status of this message is worse than the given value
     */
    @UmlExcludeMember
    fun isWorseThan(status: Status): Boolean
    {
        return status().isWorseThan(status)
    }

    /**
     * Returns true if the status of this message is worse than the given message
     */
    @UmlExcludeMember
    fun <T : Message?> isWorseThan(message: Class<T>?): Boolean
    {
        return isWorseThan(newInstance(message).status())
    }

    /**
     * Returns true if the status of this message is worse than the given value
     */
    @UmlExcludeMember
    fun isWorseThanOrEqualTo(status: Status): Boolean
    {
        return status().isWorseThanOrEqualTo(status)
    }

    /**
     * Returns true if the status of this message is worse than the given message
     */
    @UmlExcludeMember
    fun <T : Message?> isWorseThanOrEqualTo(message: Class<T>?): Boolean
    {
        return isWorseThanOrEqualTo(newInstance(message).status())
    }

    /**
     * Returns the frequency with which this message should be logged or null if the message should always be logged.
     *
     *
     * NOTE: Message identity is determined by looking at the un-formatted message returned by message(). If message()
     * does not return a constant string, a bounded map error may occur as the system attempts to track too many message
     * frequencies.
     */
    fun maximumFrequency(): Frequency?
    fun name(): String?
    {
        return simpleName(javaClass)
    }

    /**
     * Returns the operational status represented by this message, if any
     */
    @UmlRelation(label = "operation status")
    fun operationStatus(): OperationStatus

    /**
     * Returns the severity of this message
     */
    @UmlRelation(label = "message severity")
    fun severity(): Severity?

    /**
     * Returns any stack trace associated with this message (or null if none applies)
     */
    fun stackTrace(): StackTrace?

    /**
     * Returns the status of a step in an ongoing operation, if the message is relevant to this
     */
    @UmlRelation(label = "message status")
    fun status(): Status

    /**
     * Returns the un-formatted message text. See [Formatter.format] for details on how this
     * text is formatted using the [.arguments] to the message.
     */
    fun text(): String?

    /**
     * Throws this message as a [MessageException].
     */
    @UmlExcludeMember
    fun throwMessage()
    {
        throw MessageException(this)
    }

    /**
     * The formatted message, including any exception
     */
    override fun toString(): String

    companion object
    {
        /**
         * Returns the given string with message markers escaped
         */
        fun escapeMessageText(text: String?): String?
        {
            return replaceAll(text, "$", "$$")
        }

        /**
         * Returns the message with the given simple name (Problem, Warning, etc.)
         */
        fun parseMessageName(listener: Listener?, name: String?): Message?
        {
            return parseMessageType(listener, name)
        }
    }
}