package io.kstar.core.messaging.messages.status

import com.telenav.kivakit.annotations.code.quality.TypeQuality

/**
 * A [Problem] message that indicates that some operation is not supported.
 *
 * @author Jonathan Locke
 */
@UmlIncludeType(diagram = DiagramMessageType::class)
@TypeQuality(stability = STABLE, testing = TESTING_NOT_NEEDED, documentation = DOCUMENTED)
class Unsupported : FatalProblem
{
    constructor(message: String?, vararg arguments: Any?) : super(message, *arguments)
    constructor(cause: Throwable?, message: String?, vararg arguments: Any?) : super(cause, message, *arguments)
    constructor()
}