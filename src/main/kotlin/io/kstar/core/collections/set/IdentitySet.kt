package io.kstar.core.collections.set

import com.telenav.kivakit.annotations.code.quality.TypeQuality
import java.util.*

/**
 * A convenient implementation of [Set] using [ConcurrentHashMap].
 *
 * @author jonathanl (shibo)
 */
@UmlClassDiagram(diagram = DiagramCollections::class)
@TypeQuality(stability = STABLE_EXTENSIBLE, testing = TESTING_INSUFFICIENT, documentation = DOCUMENTED)
class IdentitySet<Value>(maximumSize: Maximum, values: Collection<Value>) : BaseSet<Value?>(maximumSize, values)
{
    private val map: IdentityHashMap<Value, Value> = IdentityHashMap<Value, Value>()

    @JvmOverloads
    constructor(maximumSize: Maximum = MAXIMUM) : this(maximumSize, HashSet<Value>())

    /**
     * Gets the value currently in the set is equal to the given prototype object
     *
     * @param prototype The object to match against
     * @return Any object in the current set that matches the given object
     */
    operator fun get(prototype: Value): Value
    {
        return map.get(prototype)
    }

    /**
     * Takes any object matching the given prototype out of the set, returning the set's value (but not necessarily the
     * prototype).
     *
     * @param prototype The object to match against
     * @return Any matching object after removing it from the set
     */
    fun take(prototype: Value): Value
    {
        return map.remove(prototype)
    }

    fun with(that: Iterable<Value>?): IdentitySet<Value?>
    {
        return super.with(that) as IdentitySet<Value?>
    }

    override fun with(value: Value?): IdentitySet<Value?>
    {
        return super.with(value) as IdentitySet<Value?>
    }

    fun with(that: Array<Value>?): IdentitySet<Value?>
    {
        return super.with(that) as IdentitySet<Value?>
    }

    fun with(that: Collection<Value>?): IdentitySet<Value?>
    {
        return super.with(that) as IdentitySet<Value?>
    }

    fun without(value: Value): IdentitySet<Value>
    {
        return super.without(value) as IdentitySet<Value>
    }

    fun without(that: Array<Value>?): IdentitySet<Value>
    {
        return super.without(that) as IdentitySet<Value>
    }

    fun without(that: Collection<Value>?): IdentitySet<Value>
    {
        return super.without(that) as IdentitySet<Value>
    }

    fun without(matcher: Matcher<Value>?): BaseSet<Value>
    {
        return super.without(matcher)
    }

    /**
     * {@inheritDoc}
     */
    override fun onNewBackingSet(): MutableSet<Value?>
    {
        return object : AbstractSet<Any?>()
        {
            fun add(value: Value): Boolean
            {
                map.put(value, value)
                return true
            }

            override fun clear()
            {
                map.clear()
            }

            override operator fun contains(value: Any): Boolean
            {
                return map.containsKey(value)
            }

            override fun iterator(): MutableIterator<Value>
            {
                return map.keys.iterator()
            }

            override fun remove(value: Any): Boolean
            {
                map.remove(value)
                return true
            }

            override fun size(): Int
            {
                return map.size
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    protected fun onNewCollection(): IdentitySet<Value>
    {
        return IdentitySet()
    }
}