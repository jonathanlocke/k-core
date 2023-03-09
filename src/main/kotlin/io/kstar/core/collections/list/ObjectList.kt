package io.kstar.core.collections.list

import io.kstar.annotations.documentation.UmlIncludeType
import io.kstar.annotations.quality.Documentation.DOCUMENTED
import io.kstar.annotations.quality.Stability.STABLE_EXTENSIBLE
import io.kstar.annotations.quality.Testing.*
import io.kstar.annotations.quality.TypeQuality
import io.kstar.core.values.Maximum
import io.kstar.internal.Diagrams.DiagramCollections
import java.util.function.Function

/**
 * A bounded list of objects with overrides of methods from [BaseList] to downcast return values to
 * [ObjectList] for convenience. New instances of [ObjectList] are created by [BaseList] by calling
 * [.onNewInstance], allowing functional logic to reside in the base class. For details on the methods inherited
 * from [BaseList], see that class.
 *
 *
 * **Partitioning**
 *
 *
 *  * [.partition] - The elements in this list partitioned into N new lists
 *
 *
 *
 *
 * The methods [.list] and [.list] can be used to construct constant lists.
 * The factory methods [.listFromInts] and [.listFromLongs]
 * construct lists of objects from integer and long values using the given map factories to convert the values into
 * objects. The method [.listFromLongs] iterates through the given [LongValued] object
 * values, passing each quantum to the given primitive map factory and adding the resulting object to a new object list.
 *
 *
 *
 * **Creation**
 *
 *
 *  * [.emptyList]
 *  * [.list]
 *  * [.list]
 *  * [.list]
 *  * [.list]
 *  * [.list]
 *  * [.list]
 *  * [.list]
 *  * [.list]
 *  * [.listFromArray]
 *  * [.listFromLongs]
 *  * [.listFromLongs]
 *  * [.listFromLongs]
 *  * [.listFromInts]
 *
 *
 * @param <Value> The object type
 * @author jonathanl (shibo)
 * @see BaseList
 */
@Suppress("unused")
@UmlIncludeType(inDiagrams = [DiagramCollections::class])
@TypeQuality
(
    stability = STABLE_EXTENSIBLE,
    testing = TESTING_INSUFFICIENT,
    documentation = DOCUMENTED
)
open class ObjectList<Value> : BaseList<Value>
{
    /**
     * An unbounded object list
     */
    constructor() : this(Maximum.MAXIMUM)

    /**
     * A list of objects with the given upper bound
     */
    constructor(maximumSize: Maximum) : super(maximumSize)

    /**
     * A list of objects with the given upper bound
     */
    constructor(maximumSize: Maximum, collection: MutableCollection<Value>) : super(maximumSize, collection)

    /**
     * A list of objects with the given upper bound
     */
    constructor(collection: Collection<Value>?) : super(collection)

    /**
     * {@inheritDoc}
     */
    override fun appending(values: Iterable<Value?>?): ObjectList<Value?>?
    {
        return super.appending(values) as ObjectList<Value?>
    }

    /**
     * {@inheritDoc}
     */
    override fun appending(value: Value?): ObjectList<Value?>?
    {
        return super.appending(value) as ObjectList<Value?>
    }

    /**
     * {@inheritDoc}
     */
    override fun appendingIfNotNull(value: Value?): ObjectList<Value?>?
    {
        return super.appendingIfNotNull(value) as ObjectList<Value?>
    }

    /**
     * Returns a copy of this list
     *
     * @return The copy
     */
    override fun copy(): ObjectList<Value?>?
    {
        return super.copy() as ObjectList<Value?>
    }

    /**
     * {@inheritDoc}
     */
    override fun first(count: Count): ObjectList<Value?>
    {
        return super.first(count) as ObjectList<Value?>
    }

    /**
     * {@inheritDoc}
     */
    override fun first(count: Int): ObjectList<Value?>
    {
        return super.first(count) as ObjectList<Value?>
    }

