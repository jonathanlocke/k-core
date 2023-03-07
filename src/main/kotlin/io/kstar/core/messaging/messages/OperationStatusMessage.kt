package io.kstar.core.messaging.messages

import com.telenav.kivakit.annotations.code.quality.TypeQuality

/**
 * Signals the status of an operation.
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
 *  * Narration - A step in some operation has started or completed
 *  * Information - Commonly useful information that doesn't represent any problem
 *  * Trace - Diagnostic information for use when debugging
 *
 *
 * @author Jonathan Locke
 */
@UmlIncludeType(diagram = DiagramMessageType::class)
@TypeQuality(stability = STABLE, testing = TESTING_NOT_NEEDED, documentation = DOCUMENTED)
abstract class OperationStatusMessage : OperationMessage
{
    enum class CauseFormatting
    {
        DO_NOT_FORMAT_CAUSE, FORMAT_CAUSE
    }

    /** A hierarchical error code per IETF RFC 7807.  */
    var code: String? = null

    protected constructor()
    protected constructor(causeFormatting: CauseFormatting, cause: Throwable?, message: String,
                          vararg arguments: Any?) : super(message + if (causeFormatting == CauseFormatting.FORMAT_CAUSE) """
     
     
     Cause:
     
     ${causeToString(cause)}
     """.trimIndent()
    else "")
    {
        cause?.let { cause(it) }
        arguments(arguments)
    }

    /**
     * Returns a hierarchical error code per IETF RFC 7807. For example, "errors/authentication/incorrect-password".
     */
    fun code(): String?
    {
        return code
    }

    /**
     * @param code A hierarchical error code per IETF RFC 7807. For example,
     * "errors/authentication/incorrect-password".
     */
    open fun code(code: String?): OperationStatusMessage?
    {
        this.code = code
        return this
    }

    /**
     * {@inheritDoc}
     */
    fun operationStatus(): OperationStatus
    {
        return NOT_APPLICABLE
    }
}