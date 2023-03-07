package io.kstar.core.messaging.broadcasters

import com.telenav.kivakit.core.string.Formatter.format

/**
 * An error thrown by [Multicaster] when there is no listener to call, indicating a broken or unterminated
 * listener chain.
 *
 * @author Jonathan Locke
 */
class NoListenerError(message: String?, vararg arguments: Any?) : Error(format(message, arguments))