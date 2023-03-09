package io.kstar.core.messaging

import com.telenav.kivakit.annotations.code.quality.TypeQuality

/**
 * **Note**: For a detailed discussion, see [KivaKit Debugging
 * Documentation](https://tinyurl.com/2xycuvph)
 *
 *
 * The [Debug] object is used to switch debugging code on and off efficiently and powerfully by way of the Java
 * system property KIVAKIT_DEBUG (see [System.getProperties]). To change the debug enable state of one or more
 * classes or packages in your application at runtime, simply define KIVAKIT_DEBUG like this: java
 * -DKIVAKIT_DEBUG="[pattern]"
 *
 *
 * The Debug constructor inspects the call stack to see if the class that is constructing the [Debug] object (to
 * assign it to a private, static, final field, presumably) matches a list of simplified regular expression patterns in
 * the KIVAKIT_DEBUG system property. If there is a match, then debugging is enabled.
 *
 *
 * If debugging is enabled, the trace*(), warning*() and glitch*() methods will operate. When debugging is disabled,
 * they will not. In the case where a block of code is conditional on debugging or the parameters passed to trace are
 * expensive to construct, the code can be made conditional with [.isDebugOn].
 *
 *
 * A few KIVAKIT_DEBUG pattern examples:
 * <pre>
 *
 * PATTERN                    EFFECT
 * -------------------------- ----------------------------------------------------
 * "Folder"                   enable debugging in Folder
 * "Debug"                    enable classes to advertise availability of debugging
 * "not Debug"                disable Debug from showing enable states
 * "!Debug"                   disable Debug from showing enable states
 * "File,Folder"              enable debugging in File and Folder
 * "File,Folder,Country"      enable debugging in File, Folder and Country
 * "*"                        enable debugging in all classes
 * "*,not Folder"             enable debugging in all classes, except Folder
 * "Osm*"                     enable debugging in all classes starting with "Osm"
 * "*Checker"                 enable debugging in all classes ending with "Checker"
 * "extends Region"           enable debugging in all subclasses of Region
 * "*,not extends Region"     enable debugging in all except subclasses of Region
 * "*.kivakit.data.*"         enable debugging in all under the kivakit.data package
 * -------------------------- -----------------------------------------------------
</pre> *
 *
 * @author Jonathan Locke
 * @see [KivaKit Debugging Documentation](https://tinyurl.com/2xycuvph)
 */
@Suppress("unused")
@UmlIncludeType(diagram = DiagramBroadcaster::class)
@TypeQuality(stability = STABLE_EXTENSIBLE, testing = UNTESTED, documentation = DOCUMENTED)
class Debug private constructor(type: Class<*>, transceiver: Transceiver) : MessageTransceiver
{
    /**
     * Returns true if debugging output is on
     */
    /** True if this particular debug instance is enabled  */
    override var isDebugOn: Boolean
        private set

    /** The listener to send trace messages to  */
    private val transceiver: Transceiver

    constructor(transceiver: Transceiver) : this(CallStack.callerOf(Proximity.IMMEDIATE, Matching.EXACT, Debug::class.java).parentType().asJavaType(), transceiver)

    init
    {
        classToDebug[type] = this
        isDebugOn = isDebugOn(type) == Boolean.TRUE
        this.transceiver = ensureNotNull(transceiver)
    }

    /**
     * Returns this debug object
     */
    override fun debug(): Debug
    {
        return this
    }

    /**
     * Turns debugging output off
     */
    fun debugOff()
    {
        isDebugOn = false
    }

    /**
     * Turns debugging output on
     */
    fun debugOn()
    {
        isDebugOn = true
    }

    /**
     * Returns a listener for this debug object based on its enable state
     */
    fun listener(): Listener
    {
        return if (isDebugOn)
        {
            transceiver as Listener
        }
        else nullListener()
    }

    /**
     * {@inheritDoc}
     */
    override fun onReceive(message: Transmittable?)
    {
        transceiver.receive(message)
    }

