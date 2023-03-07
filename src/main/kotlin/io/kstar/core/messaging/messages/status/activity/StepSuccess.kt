package io.kstar.core.messaging.messages.status.activity

import com.telenav.kivakit.annotations.code.quality.TypeQuality

/**
 * The current step succeeded and does not indicate any problem
 *
 * @author Jonathan Locke
 */
@UmlIncludeType(diagram = DiagramMessageType::class)
@TypeQuality(stability = STABLE, testing = TESTING_NOT_NEEDED, documentation = DOCUMENTED)
class StepSuccess : Information
{
    constructor(message: String?, vararg arguments: Any?) : super(message, arguments)
    constructor()

    fun severity(): Severity
    {
        return NONE
    }

    fun status(): Status
    {
        return SUCCEEDED
    }
}