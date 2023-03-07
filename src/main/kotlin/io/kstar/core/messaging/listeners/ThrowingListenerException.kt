package io.kstar.core.messaging.listeners

import com.telenav.kivakit.core.messaging.Message

/**
 * An exception that the messaging system does not trap during the message broadcasting process (see the method
 * [Multicaster.transmit] for details).
 *
 * @author Jonathan Locke
 * @see ThrowingListener
 */
class ThrowingListenerException(message: Message?) : MessageException(message)