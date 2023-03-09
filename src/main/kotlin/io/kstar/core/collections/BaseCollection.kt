@file:Suppress("MemberVisibilityCanBePrivate")

package io.kstar.core.collections

import io.kstar.annotations.quality.Documentation.*
import io.kstar.annotations.quality.Stability.STABLE_EXTENSIBLE
import io.kstar.annotations.quality.Testing.TESTING_INSUFFICIENT
import io.kstar.annotations.quality.TypeQuality
import io.kstar.core.collections.list.ObjectList
import io.kstar.core.collections.list.ObjectList.Companion.list
import io.kstar.core.collections.list.StringList
import io.kstar.core.language.Objects.toDebugString
import io.kstar.core.language.Objects.toHumanizedString
import io.kstar.core.language.Throw.illegalState
import io.kstar.core.values.Count
import io.kstar.core.values.Count.Companion.count
import io.kstar.core.values.Maximum
import io.kstar.receptors.collection.Addable
import io.kstar.receptors.collection.Joinable
import io.kstar.receptors.collection.Sized
import io.kstar.receptors.numeric.Countable
import io.kstar.receptors.objects.Factory
import io.kstar.receptors.objects.Matcher
import io.kstar.receptors.strings.AsString
import io.kstar.receptors.strings.StringFormat
import io.kstar.receptors.strings.StringFormat.DEBUG
import io.kstar.receptors.strings.StringFormat.TO_STRING
import java.util.function.Function

/**
 * Base class for all KivaKit collections.
 *
 *
 * A set with a maximum size. Adds useful methods to the usual [Set] operations, as well as implementing:
 *
 *  * [Addable]
 *  * [Collection]
 *  * [Countable]
 *  * [Joinable]
 *  * [Sequence]
 *  * [Sized]
 *  * [Joinable]
 *  * [AsString]
 *
 * **Adding**
 *
 *  * [add]
 *  * [addIfNotNull]
 *  * [addAll]
 *  * [addAllMatching]
 *
 * **Conversion**
 *
 *  * [asSequence]
 *  * [asIterable]
 *  * [asString]
 *  * [asStringList]
 *  * [asList]
 *
 * **Operations**
 *
 *  * [sorted]
 *
 * **Access**
 *
 *  * [first]
 *  * [map]
 *
 * **Membership**
 *
 *  * [contains]
 *  * [containsAll]
 *
 * **Size**
 *
 *  * [size]
 *  * [count]
 *  * [isEmpty]
 *  * [isNonEmpty]
 *
 * **Bounds**
 *
 *  * [maximumSize] - The maximum size of this list
 *  * [totalRoom] - The maximum size of this list
 *  * [hasRoomFor] - For use by subclasses to check their size
 *  * [onOutOfRoom] - Responds with a warning when the list is out of space
 *
 * **Removing**
 *
 *  * [clear]
 *  * [remove]
 *  * [removeAll]
 *  * [removeIf]
 *  * [removeAllMatching]
 *
 * **String Conversions**
 *
 *  * [join] - This list joined by the list [.separator]
 *  * [separator] - The separator used when joining this list into a string
 *
 * **Functional Methods**
 *
 *  * [map]
 *  * [with]
 *  * [without]
 *
 * @author Jonathan Locke
 * @see Addable
 * @see Appendable
 * @see Collection
 * @see Countable
 * @see Joinable
 * @see Sequence
 * @see Sized
 * @see AsString
 */