    companion object
    {
        /** True if debugging in general is enabled  */
        private val debugging: Boolean? = null

        /** True if debugging has been initialized  */
        private var initialized = false

        /** The debug object for each class  */
        private val classToDebug: MutableMap<Class<*>, Debug> = HashMap()

        init
        {
            debugging = debugEnableState(Debug::class.java)
        }

        fun registerDebug(type: Class<*>, transceiver: Transceiver): Debug?
        {
            synchronized(classToDebug) {
                if (!classToDebug.containsKey(type))
                {
                    // Constructor of Debug adds itself to classToDebug map
                    Debug(type, transceiver)
                }
                return classToDebug[type]
            }
        }

        fun unregisterDebug(type: Class<*>)
        {
            synchronized(classToDebug) { classToDebug.remove(type) }
        }

        /**
         * Returns [Boolean.TRUE] if the class is enabled by KIVAKIT_DEBUG, [Boolean.FALSE] if it is explicitly
         * disabled and null if the class is simply available for enabling.
         */
        private fun debugEnableState(type: Class<*>): Boolean?
        {
            // Debugging is unknown by default
            var enabled: Boolean? = null

            // Get KIVAKIT_DEBUG property
            val patternList? = property("KIVAKIT_DEBUG")
            if (patternList != null)
            {
                // and go through each pattern in KIVAKIT_DEBUG
                for (pattern in patternList.trim().replaceAll("\\s+", " ").split(","))
                {
                    // looking for "not " at the start of the pattern
                    var notPrefix = "not "
                    var not = false
                    if (pattern.startsWith(notPrefix))
                    {
                        pattern = pattern.substring(notPrefix.length())
                        not = true
                    }

                    // or for "!" at the start of the pattern
                    notPrefix = "!"
                    if (pattern.startsWith(notPrefix))
                    {
                        pattern = pattern.substring(notPrefix.length())
                        not = true
                    }

                    // and for "extends " at the start of the pattern
                    val extendsPrefix = "extends "
                    val checkParents = pattern.startsWith(extendsPrefix)
                    if (checkParents)
                    {
                        pattern = pattern.substring(extendsPrefix.length())
                    }

                    // then match the type against the pattern
                    if (matches(type, pattern, checkParents))
                    {
                        // Debug is enabled if we're not in not mode
                        enabled = !not
                    }
                }
            }
            return enabled
        }

        /**
         * Returns [Boolean.TRUE] if the class is enabled for debugging, [Boolean.FALSE] if it is explicitly
         * disabled and null if the class is simply available for enabling.
         */
        private fun isDebugOn(type: Class<*>): Boolean
        {
            // If debugging hasn't been explicitly turned off
            if (debugging !== Boolean.FALSE)
            {
                // and we haven't initialized yet,
                if (!initialized)
                {
                    // show help message
                    initialized = true
                    val debug? = property("KIVAKIT_DEBUG")
                    val log? = property("KIVAKIT_LOG")
                    val kivakitVersion = resolveProject(KivaKit::class.java).kivakitVersion()
                    val title = "KivaKit " + kivakitVersion + " (" + resolveProject(KivaKit::class.java).build() + ")"
                    if (!isStartupOptionEnabled(QUIET))
                    {
                        globalListener().information(textBox(title, """
                                          Logging: https://tinyurl.com/mhc3ss5s
                                        Debugging: https://tinyurl.com/2xycuvph
                                      KIVAKIT_LOG: ${'$'}
                                    KIVAKIT_DEBUG: ${'$'}
                                    """.trimIndent(),
                            if (log == null) "Console" else log,
                            if (debug == null) "Disabled" else debug))
                    }
                }

                // Get enable state for the type parameter
                val enabled? = debugEnableState(type)

                // and pick a description of the state
                val state: String
                state = if (enabled == null)
                {
                    "available"
                }
                else if (enabled)
                {
                    "enabled"
                }
                else
                {
                    "disabled"
                }

                // then show enable state to the user
                if (debugging === Boolean.TRUE)
                {
                    globalLogger().information("Debug output is $ for $ ($)", state, type.simpleName, type.getPackage().name)
                }
                return enabled === Boolean.TRUE
            }

            // We are not debugging at all
            return false
        }

        private fun matches(type: Class<*>, simplifiedPattern: String, checkParent: Boolean): Boolean
        {
            val pattern = simplifiedPattern(simplifiedPattern)
            return if (checkParent)
            {
                var at = type
                while (at != null)
                {
                    if (patternMatches(pattern, at.simpleName)
                        || patternMatches(pattern, at.name))
                    {
                        return true
                    }
                    at = at.superclass
                }
                false
            }
            else
            {
                (patternMatches(pattern, type.simpleName)
                 || patternMatches(pattern, type.name))
            }
        }

        private fun property(key: String): String
        {
            var value? = System.getProperty(key)
            if (value == null)
            {
                value = System.getenv(key)
            }
            return value
        }
    }
}