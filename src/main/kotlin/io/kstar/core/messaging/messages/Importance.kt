package io.kstar.core.messaging.messages

import com.telenav.kivakit.annotations.code.quality.TypeQuality

/**
 * The (relative) importance of [Message]s as a level from zero to one.
 *
 * @author Jonathan Locke
 */
@Suppress("unused")
@UmlIncludeType(diagram = DiagramMessaging::class)
@TypeQuality(stability = STABLE, testing = TESTING_NOT_NEEDED, documentation = DOCUMENTED)
class Importance private constructor(level: Double) : Level(level)
{
    companion object
    {
        /** Importance level for each message type  */
        private val levels: MutableMap<Class<out Message?>, Importance> = HashMap<Class<out Message?>, Importance>()

        init
        {
            // Built-in messages in order of increasing importance. Additional messages can register
            // their importance as midway between two registered messages with register()
            register(
                Trace::class.java,
                Step::class.java,
                Information::class.java,
                StepSuccess::class.java,
                Narration::class.java,
                Announcement::class.java,
                Glitch::class.java,
                Warning::class.java,
                StepIncomplete::class.java,
                Problem::class.java,
                StepFailure::class.java,
                Alert::class.java,
                CriticalAlert::class.java
            )
        }

        /**
         * Returns an [Importance] for the given level
         */
        fun importance(level: Double): Importance
        {
            return Importance(level)
        }

        /**
         * Returns an [Importance] for the given message type
         */
        fun importanceOfMessage(type: Class<out Message?>): Importance?
        {
            return levels[type]
        }

        /**
         * An importance level for a user-defined message class that is midway between the two given message classes.
         *
         * @param low The lower importance message class
         * @param high The higher importance message class
         * @return The importance between the two message classes
         */
        fun registerImportance(low: Class<out Message?>, high: Class<out Message?>): Importance
        {
            val lowValue: `var` = levels[low].asZeroToOne()
            val highValue: `var` = levels[high].asZeroToOne()
            val difference: `var` = highValue - lowValue
            return importance(lowValue + difference / 2.0)
        }

        @SafeVarargs
        private fun register(vararg messages: Class<out Message?>)
        {
            var level = 0.0
            val increment = 1.0 / (messages.size - 1)
            for (message in messages)
            {
                levels[message] = importance(level)
                level += increment
                level = Math.min(1.0, level)
            }
        }
    }
}