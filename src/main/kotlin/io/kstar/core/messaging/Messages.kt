package io.kstar.core.messaging

import com.telenav.kivakit.core.collections.map.StringMap

/**
 * Factory methods and parsing for messages.
 *
 *
 * **Parsing**
 *
 *
 *  * [.parseMessageType]
 *
 *
 *
 * **Factory Methods**
 *
 *
 *  * [.messageForType]
 *  * [.messages]
 *
 *
 * @author Jonathan Locke
 */
object Messages
{
    /** Map from string to message prototype  */
    private var messagePrototypes: StringMap<Message>? = null

    /**
     * Gets a message prototype for the given type
     *
     * @param type The type of message
     */
    fun messageForType(type: Class<out Message?>): Message?
    {
        return parseMessageType(throwingListener(), type.simpleName)
    }

    /**
     * **Not public API**
     *
     *
     *
     * Returns a map of KivaKit's built-in messages
     *
     */
    fun messages(): StringMap<Message>
    {
        if (messagePrototypes == null)
        {
            messagePrototypes = StringMap()
        }
        return messagePrototypes
    }

    /**
     * Returns a new message instance
     *
     * @param listener The listener to call with any problems
     * @param type The type of message to create
     * @param message The message text
     * @param arguments Formatting arguments
     * @return The message
     */
    fun <MessageType : Message?> newMessage(listener: Listener,
                                            type: Class<MessageType>,
                                            message: String?,
                                            arguments: Array<Any?>?): MessageType?
    {
        return try
        {
            type.getConstructor(String::class.java, Array<Any>::class.java).newInstance(message, arguments)
        }
        catch (e: Exception)
        {
            listener.problem(e, "Unable to create instance: $", type)
            null
        }
    }

    /**
     * Parses the given message type
     *
     * @param listener The listener to report errors to
     * @param typeName The message type name
     */
    fun parseMessageType(listener: Listener, typeName: String?): Message?
    {
        initialize()
        return listener.problemIfNull(messagePrototypes.get(typeName), "Invalid message name: $", typeName)
    }

    private fun initialize()
    {
        // Pre-populate message prototypes

        // Lifecycle messages
        OperationStarted()
        OperationSucceeded()
        OperationFailed()
        OperationHalted()

        // Progress messages
        Step()
        Alert()
        CriticalAlert()
        Information()
        Problem()
        Glitch()
        Trace()
        Warning()
    }
}