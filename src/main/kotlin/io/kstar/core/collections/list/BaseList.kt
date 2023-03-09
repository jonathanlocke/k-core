package io.kstar.core.collections.list

import io.kstar.annotations.documentation.UmlIncludeType
import io.kstar.annotations.quality.Documentation.DOCUMENTED
import io.kstar.annotations.quality.Stability.*
import io.kstar.annotations.quality.Testing.TESTING_INSUFFICIENT
import io.kstar.annotations.quality.TypeQuality
import io.kstar.core.collections.BaseCollection
import io.kstar.core.values.Maximum
import io.kstar.internal.Diagrams.DiagramCollections
import io.kstar.receptors.collection.Copyable
import io.kstar.receptors.collection.Indexable
import io.kstar.receptors.collection.Prependable
import io.kstar.receptors.collection.Sectionable
import java.util.*
import java.util.function.Predicate
import java.util.stream.Stream

/**
 * A base class for bounded lists which adds convenient methods as well as support for various KivaKit interfaces:
 *
 *
 *  * [Appendable]
 *  * [Copyable]
 *  * [Indexable]
 *  * [List]
 *  * [Prependable]
 *  * [RandomAccess]
 *  * [Sectionable]
 *
 *
 *
 * **Adding**
 *
 *
 *  * [.add]
 *  * [.addIfNotNull]
 *  * [.add]
 *  * [.addAll]
 *  * [.addAll]
 *  * [.addAll]
 *  * [.addAll]
 *  * [.addAll]
 *  * [.addAllMatching]
 *  * [.addAllMatching]
 *  * [.addAllMatching]
 *  * [.addAllMatching]
 *  * [.append]
 *  * [.appendIfNotNull]
 *  * [.appendAll]
 *  * [.appendAll]
 *  * [.appendAll]
 *  * [.appendAll]
 *  * [.appending]
 *  * [.appending]
 *  * [.prepend]
 *  * [.prependIfNotNull]
 *  * [.prependAll]
 *  * [.prependAll]
 *  * [.prependAll]
 *  * [.push]
 *
 *
 *
 * **Access**
 *
 *
 *  * [.copy]
 *  * [.first]
 *  * [.first]
 *  * [.first]
 *  * [.get]
 *  * [.last]
 *  * [.last]
 *  * [.last]
 *  * [.leftOf]
 *  * [.map]
 *  * [.matching]
 *  * [.pop]
 *  * [.rightOf]
 *  * [.set]
 *  * [.subList]
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
 *  * [.remove]
 *  * [.removeLast]
 *  * [.removeAll]
 *  * [.removeIf]
 *  * [.removeAllMatching]
 *
 *
 *
 * **Search/Replace**
 *
 *
 *  * [.indexOf]
 *  * [.lastIndexOf]
 *  * [.replaceAll]
 *  * [.replaceAll]
 *
 *
 *
 * **Tests**
 *
 *
 *  * [.endsWith] - True if this list ends with the same elements as the given list
 *  * [.startsWith] - True if this list starts with the same elements as the given list
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
 *  * [.bulleted] - The elements in this list as a bulleted string, with on element to a line
 *  * [.bulleted] - An indented bullet list of the elements in this list
 *  * [.join] - This list joined by the list [.separator]
 *  * [.separator] - The separator used when joining this list into a string
 *  * [.titledBox]
 *  * [.titledBox]
 *
 *
 *
 * **Operations**
 *
 *
 *  * [.shuffle]
 *  * [.shuffle]
 *  * [.sort]
 *  * [.sorted]
 *  * [.sorted]
 *  * [.uniqued]
 *
 *
 *
 * **Functional Methods**
 *
 *
 *
 * Some methods are functional and return a new list. The method [.newList] is used to create lists.
 * Subclasses create the subclass list type by overriding [.onNewList].
 *
 *
 *
 *  * [.copy] - A copy of this list
 *  * [.without] - This list without the matching elements
 *  * [.first] - A new list with the first n elements in it
 *  * [.first] - A new list with the first n elements in it
 *  * [.last] - A new list with the first n elements in it
 *  * [.last] - A new list with the first n elements in it
 *  * [.leftOf] - The elements in this list to the left on the given index, exclusive
 *  * [.rightOf] - The elements in this list to the right on the given index, exclusive
 *  * [.matching] - A copy of this list filtered to matching elements
 *  * [.map] - A copy of this list with elements mapped to another type
 *  * [.sorted]
 *  * [.sorted]
 *  * [.reversed] - This list reversed
 *  * [.maybeReversed] - This list reversed if the given boolean is true
 *  * [.uniqued]
 *  * [.with]
 *  * [.without]
 *
 *
 * @author jonathanl (shibo)
 * @see Appendable
 *
 * @see Copyable
 *
 * @see Indexable
 *
 * @see List
 *
 * @see Prependable
 *
 * @see RandomAccess
 *
 * @see Sectionable
 */
