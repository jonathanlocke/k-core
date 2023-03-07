package io.kstar.core.os


import io.kstar.annotations.quality.Documentation.DOCUMENTED
import io.kstar.annotations.quality.Stability.*
import io.kstar.annotations.quality.Testing.*
import io.kstar.annotations.quality.TypeQuality
import io.kstar.receptors.values.Named
import java.io.File
import java.io.IOException
import java.util.*


/**
 * An abstraction of features of the underlying OS through Java interfaces. This object is a singleton, which can be
 * retrieved with [.operatingSystem].
 *
 *
 * **Identity**
 *
 *
 *  * [.operatingSystemType]
 *  * [.processorArchitecture]
 *  * [.isMac]
 *  * [.isWindows]
 *  * [.isWindows]
 *  * [.name]
 *
 *
 *
 * **Execution**
 *
 *
 *  * [.execute]
 *  * [.javaExecutable]
 *  * [.processIdentifier]
 *
 *
 *
 * **Variables**
 *
 *
 *  * [.environmentVariables]
 *  * [.systemPropertyOrEnvironmentVariable]
 *  * [.javaHome]
 *
 *
 * @author Jonathan Locke
 */
@TypeQuality
(
    stability = STABLE_EXTENSIBLE,
    testing = UNTESTED,
    documentation = DOCUMENTED
)
class OperatingSystem : Named
{

    /** Map of environment variables  */
    private var environmentVariables: VariableMap<String>? = null

    /**
     * Returns all OS environment variables
     */
    fun environmentVariables(): VariableMap<String>?
    {
        if (environmentVariables == null)
        {
            environmentVariables = VariableMap()
            for (key: String? in System.getenv().keys)
            {
                environmentVariables.put(key, System.getenv(key))
            }
        }
        return environmentVariables
    }

    /**
     * Executes the given command in the given folder
     *
     * @param listener The listener to call with any problems
     * @param folder The folder to run the command in
     * @param command The command to run
     * @return The output of the command
     */
    fun execute(listener: Listener?, folder: File?, vararg command: String?): String
    {
        try
        {
            val builder: ProcessBuilder = ProcessBuilder()
            builder.command(*command)
            builder.directory(folder)
            builder.redirectErrorStream(true)
            val process: Process = builder.start()
            val output: Unit = captureOutput(listener, process)
            waitForTermination(process)
            return output
        }
        catch (e: IOException)
        {
            throw IllegalStateException("OperationFailed reading output of child process", e)
        }
    }

    val isMac: Boolean
        /**
         * Returns true if this is a Mac
         */
        get()
        = name().lowercase(Locale.getDefault()).contains("mac")
    val isUnix: Boolean
        /**
         * Returns true if this is UNIX (but not macOS)
         */
        get()
        = (name().lowercase(Locale.getDefault()).contains("unix")
           || name().lowercase(Locale.getDefault()).contains("linux"))
    val isWindows: Boolean
        /**
         * Returns true if this Windows
         */
        get()
        {
            return name().lowercase(Locale.getDefault()).contains("win")
        }

    /**
     * Returns path to java executable
     */
    fun javaExecutable(): String
    {
        return ensureNotNull(javaHome(), "JAVA_HOME must be set") + "/bin/java"
    }

    /**
     * Returns KIVAKIT_JAVA_HOME, or if that's not defined, returns JAVA_HOME
     */
    fun javaHome(): String?
    {
        var home: String? = systemPropertyOrEnvironmentVariable("KIVAKIT_JAVA_HOME")
        if (home == null)
        {
            home = systemPropertyOrEnvironmentVariable("JAVA_HOME")
        }
        return home
    }

    /**
     * {@inheritDoc}
     */
    fun name(): String
    {
        return System.getProperty("os.name")
    }

    /**
     * Returns the type of operating system
     */
    fun operatingSystemType(): com.telenav.kivakit.core.os.OperatingSystem.OperatingSystemType
    {
        if (name().contains("Mac"))
        {
            return MACOS
        }
        if (name().contains("Windows"))
        {
            return WINDOWS
        }
        if ((name().contains("Linux")
             || name().contains("SunOS")
             || name().contains("BSD")))
        {
            return UNIX
        }
        return OTHER_OS
    }

    /**
     * Returns the PID for this process
     */
    fun processIdentifier(): Int
    {
        return ProcessHandle.current().pid().toInt()
    }

    /**
     * Returns the processor architecture
     */
    @Suppress("SpellCheckingInspection")
    fun processorArchitecture(): com.telenav.kivakit.core.os.OperatingSystem.ProcessorArchitecture
    {
        return when (System.getProperty("os.arch"))
        {
            "x86" -> INTEL
            "aarch64" -> APPLE
            else -> OTHER_PROCESSOR
        }
    }

    /**
     * Returns the value of the given property, or if that doesn't exist the value of the given environment variable. If
     * that also does not exist, returns the given default value.
     *
     * @param variable The name of the property or environment variable to get.
     * @param defaultValue A default value to use if there is no system property or environment variable defined for the
     * given key
     */
    fun systemPropertyOrEnvironmentVariable(variable: String?, defaultValue: String): String
    {
        val value: String? = systemPropertyOrEnvironmentVariable(variable)
        return if (value == null) defaultValue else value
    }

    /**
     * Returns the value of the given property, or if that doesn't exist the value of the given environment variable
     *
     * @param variable The name of the property or environment variable to get.
     */
    fun systemPropertyOrEnvironmentVariable(variable: String?): String?
    {
        // First check for a system property
        var value: String? = System.getProperty(variable)

        // then if that's not available,
        if (value == null)
        {
            // check for an environment variable
            value = System.getenv(variable)
        }
        return value
    }

    companion object
    {
        /** This operating system  */
        private val os: com.telenav.kivakit.core.os.OperatingSystem = com.telenav.kivakit.core.os.OperatingSystem()

        /**
         * Returns the operating system
         */
        fun operatingSystem(): com.telenav.kivakit.core.os.OperatingSystem
        {
            return com.telenav.kivakit.core.os.OperatingSystem.Companion.os
        }
    }
}