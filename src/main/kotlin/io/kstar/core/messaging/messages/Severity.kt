package io.kstar.core.messaging.messages

import io.kstar.annotations.documentation.UmlIncludeType
import io.kstar.annotations.quality.Documentation.*
import io.kstar.annotations.quality.Stability.*
import io.kstar.annotations.quality.Testing.*
import io.kstar.annotations.quality.TypeQuality
import io.kstar.core.messaging.Listener
import io.kstar.core.values.level.Level
import io.kstar.internal.Diagrams
import io.kstar.receptors.values.Named


/**
 * Represents the severity of the current state of an operation or a step in an operation.
 *
 * @author Jonathan Locke
 */
@Suppress("unused")
@UmlIncludeType(inDiagrams = [Diagrams.DiagramMessaging::class])
@TypeQuality
(
    stability = STABLE_EXTENSIBLE,
    testing = TESTING_NOT_NEEDED,
    documentation = DOCUMENTED
)
class Severity : Level, Named
{
    private var name: String

    constructor(name: String?, severity: Double) : super(severity)
    {
        assert(name != null)
        this.name = name
        severities[name] = this
    }

    private constructor() : super(0.0)

    /**
     * {@inheritDoc}
     */
    fun name(): String
    {
        return name
    }

    /**
     * {@inheritDoc}
     */
    override fun toString(): String
    {
        return name
    }

    companion object
    {
        private val severities: MutableMap<String?, Severity> = HashMap()
        val NONE = Severity("None", 0.0)
        val LOW = Severity("Low", 0.1)
        val MEDIUM = Severity("Medium", 0.5)
        val HIGH = Severity("High", 0.7)
        val CRITICAL = Severity("Critical", 0.9)
        fun parseSeverity(listener: Listener, name: String?): Severity
        {
            return listener.problemIfNull(severities[name], "Invalid severity: $", name)
        }
    }
}