@Suppress("unused")
@TypeQuality
(
    stability = STABLE_EXTENSIBLE,
    testing = TESTING_INSUFFICIENT,
    documentation = DOCUMENTED
)
abstract class BaseCollection<Value> protected constructor
(
    /** The Maximum size of this collection */
    var maximumSize: Maximum,

    /** The separator to use when joining values into a string */
    var separator: String = ", ",

    /** The underlying collection */
    private val backingCollection: MutableCollection<Value>

) :
    Addable<Value>,
    MutableCollection<Value> by backingCollection,
    Countable,
    Joinable<Value>,
    Sequence<Value>,
    Factory<BaseCollection<Value>>,
    Sized,
    AsString
{
    /** True if this set ran out of room, and we've already warned about it  */
    private var warnedAboutOutOfRoom = false

    /**
     * {@inheritDoc}
     */
    override fun asString(format: StringFormat): String
    {
        return when (format)
        {
            DEBUG -> join(separator()) { it.toDebugString() }
            else -> join()
        }
    }

    /**
     * Returns this list as a string list
     */
    fun asStringList(): StringList
    {
        return StringList(maximumSize, this)
    }

    /**
     * Returns true if this collection contains any value in the given collection
     *
     * @param collection The collection to check
     * @return True if this collection contains a value in the given collection
     */
    fun containsAny(collection: Collection<*>): Boolean
    {
        return stream().anyMatch { o: Value -> collection.contains(o) }
    }

    /**
     * {@inheritDoc}
     */
    override fun count(): Count
    {
        return count(size)
    }

    /**
     * {@inheritDoc}
     */
    override fun equals(other: Any?): Boolean
    {
        return backingCollection == other
    }

    /**
     * {@inheritDoc}
     */
    override fun hashCode(): Int
    {
        return backingCollection.hashCode()
    }

    /**
     * Returns this bounded list with all elements mapped by the given mapper to the mapper's target type
     */
    @Suppress("UNCHECKED_CAST")
    fun <To> map(mapper: Function<Value, To>): BaseCollection<To>
    {
        val filtered = new() as BaseCollection<To>
        for (element in this)
        {
            filtered.add(mapper.apply(element))
        }
        return filtered
    }

    /**
     * Called when a bounded list runs out of room
     */
    override fun onOutOfRoom(values: Int)
    {
        if (!warnedAboutOutOfRoom)
        {
            warnedAboutOutOfRoom = true
            globalListener().warning(Throwable(), "Adding $ values, would exceed maximum size of $. Ignoring operation.", values, totalRoom())
        }
    }

    /**
     * Removes all values matching the given matcher
     */
    fun removeAllMatching(matcher: Matcher<Value>): Boolean
    {
        return removeIf(matcher)
    }

    /**
     * Returns a copy of this collection sorted by the given comparator
     */
    fun sorted(comparator: Comparator<Value>): ObjectList<Value>
    {
        val sorted = list<Value>()
        sorted.addAll(this)
        sorted.sort(comparator)
        return sorted
    }

    /**
     * Returns an [ObjectList] with the values in this collection in sorted order.
     */
    open fun sorted(): ObjectList<Value>
    {
        return sorted { a: Value, b: Value ->
            if (a is Comparable<in Value>)
            {
                a.compareTo(b)
            }
            illegalState("Cannot sort list of values that doesn't implement Comparable")
        }
    }

    /**
     * {@inheritDoc}
     */
    override fun toString(): String
    {
        return asString(TO_STRING)
    }

    /**
     * {@inheritDoc}
     */
    override fun totalRoom(): Int
    {
        return maximumSize.asInt()
    }

    /**
     * Returns this list with the given values
     */
    fun with(that: Iterable<Value>): BaseCollection<Value>
    {
        val copy = new()
        copy.addAll(this)
        copy.addAll(that)
        return copy
    }

    /**
     * Returns this list with the given values
     */
    fun with(that: Collection<Value>): BaseCollection<Value>
    {
        val copy = new()
        copy.addAll(this)
        copy.addAll(that)
        return copy
    }

    /**
     * Returns this list with the given values
     */
    fun with(that: Array<Value>): BaseCollection<Value>
    {
        val copy = new()
        copy.addAll(this)
        copy.addAll(that)
        return copy
    }

    /**
     * Makes a copy of this object but with the given value appended
     *
     * @param value The value to add
     * @return This object
     */
    fun with(value: Value): BaseCollection<Value>
    {
        val copy = new()
        copy.addAll(this)
        copy.add(value)
        return copy
    }

    /**
     * Returns this list with the given values
     */
    fun without(that: Collection<Value>): BaseCollection<Value>
    {
        val copy = new()
        copy.addAll(this)
        copy.removeAll(that.toSet())
        return copy
    }

    /**
     * Returns this list with the given values
     */
    fun without(that: Array<Value>): BaseCollection<Value>
    {
        val copy = new()
        copy.addAll(this)
        copy.removeAll(that.toSet())
        return copy
    }

    /**
     * Makes a copy of this object but with the given value appended
     *
     * @param value The value to add
     * @return This object
     */
    fun without(value: Value): BaseCollection<Value>
    {
        val copy = new()
        copy.addAll(this)
        copy.remove(value)
        return copy
    }

    /**
     * Convert the given value to a string
     *
     * @param value The value
     * @return A string corresponding to the value
     */
    protected fun toString(value: Value): String
    {
        return value.toHumanizedString()
    }
}