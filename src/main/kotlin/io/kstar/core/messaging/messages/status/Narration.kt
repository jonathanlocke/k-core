package io.kstar.core.messaging.messages.status

import com.telenav.kivakit.annotations.code.quality.TypeQuality

/**
 * A step in a narrative describing a series of successful steps performed by an operation.
 *
 *
 *
 * [OperationStatusMessage]s in order of importance:
 *
 *
 *  * Critical Alert - An operation failed and needs *immediate attention* from a human operator
 *  * Alert - An operation failed and needs to be looked at by an operator soon
 *  * FatalProblem - An unrecoverable problem has caused an operation to fail and needs to be addressed
 *  * Problem - Something has gone wrong and needs to be addressed, but it's not fatal to the current operation
 *  * Glitch - A minor problem has occurred. Unlike a Warning, a Glitch indicates validation failure or minor data loss has occurred. Unlike a Problem, a Glitch indicates that the operation will definitely recover and continue.
 *  * Warning - A minor issue occurred which should be corrected, but does not necessarily require attention
 *  * Quibble - A trivial issue that does not require correction
 *  * Announcement - Announcement of an important phase of an operation
 *  * **Narration** - A step in some operation has started or completed
 *  * Information - Commonly useful information that doesn't represent any problem
 *  * Trace - Diagnostic information for use when debugging
 *
 *
 * @author Jonathan Locke
 */
@UmlIncludeType(diagram = DiagramMessageType::class)
@TypeQuality(stability = STABLE, testing = TESTING_NOT_NEEDED, documentation = DOCUMENTED)
class Narration : Information
{
    constructor(message: String?, vararg arguments: Any?) : super(message, *arguments)
    constructor()

    /**
     * {@inheritDoc}
     */
    override fun severity(): Severity
    {
        return NONE
    }

    /**
     * {@inheritDoc}
     */
    override fun status(): Status
    {
        return SUCCEEDED
    }
}