package io.kstar.core.messaging.context

import com.telenav.kivakit.annotations.code.quality.TypeQuality
import java.lang.reflect.Method

/**
 * Information about a location in code, including the host and class. Line numbers are not available.
 *
 * @author Jonathan Locke
 */
@UmlIncludeType(diagram = DiagramThread::class)
@TypeQuality(stability = STABLE_EXTENSIBLE, testing = UNTESTED, documentation = DOCUMENTED)
class CodeContext
{
    /** The class  */
    @Transient
    private var type: Class<*>? = null

    /** The full name of the class  */
    private var fullTypeName: String? = null

    /** The short name of the class  */
    private var typeName: String? = null

    /** The host  */
    private val host: String = hostResolver.get()

    constructor(type: Class<*>)
    {
        this.type = type
        fullTypeName = type.name
        typeName = simpleName(type)
    }

    constructor(callerOf: Method?)
    {
        if (callerOf != null)
        {
            type = callerOf.declaringClass
            fullTypeName = type.getName()
            typeName = simpleName(type)
        }
    }

    constructor(locationName: String?)
    {
        type = null
        fullTypeName = locationName
        typeName = locationName
    }

    protected constructor()

    /**
     * Returns the full name of the code context's type
     */
    fun fullTypeName(): String?
    {
        return fullTypeName
    }

    /**
     * Returns code context's host
     */
    fun host(): String
    {
        return host
    }

    /**
     * Returns the package for the code context's type
     */
    fun packagePath(): PackageReference
    {
        return packageReference(type)
    }

    /**
     * {@inheritDoc}
     */
    override fun toString(): String
    {
        return typeName()!!
    }

    /**
     * Returns the code context class
     */
    fun type(): Class<*>?
    {
        if (type == null)
        {
            type = classForName(fullTypeName)
        }
        return type
    }

    /**
     * Returns the simple type name for this code context
     */
    fun typeName(): String?
    {
        return typeName
    }

    companion object
    {
        private var hostResolver: Source<String> = Source<String> { "localhost" }

        /**
         * Sets the resolver to use to find hosts
         *
         * @param resolver The host resolver
         */
        fun hostResolver(resolver: Source<String?>)
        {
            hostResolver = resolver
        }
    }
}