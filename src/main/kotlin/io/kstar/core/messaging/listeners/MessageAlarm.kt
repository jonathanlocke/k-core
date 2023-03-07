package io.kstar.core.messaging.listeners

import com.telenav.kivakit.annotations.code.quality.TypeQuality

/**
 * Base class for implementing message alarms that trigger when alarming messages are received at a high rate.
 *
 *
 * **Installing a Message Alarm**
 *
 *
 *
 * A [MessageAlarm] is a [Listener] which can listen to messages from any [Broadcaster] or
 * [Repeater] when installed with [MessageAlarm.listenTo]. For example:
 *
 *
 * <pre>
 * public void onRun()
 * {
 * // Send alarm email if this application exceeds 10 errors per minute
 * new EmailAlarm(...).listenTo(this);
 * }</pre>
 *
 *
 *
 * The installed alarm will be triggered when the error [.rate] exceeds [.triggerRate], which defaults
 * to 10 errors per minute. This default value can be overridden with [.triggerRate]. The maximum alarm
 * trigger frequency defaults to once every 30 minutes. This value can be overridden with
 * [.maximumTriggerFrequency].
 *
 *
 *
 * **Implementing an Alarm**
 *
 *
 *
 * To implement an alarm, override [.onTrigger] and implement the alarm action. By default, the alarm will
 * be triggered when [.shouldTrigger] returns true. By default, [.shouldTrigger] returns true if the
 * current error [.rate] exceeds [.triggerRate]. The current error [.rate] is computed with a
 * [RateCalculator] that automatically resets once a minute. MessageTransceiver are categorized as errors (or not)
 * by [.isAlarming], which returns true if the message status is worse-than-or-equal-to [Problem]
 * by default.
 *
 *
 * @author Jonathan Locke
 */
@Suppress("unused")
@TypeQuality(stability = STABLE_EXTENSIBLE, testing = UNTESTED, documentation = DOCUMENTED)
abstract class MessageAlarm : Listener
{
    /** Computes the rate of errors received  */
    private val errorRate: RateCalculator = RateCalculator(ONE_MINUTE)

    /** The maximum frequency at which this alarm can be triggered  */
    private var maximumTriggerFrequency: Frequency = every(minutes(30))

    /** The time at which this alarm can next be triggered  */
    private var nextAllowedTriggerTime: Time? = null

    /** Tracks frequency cycles  */
    private var triggerCycle: Frequency.Cycle? = null

    /** The maximum rate above which an alarm is triggered  */
    private var triggerRate: Rate = perMinute(10)

    /**
     * Sets the maximum frequency at which this alarm can be triggered
     */
    fun maximumTriggerFrequency(maximumTriggerFrequency: Frequency): MessageAlarm
    {
        this.maximumTriggerFrequency = maximumTriggerFrequency
        return this
    }

    /**
     * {@inheritDoc}
     */
    @Synchronized
    fun onMessage(message: Message)
    {
        // If the message is an error,
        if (isAlarming(message))
        {
            // increment the number of errors,
            errorRate.increment()

            // and if we should trigger the alarm, and it is allowed,
            if (isTriggerAllowedNow && shouldTrigger())
            {
                // then do that,
                trigger(rate())

                // and reset the error rate calculator.
                errorRate.reset()
            }
        }
    }

    /**
     * Called to determine if this alarm should be triggered
     *
     * @return True if this alarm should be triggered
     */
    fun shouldTrigger(): Boolean
    {
        return rate().isFasterThan(triggerRate)
    }

    /**
     * Sets the trigger for this alarm to the given rate
     */
    fun trigger(rate: Rate?)
    {
        onTrigger(rate)
    }

    /**
     * Sets the error rate above which this alarm will be triggered
     *
     * @param triggerRate The rate of error messages at which this alarm will be triggered
     */
    fun triggerRate(triggerRate: Rate): MessageAlarm
    {
        this.triggerRate = triggerRate
        return this
    }

    /**
     * Returns the rate above which this alarm will be triggered
     */
    fun triggerRate(): Rate
    {
        return triggerRate
    }

    /**
     * Returns true if the given message should count towards setting off this alarm
     *
     * @param message The message
     * @return True if the message is a problem that should be counted
     */
    protected fun isAlarming(message: Message): Boolean
    {
        return message.isWorseThanOrEqualTo(Problem::class.java)
    }

    /**
     * Alarm action implementation
     *
     * @param rate The error rate that triggered this alarm
     */
    protected abstract fun onTrigger(rate: Rate?)

    /**
     * Returns the current rate of error messages
     */
    protected fun rate(): Rate
    {
        return errorRate.rate()
    }

    private val isTriggerAllowedNow: Boolean
        /**
         * Returns true if it's allowed to trigger the alarm at this time
         */
        private get() =// If we haven't triggered yet,
            if (nextAllowedTriggerTime == null)
            {
                // get the next trigger time
                nextAllowedTriggerTime = nextAllowedTriggerTime()

                // and allow this first trigger.
                true
            }
            else
            {
                // If we have passed the next allowed trigger time,
                val allowed: `var` = Time.now().isGreaterThan(nextAllowedTriggerTime)
                if (allowed)
                {
                    // set a new next allowed trigger time
                    nextAllowedTriggerTime = nextAllowedTriggerTime()
                }

                // and return whether triggering is allowed.
                allowed
            }

    /**
     * Returns the time at which this alarm can next be triggered
     */
    private fun nextAllowedTriggerTime(): Time
    {
        // If we haven't initialized,
        if (triggerCycle == null)
        {
            // create a frequency cycle object.
            triggerCycle = maximumTriggerFrequency.startingNow()
        }

        // Return the next allowed trigger time
        return triggerCycle.next()
    }
}