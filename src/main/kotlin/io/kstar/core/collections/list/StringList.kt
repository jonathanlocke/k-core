package io.kstar.core.collections.list

import com.telenav.kivakit.annotations.code.quality.TypeQuality
import java.util.*

/**
 * A list of strings, adding useful string operations to [ObjectList]. Inherited methods that return lists are
 * overridden with down-casting methods for convenience. String lists can be constructed using the constructors, but
 * also using factory methods. Inherited methods are documented in [ObjectList] and [BaseList].
 *
 *
 * **Creation**
 *
 *
 *  * [.repeat] - A list of the given string repeated the given number of times
 *  * [.split] - A list of strings from the given string split on the given string
 *  * [.split] - A list of strings from the given string split on the given character
 *  * [.splitOnPattern] - A list of strings from the given string split on the given regular expression
 *  * [.stringList] - A string list of the given collection of strings
 *  * [.stringList] - A string list of the given strings
 *  * [.stringList] - A list of the strings in the given collection
 *  * [.stringList] - A list of the strings returned by the given iterable
 *  * [.stringList] - A string list of the given strings
 *  * [.stringList] - A string list for the given strings
 *  * [.words] - A list of the whitespace-separated words in the given string
 *
 *
 *
 * **Length**
 *
 *
 *  * [.longest] - The length of the longest string in this list
 *
 *
 *
 * **Conversions**
 *
 *
 *  * [.asStringArray] - This string list as a string array
 *  * [.asVariableMap]
 *
 *
 *
 * **String Operations**
 *
 *
 *  * [.doubleQuoted] - This string list with all strings in double quotes
 *  * [.indented] - This string list indented the given number of spaces
 *  * [.numbered] - This string list with each string prefixed by a number starting at 1
 *  * [.prefixedWith] - This string list with each element prefixed with the given string
 *  * [.singleQuoted] - This string list with all strings in single quotes
 *  * [.titledBox] - This string list in a titled box
 *  * [.println] - Prints this list, separated by newlines
 *
 */
@Suppress("unused")
@UmlClassDiagram(diagram = DiagramString::class)
@TypeQuality(stability = STABLE_EXTENSIBLE, testing = TESTING_INSUFFICIENT, documentation = DOCUMENTED)
class StringList : ObjectList<String>
{
    constructor() : this(MAXIMUM)
    constructor(iterable: Iterable<*>) : this(MAXIMUM, iterable)
    constructor(collection: Collection<String?>?) : super(collection)
    constructor(iterator: Iterator<*>) : this(MAXIMUM, iterator)
    constructor(maximumSize: Maximum?) : super(maximumSize)
    constructor(maximumSize: Maximum?, iterable: Iterable<*>) : super(maximumSize)
    {
        for (`object` in iterable)
        {
            add(objectToString(`object`))
        }
    }

    /**
     * Splits the given text on the given delimiter, returning a list of strings
     *
     * @param pattern The regular expression to split on
     * @return The list of strings
     */
    fun split(text: String, pattern: String): StringList
    {
        return stringList(text.split(pattern))
    }

    constructor(maximumSize: Maximum?, iterator: Iterator<*>) : super(maximumSize)
    {
        while (iterator.hasNext())
        {
            add(objectToString(iterator.next()))
        }
    }

    constructor(maximumSize: Maximum?, vararg strings: String?) : super(maximumSize)
    {
        addAll(Arrays.asList(*strings))
    }

    /**
     * Returns adds the given formatted message to this string list
     */
    fun add(message: String?, vararg arguments: Any?): StringList
    {
        add(format(message, arguments))
        return this
    }

    fun addObject(`object`: Any): StringList
    {
        add(`object`.toString())
        return this
    }

    override fun appending(values: Iterable<String?>?): StringList
    {
        return super.appending(values) as StringList
    }

    override fun appending(value: String?): StringList
    {
        return super.appending(value) as StringList
    }