    /**
     * {@inheritDoc}
     */
    override fun last(count: Int): ObjectList<Value?>
    {
        return super.last(count) as ObjectList<Value?>
    }

    /**
     * {@inheritDoc}
     */
    override fun leftOf(index: Int): ObjectList<Value?>?
    {
        return super.leftOf(index) as ObjectList<Value?>
    }

    /**
     * {@inheritDoc}
     */
    open fun <To> map(mapper: Function<Value, To?>?): ObjectList<To?>?
    {
        return super.map(mapper) as ObjectList<To?>?
    }

    /**
     * Returns a new list containing the elements in this list that match the given matcher.
     *
     * @param matcher The matcher to use
     * @return The list of elements matching the matcher
     */
    open fun matching(matcher: Matcher<Value>): ObjectList<Value>?
    {
        return super.matching(matcher) as ObjectList<Value>?
    }

    /**
     * {@inheritDoc}
     */
    override fun maybeReversed(reverse: Boolean): ObjectList<Value?>?
    {
        return super.maybeReversed(reverse) as ObjectList<Value?>
    }

    /**
     * {@inheritDoc}
     */
    override fun onAppend(value: Value?): Boolean
    {
        return add(value)
    }

    /**
     * Returns this object list partitioned in to n object lists
     */
    fun partition(partitions: Count): ObjectList<ObjectList<Value>>
    {
        val lists: `var` = ObjectList<ObjectList<Value>>(maximumSize())
        var i: `var` = 0
        var list: `var` = -1
        val every: `var` = Math.round(size.toDouble() / partitions.asInt() as Double).toInt()
        for (`object` in this)
        {
            if (i++ % every == 0 && list < partitions.asInt() - 1)
            {
                lists.add(ObjectList<Value>())
                list++
            }
            lists.get(list).add(`object`)
        }
        return lists
    }

    override fun prepending(value: Value?): ObjectList<Value?>?
    {
        return super.prepending(value) as ObjectList<Value?>
    }

    override fun prepending(values: Iterable<Value?>?): ObjectList<Value?>?
    {
        return super.prepending(values) as ObjectList<Value?>
    }

    open fun prependingIfNotNull(value: Value): ObjectList<Value>?
    {
        return super.prependingIfNotNull(value) as ObjectList<Value>?
    }

    /**
     * {@inheritDoc}
     */
    override fun reversed(): ObjectList<Value?>?
    {
        return super.reversed() as ObjectList<Value?>
    }

    /**
     * {@inheritDoc}
     */
    override fun rightOf(index: Int): ObjectList<Value?>?
    {
        return super.rightOf(index) as ObjectList<Value?>
    }

    /**
     * {@inheritDoc}
     */
    open fun sorted(): ObjectList<Value>?
    {
        return super.sorted()
    }

    /**
     * Creates a list containing the unique objects in this list
     *
     * @return The unique objects
     */
    override fun uniqued(): ObjectList<Value?>?
    {
        return super.uniqued() as ObjectList<Value?>
    }

    /**
     * {@inheritDoc}
     */
    open fun with(value: Iterable<Value>?): ObjectList<Value>?
    {
        return super.with(value) as ObjectList<Value>?
    }

    /**
     * {@inheritDoc}
     */
    open fun with(value: Value): ObjectList<Value>?
    {
        return super.with(value) as ObjectList<Value>?
    }

    /**
     * {@inheritDoc}
     */
    open fun with(value: Collection<Value>?): ObjectList<Value>?
    {
        return super.with(value) as ObjectList<Value>?
    }

    /**
     * {@inheritDoc}
     */
    open fun with(value: Array<Value>?): ObjectList<Value>?
    {
        return super.with(value) as ObjectList<Value>?
    }

    /**
     * {@inheritDoc}
     */
    open fun without(matcher: Matcher<Value>): ObjectList<Value>?
    {
        return super.without(matcher) as ObjectList<Value>?
    }

    open fun without(value: Value): ObjectList<Value>?
    {
        return super.without(value) as ObjectList<Value>?
    }

