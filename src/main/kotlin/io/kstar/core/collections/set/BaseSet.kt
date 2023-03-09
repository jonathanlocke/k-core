package io.kstar.core.collections.set

import io.kstar.annotations.quality.Documentation.DOCUMENTED
import io.kstar.annotations.quality.Stability.STABLE_EXTENSIBLE
import io.kstar.annotations.quality.Testing.TESTING_INSUFFICIENT
import io.kstar.annotations.quality.TypeQuality
import io.kstar.core.collections.BaseCollection
import io.kstar.core.values.Maximum
import io.kstar.receptors.collection.Copyable
import java.util.function.Function

/**
 * A set with a maximum size. Adds useful methods to the usual [Set] operations, as well as implementing:
 *
 *
 *  * [Set]
 *  * [Sequence]
 *  * [Addable]
 *  * [Copyable]
 *  * [Countable]
 *  * [Joinable]
 *  * [StringFormattable]
 *
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
 * @see BaseCollection
 *
 * @see Copyable
 *
 * @see Factory
 *
 * @see Set
 */
@Suppress("unused")
@TypeQuality
(
    stability = STABLE_EXTENSIBLE,
    testing = TESTING_INSUFFICIENT,
    documentation = DOCUMENTED
)
abstract class BaseSet<Value> protected constructor(maximumSize: Maximum, values: Collection<Value>) :
    BaseCollection<Value>(maximumSize),
    Copyable<Value, BaseSet<Value>?>, MutableSet<Value>
{
    /** The backing set  */
    private var backingSet: MutableSet<Value>? = null

    /**
     * Construct a set with a maximum number of elements
     *
     * @param maximumSize The maximum size
     */
    protected constructor(maximumSize: Maximum = MAXIMUM) : this(maximumSize, emptySet<Value>())

    /**
     * @param maximumSize The maximum size of this set
     * @param values The initial values for this set
     */
    init
    {

        // If there is room for the initial values,
        if (values.size < maximumSize.asInt())
        {
            // save the set.
            backingSet = newBackingSet()
            backingSet.addAll(values)
        }
        else
        {
            // otherwise, signal that we're out of
            onOutOfRoom(values.size)

            // and leave the set empty.
            backingSet = newBackingSet()
        }
    }

    /**
     * {@inheritDoc}
     */
    open fun copy(): BaseSet<Value>?
    {
        return super.copy()
    }

    /**
     * {@inheritDoc}
     */
    fun <Output> map(mapper: Function<Value, Output>?): BaseSet<Output>
    {
        return super.map(mapper) as BaseSet<Output>
    }

    /**
     * {@inheritDoc}
     */
    open fun matching(matcher: Matcher<Value>): BaseSet<Value>?
    {
        return super.matching(matcher)
    }

    /**
     * {@inheritDoc}
     */
    fun onNewInstance(): BaseSet<Value>
    {
        return newCollection() as BaseSet<Value>
    }

    /**
     * {@inheritDoc}
     */
    open fun with(value: Value): BaseSet<Value>?
    {
        return super.with(value) as BaseSet<Value>?
    }

    /**
     * {@inheritDoc}
     */
    protected fun backingCollection(): Set<Value>?
    {
        return backingSet
    }

    /**
     * Returns a new backing set to store values in
     */
    protected fun newBackingSet(): MutableSet<Value>
    {
        return onNewBackingSet()
    }

    /**
     * Returns a new backing set to store values in
     */
    protected abstract fun onNewBackingSet(): MutableSet<Value>
}