    override fun appendingIfNotNull(value: String?): StringList
    {
        return super.appendingIfNotNull(value) as StringList
    }

    fun asStringArray(): Array<String>
    {
        return kotlin.collections.toTypedArray<String>()
    }

    /**
     * Returns this list of strings as a variable map where the even elements are keys and the odd elements are values.
     */
    fun asVariableMap(): VariableMap<String>
    {
        val variables: `var` = VariableMap<String>()
        var i: `var` = 0
        while (i < size)
        {
            if (i + 1 < size)
            {
                variables.add(get(i), get(i + 1))
            }
            i += 2
        }
        return variables
    }

    /**
     * Returns this list with braces around it indented by 4 spaces
     */
    fun bracketed(): StringList
    {
        return copy()
            .prepending("{")
            .appending("}")
    }

    /**
     * {@inheritDoc}
     */
    override fun copy(): StringList
    {
        return super.copy() as StringList
    }

    /**
     * Returns this string list with each element in double quotes
     */
    fun doubleQuoted(): StringList
    {
        val quoted: `var` = StringList(maximumSize())
        for (value in this)
        {
            quoted.add("\"" + value + "\"")
        }
        return quoted
    }

    /**
     * Returns this string list alternated with blank lines
     */
    fun doubleSpaced(): StringList
    {
        val doubleSpaced: `var` = stringList()
        for (at in this)
        {
            if (doubleSpaced.isNonEmpty())
            {
                doubleSpaced.add("")
            }
            doubleSpaced.add(at)
        }
        return doubleSpaced
    }

    /**
     * {@inheritDoc}
     */
    override fun first(count: Count): StringList
    {
        return super.first(count) as StringList
    }

    /**
     * {@inheritDoc}
     */
    override fun first(count: Int): StringList
    {
        return super.first(count) as StringList
    }
    /**
     * Returns this string list indented the given number of indent strings
     */
    /**
     * Returns this string list indented the given number of spaces
     */
    @JvmOverloads
    fun indented(spaces: Int, indent: String? = " "): StringList
    {
        val copy: `var` = StringList()
        val i: `var` = 0
        for (string in this)
        {
            copy.add(AsciiArt.repeat(spaces, indent) + string)
        }
        return copy
    }

    val isBlank: Boolean
        /**
         * Returns true if all elements of this string list are blank
         */
        get()
        {
            for (at in this)
            {
                if (!at.isBlank())
                {
                    return false
                }
            }
            return true
        }

    /**
     * {@inheritDoc}
     */
    override fun leftOf(index: Int): StringList
    {
        return super.leftOf(index) as StringList
    }

    /**
     * Returns the length of the longest string in this list
     */
    fun longest(): Count
    {
        var count = 0
        for (at in this)
        {
            count = max(at.length(), count)
        }
        return Count.count(count)
    }

    /**
     * {@inheritDoc}
     */
    override fun matching(matcher: Matcher<String?>): StringList
    {
        return super.matching(matcher) as StringList
    }

    /**
     * {@inheritDoc}
     */
    override fun maybeReversed(reverse: Boolean): StringList
    {
        return super.maybeReversed(reverse) as StringList
    }

    /**
     * {@inheritDoc}
     */
    fun newInstance(): StringList
    {
        return super.newInstance() as StringList
    }

    /**
     * Returns this list with all elements numbered starting at 1
     */
    override fun numbered(): StringList
    {
        val numbered: `var` = newInstance()
        var number: `var` = 1
        for (value in this)
        {
            numbered.add(number.toString() + ". " + value)
            number++
        }
        return numbered
    }

    /**
     * {@inheritDoc}
     */
    override fun onNewInstance(): StringList
    {
        return StringList()
    }

    /**
     * Returns this string list with each element prefixed with the given prefix
     */
    fun prefixedWith(prefix: String): StringList
    {
        val prefixed: `var` = newInstance()
        for (string in this)
        {
            prefixed.add(prefix + string)
        }
        return prefixed
    }