@Suppress("unused")
@UmlIncludeType(inDiagrams = [DiagramCollections::class])
@TypeQuality
(
    stability = STABLE_EXTENSIBLE,
    testing = TESTING_INSUFFICIENT,
    documentation = DOCUMENTED
)
abstract class BaseList<Value> protected constructor(maximumSize: Maximum, list: MutableCollection<Value>)
    : BaseCollection<Value>(maximumSize),
      Appendable<Value>,
      Copyable<Value, BaseList<Value>>,
      Indexable<Value>,
      MutableList<Value>,
      Prependable<Value>,
      RandomAccess,
      Sectionable<Value, BaseList<Value>>
{
    /** Initial list implementation while mutable  */
    private val list: MutableList<Value>? = null

    /**
     * @param maximumSize The maximum size of this list
     */
    protected constructor(maximumSize: Maximum) : this(maximumSize, ArrayList<Value>())

    /**
     * @param maximumSize The maximum size of this list
     * @param list The list implementation to use
     */
    init
    {

        // If we have room for the list at all,
        if (list.size < maximumSize.asInt())
        {
            // save it.
            if (list is List<*>)
            {
                this.list = list as MutableList<Value>
            }
            else
            {
                this.list = ArrayList(list)
            }
        }
        else
        {
            // otherwise, signal that the list is out of room,
            onOutOfRoom(list.size)

            // and leave the list empty.
            this.list = ArrayList()
        }
    }

    /**
     * An unbounded list
     */
    protected constructor() : this(MAXIMUM)

    /**
     * An unbounded list with the given list implementation
     */
    protected constructor(collection: MutableCollection<Value>) : this(MAXIMUM, collection)

    /**
     * {@inheritDoc}
     */
    override fun add(index: Int, value: Value)
    {
        if (hasRoomFor(1))
        {
            list!!.add(index, value)
        }
    }

    /**
     * {@inheritDoc}
     */
    override fun addAll(index: Int, collection: Collection<Value>): Boolean
    {
        return if (hasRoomFor(collection.size))
        {
            list!!.addAll(index, collection)
        }
        else false
    }

    open fun appending(values: Iterable<Value>?): BaseList<Value>?
    {
        return super<Appendable>.appending(values)
    }

    open fun appending(value: Value): BaseList<Value>?
    {
        return super<Appendable>.appending(value)
    }

    /**
     * {@inheritDoc}
     */
    open fun appendingIfNotNull(value: Value): BaseList<Value>?
    {
        return super<Appendable>.appendingIfNotNull(value)
    }
    /**
     * Returns the items in this list in a bulleted ASCII art representation with the given indent
     */
    /**
     * Returns the items in this list in a bulleted ASCII art representation
     */
    @JvmOverloads
    fun bulleted(indent: Int = 4): String
    {
        return AsciiArt.bulleted(indent, this)
    }

    open fun copy(): BaseList<Value>?
    {
        return super.copy()
    }

    /**
     * {@inheritDoc}
     */
    override fun equals(`object`: Any?): Boolean
    {
        if (`object` is List<*>)
        {
            // The lists are only seen as equal if they have the same objects in
            // the same order.
            if (size == `object`.size)
            {
                for (i in 0 until size)
                {
                    if (get(i) !== `object`[i] && get(i) != `object`[i])
                    {
                        return false
                    }
                }
                return true
            }
        }
        return false
    }

    /**
     * {@inheritDoc}
     */
    open fun first(count: Int): BaseList<Value>
    {
        return super.first(count)
    }

    /**
     * Returns the first count elements of this list
     */
    open fun first(count: Count): BaseList<Value>
    {
        return first(count.asInt())
    }

    /**
     * {@inheritDoc}
     */
    override fun get(index: Int): Value
    {
        return list!![index]
    }

    /**
     * {@inheritDoc}
     */
    override fun indexOf(element: Any): Int
    {
        return list!!.indexOf(element)
    }

    /**
     * {@inheritDoc}
     */
    open fun last(count: Int): BaseList<Value>
    {
        return super.last(count)
    }

    /**
     * Returns the last count values of this list
     */
    fun last(count: Count): BaseList<Value>
    {
        return last(count.asInt())
    }

    /**
     * {@inheritDoc}
     */
    override fun lastIndexOf(element: Any): Int
    {
        return list!!.lastIndexOf(element)
    }

    /**
     * {@inheritDoc}
     */
    open fun leftOf(index: Int): BaseList<Value>?
    {
        return super.leftOf(index)
    }

    /**
     * {@inheritDoc}
     */
    override fun listIterator(): MutableListIterator<Value>
    {
        return list!!.listIterator()
    }

    /**
     * {@inheritDoc}
     */
    override fun listIterator(index: Int): MutableListIterator<Value>
    {
        return list!!.listIterator(index)
    }

    /**
     * Returns this list reversed if reverse is true, or the list itself if it is false
     */
    open fun maybeReversed(reverse: Boolean): BaseList<Value>?
    {
        return if (reverse) reversed() else this
    }

    /**
     * Returns this list as a numbered string list
     *
     * @return The string list
     */
    open fun numbered(): StringList
    {
        return asStringList().numbered()
    }

    /**
     * {@inheritDoc}
     */
    open fun onAppend(value: Value): Boolean
    {
        return onAdd(value)
    }

    /**
     * {@inheritDoc}
     */
    open fun onNewInstance(): BaseList<Value>
    {
        return newList()
    }

    /**
     * Prepends the given value to the front of this list
     */
    fun onPrepend(value: Value): Boolean
    {
        list!!.add(0, value)
        return true
    }

    /**
     * {@inheritDoc}
     */
    override fun parallelStream(): Stream<Value>
    {
        return list!!.parallelStream()
    }

    /**
     * Returns the last value in this list, after removing it
     */
    fun pop(): Value?
    {
        return if (isEmpty()) null else removeLast()
    }

    open fun prepending(values: Iterable<Value>?): BaseList<Value>?
    {
        return super.prepending(values) as BaseList<Value>?
    }

    open fun prepending(value: Value): BaseList<Value>?
    {
        return super.prepending(value) as BaseList<Value>?
    }

    /**
     * {@inheritDoc}
     */
    override fun remove(index: Int): Value
    {
        return list!!.removeAt(index)
    }

    /**
     * {@inheritDoc}
     */
    override fun removeIf(filter: Predicate<in Value>): Boolean
    {
        return list!!.removeIf(filter)
    }

    /**
     * Returns removes the last element in this list
     */
    fun removeLast(): Value?
    {
        return if (!isEmpty())
        {
            removeAt(size - 1)
        }
        else null
    }

    /**
     * Replace all occurrences of the given value with the given replacement
     *
     * @param value The value to replace
     * @param replacement The value to substitute
     */
    fun replaceAll(value: Value, replacement: Value): Boolean
    {
        return Collections.replaceAll(list, value, replacement)
    }

    /**
     * Reverses this list in-place
     */
    fun reverse()
    {
        Collections.reverse(this)
    }

    /**
     * Returns this list reversed
     */
    open fun reversed(): BaseList<Value>?
    {
        val copy = copy()
        copy.reverse()
        return copy
    }

    /**
     * Returns the values to the right of the given index
     */
    open fun rightOf(index: Int): BaseList<Value>?
    {
        return super.rightOf(index)
    }

    /**
     * {@inheritDoc}
     */
    override fun set(index: Int, value: Value): Value
    {
        return list!!.set(index, value)
    }
    /**
     * Shuffles this list using the given [Random] number generator
     *
     * @param random The random number generator
     */
    /**
     * Shuffles this list into a random ordering
     */
    @JvmOverloads
    fun shuffle(random: Random? = Random())
    {
        Collections.shuffle(list, random)
    }

    /**
     * {@inheritDoc}
     */
    override fun sort(comparator: Comparator<in Value>)
    {
        list!!.sortWith(comparator)
    }

    /**
     * {@inheritDoc}
     */
    override fun spliterator(): Spliterator<Value>
    {
        return list!!.spliterator()
    }

    /**
     * {@inheritDoc}
     */
    override fun stream(): Stream<Value>
    {
        return list!!.stream()
    }

    /**
     * {@inheritDoc}
     */
    override fun subList(fromIndex: Int, toIndex: Int): MutableList<Value>
    {
        return ObjectList.Companion.list<Value>(list!!.subList(fromIndex, toIndex))
    }

    /**
     * Returns this list of objects as an ASCII art text box with the given title
     */
    fun titledBox(title: String?): String
    {
        return textBox(title, join("\n"))
    }

    fun titledBox(style: TextBoxStyle?, title: String?): String
    {
        return textBox(style, title, join("\n"))
    }

    fun titledBox(style: TextBoxStyle?, title: String?, vararg arguments: Any?): String
    {
        return titledBox(style, format(title, arguments))
    }

    /**
     * Returns this list of objects as an ASCII art text box with the given title
     */
    fun titledBox(title: String?, vararg arguments: Any?): String
    {
        return titledBox(format(title, arguments))
    }

    /**
     * Returns a copy of this list with only unique elements in it
     */
    @Suppress("SpellCheckingInspection")
    open fun uniqued(): BaseList<Value>?
    {
        val list = newList()
        list.addAll(asSet())
        return list
    }

    /**
     * {@inheritDoc}
     */
    protected fun backingCollection(): Collection<Value>?
    {
        return list
    }

    /**
     * Returns the wrapped list
     */
    protected fun backingList(): List<Value>?
    {
        return list
    }

    /**
     * Returns a new [BaseList] subclass
     */
    protected fun newList(): BaseList<Value>
    {
        return onNewList()
    }

    /**
     * {@inheritDoc}
     */
    protected fun onNewCollection(): BaseList<Value>
    {
        return newList()
    }

    /**
     * Creates a list of the subclass type
     *
     * @return The new list
     */
    protected abstract fun onNewList(): BaseList<Value>
}