    open fun without(that: Collection<Value>?): ObjectList<Value>?
    {
        return super.without(that) as ObjectList<Value>?
    }

    open fun without(that: Array<Value>?): ObjectList<Value>?
    {
        return super.without(that) as ObjectList<Value>?
    }

    /**
     * {@inheritDoc}
     */
    override fun onNewList(): BaseList<Value?>
    {
        return list<Value?>()
    }

    companion object
    {
        /**
         * Returns an empty object list
         */
        fun <T> emptyList(): ObjectList<T>
        {
            return ObjectList()
        }

        /**
         * Returns a list of objects from the given iterable
         */
        fun <T> list(maximumSize: Maximum?, values: Iterable<T>?): ObjectList<T>
        {
            val list: `var` = ObjectList<T>(maximumSize)
            list.appendAll(values)
            return list
        }

        /**
         * Returns a list of objects from the given iterable
         */
        fun <T> list(maximumSize: Maximum?, values: Collection<T>?): ObjectList<T>
        {
            return ObjectList<T>(values)
        }

        /**
         * Returns a list of objects from the given iterator
         */
        fun <T> list(maximumSize: Maximum?, values: Iterator<T>?): ObjectList<T>
        {
            val list: `var` = ObjectList<T>()
            list.appendAll(values)
            return list
        }

        /**
         * Returns a list of objects from the given iterable
         */
        fun <T> list(values: Iterable<T>?): ObjectList<T>
        {
            val list: `var` = ObjectList<T>()
            list.appendAll(values)
            return list
        }

        /**
         * Returns a list of objects from the given iterable
         */
        fun <T> list(values: Collection<T>?): ObjectList<T>
        {
            return ObjectList<T>(values)
        }

        /**
         * Returns a list of objects from the given iterator
         */
        fun <T> list(values: Iterator<T>?): ObjectList<T>
        {
            val list: `var` = ObjectList<T>()
            list.appendAll(values)
            return list
        }

        /**
         * Returns the given list of objects with a maximum size
         */
        @SafeVarargs
        fun <T> list(maximumSize: Maximum?, vararg objects: T): ObjectList<T>
        {
            val list: `var` = ObjectList<T>(maximumSize)
            list.addAll(objects)
            return list
        }

        /**
         * Returns the given list of objects
         */
        @SafeVarargs
        fun <T> list(vararg objects: T): ObjectList<T>
        {
            val list = ObjectList<T>()
            list.addAll(objects)
            return list
        }

        fun <T> listFromArray(objects: Array<T>?): ObjectList<T>
        {
            val list: `var` = ObjectList<T>()
            list.addAll(objects)
            return list
        }

        /**
         * Returns a list of elements from the given integers created using the given map factory
         */
        fun <T> listFromInts(factory: IntMapper<T>, vararg values: Int): ObjectList<T>
        {
            val objects: `var` = ObjectList<T>()
            for (value in values)
            {
                objects.add(factory.map(value))
            }
            return objects
        }

        /**
         * Returns a list of longs containing the values in the given array
         */
        fun listFromLongs(objects: LongArray): ObjectList<Long>
        {
            val list: `var` = ObjectList<Long>()
            for (at in objects)
            {
                list.add(at)
            }
            return list
        }

        /**
         * Returns a list of elements from the given integers created using the given map factory
         */
        fun <T> listFromLongs(factory: LongMapper<T>, values: Iterable<LongValued?>): ObjectList<T>
        {
            val objects: `var` = ObjectList<T>()
            for (value in values)
            {
                objects.add(factory.map(value.longValue()))
            }
            return objects
        }

        /**
         * Returns a list of elements from the given integers created using the given map factory
         */
        fun <T> listFromLongs(factory: LongMapper<T>, vararg values: Long): ObjectList<T>
        {
            val objects: `var` = ObjectList<T>()
            for (value in values)
            {
                objects.add(factory.map(value))
            }
            return objects
        }
    }
}