    override fun prepending(value: String?): StringList
    {
        return super.prepending(value) as StringList
    }

    override fun prepending(values: Iterable<String?>?): StringList
    {
        return super.prepending(values) as StringList
    }

    override fun prependingIfNotNull(value: String?): StringList
    {
        return super.prependingIfNotNull(value) as StringList
    }

    /**
     * Prints the values in this string list to the console, separated by newlines
     */
    fun println(): StringList
    {
        console().println(join("\n"))
        return this
    }

    /**
     * {@inheritDoc}
     */
    override fun reversed(): StringList
    {
        return super.reversed() as StringList
    }

    /**
     * {@inheritDoc}
     */
    override fun rightOf(index: Int): StringList
    {
        return super.rightOf(index) as StringList
    }

    fun separator(separator: String?): StringList
    {
        return super.separator(separator) as StringList
    }

    /**
     * Returns this string list with each element in single quotes
     */
    fun singleQuoted(): StringList
    {
        val quoted: `var` = newInstance()
        for (value in this)
        {
            quoted.add("'$value'")
        }
        return quoted
    }

    /**
     * {@inheritDoc}
     */
    override fun sorted(): StringList
    {
        return super.sorted() as StringList
    }

    /**
     * {@inheritDoc}
     */
    fun sorted(comparator: Comparator<String?>?): StringList
    {
        return super.sorted(comparator) as StringList
    }

    /**
     * {@inheritDoc}
     */
    override fun subList(start: Int, end: Int): StringList
    {
        return super.subList(start, end) as StringList
    }

    fun tail(): StringList
    {
        return stringList(super.tail())
    }

    /**
     * Removes all leading and trailing blank lines
     */
    fun trim(): StringList?
    {
        var trimmed: `var`? = copy()
        while (trimmed.first().isBlank())
        {
            trimmed = trimmed.tail()
        }
        while (trimmed.last().isBlank())
        {
            trimmed = trimmed.first(trimmed.size() - 1)
        }
        return trimmed
    }

    /**
     * {@inheritDoc}
     */
    override fun uniqued(): StringList
    {
        return super.uniqued() as StringList
    }

    /**
     * {@inheritDoc}
     */
    override fun with(value: Iterable<String?>?): StringList
    {
        return super.with(value) as StringList
    }

    /**
     * {@inheritDoc}
     */
    override fun with(value: String?): StringList
    {
        return super.with(value) as StringList
    }

    /**
     * {@inheritDoc}
     */
    override fun with(value: Collection<String?>?): StringList
    {
        return super.with(value) as StringList
    }

    /**
     * {@inheritDoc}
     */
    override fun with(vararg value: String?): StringList
    {
        return super.with(value) as StringList
    }

    /**
     * {@inheritDoc}
     */
    override fun without(matcher: Matcher<String?>): StringList?
    {
        return super.without(matcher) as StringList?
    }

    override fun without(that: Collection<String?>?): StringList
    {
        return super.without(that) as StringList
    }

    override fun without(s: String?): StringList
    {
        return super.without(s) as StringList
    }

    override fun without(that: Array<String?>?): StringList
    {
        return super.without(that) as StringList
    }

    protected fun objectToString(`object`: Any?): String
    {
        return StringConversions.toHumanizedString(`object`)
    }

    /**
     * {@inheritDoc}
     */
    override fun onNewList(): StringList
    {
        return StringList()
    }

