package io.kstar.core.collections.set

import com.telenav.kivakit.annotations.code.quality.TypeQuality

/**
 * A set of objects with an arbitrary backing set.
 *
 *
 * **Creation**
 *
 *
 *  * [.set]
 *  * [.set]
 *  * [.emptySet]
 *  * [ObjectSet.ObjectSet]
 *  * [ObjectSet.ObjectSet]
 *  * [ObjectSet.ObjectSet]
 *
 *
 * **Adding**
 *
 *
 *  * [.add]
 *  * [.addIfNotNull]
 *  * [.addAll]
 *  * [.addAll]
 *  * [.addAll]
 *  * [.addAll]
 *  * [.addAllMatching]
 *  * [.addAllMatching]
 *  * [.addAllMatching]
 *  * [.addAllMatching]
 *
 *
 *
 * **Conversion**
 *
 *
 *  * [.asIterable]
 *  * [.asIterator]
 *  * [.asList]
 *  * [.asSet]
 *  * [.asString]
 *
 *
 *
 * **Access**
 *
 *
 *  * [.copy]
 *  * [.first]
 *  * [.matching]
 *  * [.map]
 *
 *
 *
 * **Membership**
 *
 *
 *  * [.contains]
 *  * [.containsAll]
 *
 *
 *
 * **Size**
 *
 *
 *  * [.size]
 *  * [.count]
 *  * [.isEmpty]
 *  * [.isNonEmpty]
 *
 *
 *
 * **Bounds**
 *
 *
 *  * [.maximumSize] - The maximum size of this list
 *  * [.totalRoom] - The maximum size of this list
 *  * [.hasRoomFor] - For use by subclasses to check their size
 *  * [.onOutOfRoom] - Responds with a warning when the list is out of space
 *
 *
 *
 * **Removing**
 *
 *
 *  * [.clear]
 *  * [.remove]
 *  * [.removeAll]
 *  * [.removeIf]
 *  * [.removeAllMatching]
 *
 *
 *
 * **Conversions**
 *
 *
 *  * [.asArray] - This list as an array of the given type
 *  * [.asIterable]
 *  * [.asIterable]
 *  * [.asIterator]
 *  * [.asIterator]
 *  * [.asSet]
 *  * [.asString]
 *  * [.asStringList]
 *
 *
 *
 * **String Conversions**
 *
 *
 *  * [.join] - This list joined by the list [.separator]
 *  * [.separator] - The separator used when joining this list into a string
 *
 *
 *
 * **Functional Methods**
 *
 *
 *  * [.copy] - A copy of this list
 *  * [.map]
 *  * [.matching] - A copy of this list filtered to matching elements
 *  * [.with]
 *  * [.with]
 *  * [.without] - This list without the matching elements
 *
 *
 * @author jonathanl (shibo)
 * @see BaseSet
 */
@TypeQuality(stability = STABLE_EXTENSIBLE, testing = TESTING_INSUFFICIENT, documentation = DOCUMENTED)
class ObjectSet<Value> : BaseSet<Value?>
{
    /**
     * Creates an object set
     *
     * @param maximumSize The maximum size of the set
     */
    constructor(maximumSize: Maximum) : super(maximumSize)

    /**
     * Creates an object set
     *
     * @param maximumSize The maximum size of the set
     * @param values The initial values to add to the set
     */
    constructor(maximumSize: Maximum, values: Collection<Value>) : super(maximumSize, values)

    /**
     * Creates an object set
     *
     * @param values The initial values to add to the set
     */
    constructor(values: Collection<Value>) : super(MAXIMUM, values)

    /**
     * Creates an empty object set with no maximum size
     */
    constructor() : this(MAXIMUM)

    /**
     * {@inheritDoc}
     */
    fun asList(): ObjectList<Value>
    {
        return list(super.asList())
    }

    /**
     * {@inheritDoc}
     */
    override fun copy(): ObjectSet<Value?>
    {
        return super.copy() as ObjectSet<Value?>
    }

    /**
     * {@inheritDoc}
     */
    override fun matching(matcher: Matcher<Value?>): ObjectSet<Value?>
    {
        return super.matching(matcher) as ObjectSet<Value?>
    }

    /**
     * {@inheritDoc}
     */
    override fun with(value: Value?): ObjectSet<Value?>
    {
        return super.with(value) as ObjectSet<Value?>
    }

    /**
     * {@inheritDoc}
     */
    @SafeVarargs
    fun with(vararg value: Value): ObjectSet<Value?>
    {
        return super.with(value) as ObjectSet<Value?>
    }

    /**
     * {@inheritDoc}
     */
    fun with(that: Collection<Value>?): ObjectSet<Value?>
    {
        return super.with(that) as ObjectSet<Value?>
    }

    /**
     * {@inheritDoc}
     */
    fun with(that: Iterable<Value>?): ObjectSet<Value?>
    {
        return super.with(that) as ObjectSet<Value?>
    }

    fun without(that: Array<Value>?): ObjectSet<Value>
    {
        return super.without(that) as ObjectSet<Value>
    }

    fun without(matcher: Matcher<Value>?): BaseSet<Value>
    {
        return super.without(matcher)
    }

    fun without(value: Value): ObjectSet<Value>
    {
        return super.without(value) as ObjectSet<Value>
    }

    fun without(that: Collection<Value>?): ObjectSet<Value>
    {
        return super.without(that) as ObjectSet<Value>
    }

    /**
     * {@inheritDoc}
     */
    override fun onNewBackingSet(): MutableSet<Value?>
    {
        return HashSet()
    }

    /**
     * {@inheritDoc}
     */
    protected fun onNewCollection(): ObjectSet<Value>
    {
        return set()
    }

    companion object
    {
        /**
         * Returns an empty [ObjectSet].
         */
        fun <T> emptySet(): ObjectSet<T>
        {
            return ObjectSet()
        }

        /**
         * Returns an [ObjectSet] with the given values in it
         *
         * @param values The values to add to the set
         */
        @SafeVarargs
        fun <T> set(vararg values: T): ObjectSet<T>
        {
            val set = ObjectSet<T>()
            set.addAll(values)
            return set
        }

        /**
         * Returns an [ObjectSet] with the given values in it
         *
         * @param values The values to add to the set
         */
        fun <T> set(values: Collection<T>?): ObjectSet<T>
        {
            val set = ObjectSet<T>()
            set.addAll(values)
            return set
        }
    }
}