    companion object
    {
        /**
         * Returns a list of the lines in the given text
         *
         * @param text The text
         */
        fun lines(text: String?): StringList
        {
            return split(text, "\n")
        }

        /**
         * Returns a string list of the given text repeated the given number of times
         */
        fun repeat(text: String?, times: Int): StringList
        {
            val list: `var` = StringList()
            for (i in 0 until times)
            {
                list.add(text)
            }
            return list
        }

        /**
         * Returns the list of strings resulting from splitting the given string on a delimiter character. The
         * [StringList] is unbounded for backwards compatibility.
         */
        fun split(string: String?, delimiter: Char): StringList
        {
            return split(MAXIMUM, string, delimiter)
        }

        /**
         * Returns the list of strings resulting from splitting the given string on a delimiter string. The
         * [StringList] is unbounded for backwards compatibility.
         */
        fun split(string: String?, delimiter: String): StringList
        {
            return split(MAXIMUM, string, delimiter)
        }

        /**
         * Returns a string list of the given maximum size from the given text split on the given delimiter
         */
        fun split(maximumSize: Maximum?, string: String?, delimiter: Char): StringList
        {
            if (string == null)
            {
                return StringList(maximumSize)
            }
            val strings: `var` = StringList(maximumSize)
            var pos: `var` = 0
            while (true)
            {
                val next: `var` = string.indexOf(delimiter, pos)
                if (next == -1)
                {
                    strings.add(string.substring(pos))
                    break
                }
                else
                {
                    strings.add(string.substring(pos, next))
                }
                pos = next + 1
            }
            return strings
        }

        /**
         * Returns a string list of the given maximum size from the given text split on the given delimiter
         */
        fun split(maximumSize: Maximum?, text: String?, delimiter: String): StringList
        {
            if (text == null)
            {
                return StringList(maximumSize)
            }
            val strings: `var` = StringList(maximumSize)
            val delimiterLength: `var` = delimiter.length
            var pos: `var` = 0
            while (true)
            {
                val next: `var` = text.indexOf(delimiter, pos)
                if (next == -1)
                {
                    strings.add(text.substring(pos))
                    break
                }
                else
                {
                    strings.add(text.substring(pos, next))
                }
                pos = next + delimiterLength
            }
            return strings
        }

        /**
         * Returns a string list split from the given text using a regular expression pattern
         */
        fun splitOnPattern(text: String, pattern: String): StringList
        {
            val list: `var` = StringList()
            list.addAll(text.split(pattern.toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray())
            return list
        }

        /**
         * Returns a list of strings from the given iterable
         */
        fun <T> stringList(maximumSize: Maximum?, values: Collection<T>): StringList
        {
            return StringList(maximumSize, values)
        }

        /**
         * Returns a list of strings from the given iterable
         */
        fun <T> stringList(maximumSize: Maximum?, values: Iterable<T>): StringList
        {
            val list: `var` = StringList(maximumSize)
            for (value in values)
            {
                list.addIfNotNull(value.toString())
            }
            return list
        }

        /**
         * Returns a list of strings from the given iterable
         */
        fun <T> stringList(values: Collection<T>?): StringList
        {
            return StringList(values)
        }

        /**
         * Returns a list of strings from the given iterable
         */
        fun <T> stringList(values: Iterable<T>): StringList
        {
            val list: `var` = StringList()
            for (value in values)
            {
                list.addIfNotNull(value.toString())
            }
            return list
        }

        /**
         * Returns the given list of objects with a maximum size
         */
        fun stringList(maximumSize: Maximum?, vararg strings: String?): StringList
        {
            val list: `var` = StringList(maximumSize)
            list.addAll(Arrays.asList(*strings))
            return list
        }

        /**
         * Returns the given list of objects
         */
        fun stringList(vararg strings: String?): StringList
        {
            return stringList(MAXIMUM, strings)
        }

        /**
         * Returns a list of words from the given text with word breaks occurring on whitespace
         */
        fun String.words(delimiters: String = " \t\n"): StringList
        {
            val list = StringList()
            var startOfWord = -1
            val length = length
            for (at in 0 until length)
            {
                when (this[at])
                {
                    in delimiters ->
                    {
                        if (startOfWord >= 0)
                        {
                            list.add(substring(startOfWord, at))
                            startOfWord = -1
                        }
                    }

                    else ->
                    {
                        if (startOfWord < 0)
                        {
                            startOfWord = at
                        }
                    }
                }
            }
            if (startOfWord >= 0)
            {
                list.add(substring(startOfWord))
            }
            return list
        }
    }
}