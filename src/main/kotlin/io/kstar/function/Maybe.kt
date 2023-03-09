package io.kstar.function

import io.kstar.annotations.quality.Documentation.DOCUMENTED
import io.kstar.annotations.quality.Stability.STABLE_EXTENSIBLE
import io.kstar.annotations.quality.Testing.TESTING_INSUFFICIENT
import io.kstar.annotations.quality.TypeQuality
import io.kstar.receptors.objects.Presence
import java.util.*
import java.util.function.Consumer
import java.util.function.Function
import java.util.stream.Stream

/**
 * A substitute for [Optional] that adds functionality and integrates with [Repeater]
 *
 *
 * **Creation**
 *
 *
 *
 * A [Maybe] can be constructed in three ways:
 *
 *
 *  1. [.absent] - Creates a [Maybe] whose value is missing
 *  1. [.maybe] - Creates a [Maybe] whose value can be missing or not
 *  1. [.present] - Creates a [Maybe] whose value is always non-null
 *
 *
 *
 * **Terminal Operations**
 *
 *
 *  * [.isPresent] - Returns true if a value is present and it is not the boolean value *false*
 *  * [.isAbsent] - Returns true if no value is present
 *  * [.get] - Any value that might be present, or null if no value is present
 *  * [.has] - Returns true if a value is present
 *  * [.orMaybe] - Returns this value, or the default maybe value
 *  * [.orDefaultTo] - Returns this value, or the default value
 *  * [.orDefaultTo] - Returns this value, or the default value
 *  * [.orThrow] - Returns this value or throws an exception
 *  * [.orThrow]- Returns this value or throws an exception
 *  * [.asStream] - Converts this value to a stream with zero or one element(s)
 *
 *
 *
 * **Functions**
 *
 *
 *  * [.apply] - Applies the given function to this value
 *  * [.then] - Returns the result of applying the given function to this value
 *  * [.map] - Returns the result of applying the given two-argument function to this value and the given argument
 *  * [.map] - Returns the result of applying the given three-argument function to this value and the given arguments
 *  * [.map] - Returns the result of applying the given four-argument function to this value and the given arguments
 *  * [.map] - Returns the result of applying the given five-argument function to this value and the given arguments
 *  * [.map] - Applies the given function to this value
 *  * [.mapWithRetries] - Retries the given source up to the given maximum number of times or until source returns a non-null value
 *
 *
 *
 * **Conditionals**
 *
 *
 *  * [.absentIf] - Applies the given function to this value, returning this value if it is true, or [.absent] if it is false
 *  * [.presentIf] - Applies the given function to this value, returning this value if it is true, or [.absent] if it is false
 *  * [.ifPresent] - Calls the given consumer if a value is present
 *  * [.ifPresentOr] - Calls the given consumer if a value is present, otherwise calls the given code
 *
 *
 *
 * **Example - Absent Value**
 *
 * <pre>
 * ensureEqual(present("abc")            // "abc"
 * .map(Integer::parseInt)           // null
 * .map(Integer::sum, 123)           // null
 * .map(String::valueOf)             // null
 * .map(String::concat, "xyz")       // null
 * .map(String::substring, 2, 4)     // null
 * .get(), null);
</pre> *
 *
 *
 * **Example - Present Value**
 *
 * <pre>
 * ensureEqual(present("123")        // "123"
 * .map(Integer::parseInt)       // 123
 * .map(Integer::sum, 123)       // 246
 * .map(String::valueOf)         // "246"
 * .map(String::concat, "xyz")   // "246xyz"
 * .map(String::substring, 2, 4) // "6x"
 * .get(), "6x");
</pre> *
 *
 *
 * **Example - Asynchronous Mapping**
 *
 * <pre>
 * var sum = result(3).mapTask(value -&gt; new FutureTask&lt;&gt;(() &gt; value + 3)).get().get();
</pre> *
 *
 * @author jonathanl (shibo)
 * @author viniciusluisr
 * @see [improved-optional](https://github.com/viniciusluisr/improved-optional)
 */
@Suppress("unused")
@TypeQuality
(
    stability = STABLE_EXTENSIBLE,
    testing = TESTING_INSUFFICIENT,
    documentation = DOCUMENTED
)
open class Maybe<Value> : Presence, TryCatchTrait, Source<Value>
{
    /** The null or non-null value  */
    private var value: Value?

    protected constructor(value: Value)
    {
        this.value = value
    }

    protected constructor(value: Maybe<Value>)
    {
        this.value = value.get()
    }

    protected constructor()
    {
        value = null
    }

    /**
     * If this value is not null and the given predicate evaluates to true for the value, returns [.absent],
     * otherwise returns this value.
     *
     * @param predicate The predicate to test any non-null value
     * @return This value or a null value
     */
    fun absentIf(predicate: Function<Value, Boolean?>?): Maybe<Value>
    {
        return tryCatch { if (value != null && ensureNotNull(predicate).apply(value)) newAbsent() else this }
    }

    /**
     *
     *
     * If a value is present, uses the given function to map the value from Value to Maybe&lt;ResultType&gt;. If no
     * value is present, returns [.absent]. The effect is that of allowing functions to be chained together in a
     * nested structure where each nested function introduces a new lambda variable that is visible to inner functions.
     * For example:
     *
     *
     * <pre>
     * var seven = present(7);
     * var three = present(3);
     *
     * var sum = seven.apply(a ->
     * three.apply(b ->
     * present(a + b)))
     * .get();</pre>
     *
     *
     *
     * In this example, if either *seven* or *three* had no value present (or both values were absent), the
     * statement would return [.absent]. Only if both values are present will the statement result in the sum of
     * the two values. *This allows us to stop thinking about the special case where one or both values are
     * missing.*
     *
     *
     *
     * **Monadic Terminology**
     *
     *
     *
     * Note that this method is sometimes referred to as *flatMap*. However, the name 'flatMap' does not accurately
     * describe what this method does, as it only describes what happens when values are collections.
     *
     *
     *
     * **Important Note**
     *
     *
     *
     * Notice that the first sentence of the description for this method is exactly the same as that provided for
     * [.map]. The difference between the two methods is that the function passed to this method
     * produces Maybe&lt;ResultType&gt; directly (which allows function nesting), while the function passed to
     * [.map] just maps the value to the ResultType type, which is then wrapped in a [Maybe].
     *
     *
     * @param function A function mapping from Value to Maybe&lt;ResultType&gt;
     * @param <Output> The type being mapped to
     * @return The mapped value as a [Maybe] object, or [.absent] if no value was present
    </Output> */
    open fun <Output> apply(function: Function<in Value?, out Maybe<out Output?>?>): Maybe<Output?>?
    {
        return tryCatchDefault({ if (isPresent) ensureNotNull(function.apply(value) as Maybe<Output?>?) else newAbsent() }, newAbsent())
    }

    /**
     * If this value is not null, returns the value as a [Stream], otherwise returns an empty [Stream].
     *
     * @return This value as a [Stream]
     */
    fun asStream(): Stream<Value?>
    {
        return if (isPresent) Stream.of(value) else Stream.empty()
    }

    override fun equals(`object`: Any): Boolean
    {
        return if (`object` is Maybe<*>)
        {
            value == `object`.value
        }
        else false
    }

    /**
     * Returns any value that might be present, or null if there is none
     */
    fun get(): Value?
    {
        return value
    }

    /**
     * Returns true if a value is present
     */
    fun has(): Boolean
    {
        return isPresent
    }

    override fun hashCode(): Int
    {
        return Objects.hashCode(value)
    }

    /**
     * If this value is not null, calls the given consumer with the value
     *
     * @param consumer The consumer for any non-null value
     * @return This [Maybe] for chaining
     */
    open fun ifPresent(consumer: Consumer<Value?>): Maybe<Value>?
    {
        if (isPresent)
        {
            tryCatch { consumer.accept(value) }
        }
        return this
    }

    /**
     * If a value is present, calls the given consumer, otherwise runs the given block of code
     *
     * @param consumer The consumer to call
     * @param runnable The code to run
     * @return This value for chaining
     */
    open fun ifPresentOr(consumer: Consumer<Value?>, runnable: UncheckedVoidCode?): Maybe<Value>?
    {
        if (isPresent)
        {
            tryCatch { consumer.accept(value) }
        }
        else
        {
            tryCatch(runnable)
        }
        return this
    }

    val isAbsent: Boolean
        /**
         * Returns true if there is no value present
         */
        get() = value == null
    val isPresent: Boolean
        /**
         * Returns true if there is a value present, and it is not [Boolean.FALSE]. The check for false allows monads
         * to be treated as if they were null.
         */
        get() = if (value is Boolean)
        {
            value as Boolean
        }
        else value != null
    open val isValid: Boolean
        /**
         * Returns true if this object is valid
         */
        get() = true

    /**
     * If a value is present, uses the given function to map the value from Value to ResultType. The mapped value is
     * then wrapped in a Maybe&lt;ResultType&gt; object and returned. If no value is present, returns [.absent].
     * The effect is that of simply mapping this optional value to a new type.
     *
     *
     * **Important Note**
     *
     *
     *
     * Notice that the first sentence of the description for this method is exactly the same as that provided for
     * [.apply]. The difference between the two methods is that the function passed to
     * [.apply] produces Maybe&lt;ResultType&gt; directly (which allows function nesting), while the
     * function passed to this method just maps the value to the ResultType type, which is then wrapped in a
     * [Maybe].
     *
     *
     * @param mapper A function mapping from Value to ResultType
     * @param <ResultType> The type that Value is being mapped to
     * @return The mapped value or [.absent]
    </ResultType> */
    open fun <ResultType> map(
        mapper: Function<in Value, out ResultType?>?): Maybe<ResultType?>?
    {
        return tryCatchDefault({ if (isPresent) newMaybe(ensureNotNull(mapper).apply(value)) else newAbsent() }, newAbsent())
    }

    /**
     * If a value is present and the given value is not null, applies the given bi-function to produce a result of a
     * different type.
     *
     * @param function The bi-function, f(Value, Argument2) -> ResultType
     * @param argument2 The second argument to pass to the bi-function, along with this value as the first argument
     * @return The combination of this value and the given argument, if both values are non-null, otherwise, returns
     * [.absent].
     */
    open fun <Argument2, ResultType> map(
        function: BiFunction<Value, Argument2, ResultType>,
        argument2: Argument2): Maybe<ResultType?>?
    {
        return if (isPresent && value != null)
        {
            tryCatch { newMaybe(function.apply(value, argument2)) }
        }
        else newAbsent()
    }

    /**
     * If a value is present, and the given arguments are non-null, applies the given tri-function to produce a result
     * of a different type.
     *
     * @param function The tri-function, f(Value, Argument2, Argument3) -> ResultType
     * @param argument2 The second argument to pass to the tri-function, along with this value as the first argument
     * @param argument3 The third argument to pass to the tri-function
     * @return The combination of this value and the given arguments, if all arguments are non-null, otherwise, returns
     * [.absent].
     */
    open fun <Argument2, Argument3, ResultType> map(
        function: TriFunction<Value, Argument2?, Argument3?, ResultType?>,
        argument2: Argument2?,
        argument3: Argument3?): Maybe<ResultType?>?
    {
        return if (isPresent && argument2 != null && argument3 != null)
        {
            tryCatch { newMaybe(function.apply(value, argument2, argument3)) }
        }
        else newAbsent()
    }

    /**
     * If a value is present, and the given value is also present, applies the given tri-function to produce a result of
     * a different type.
     *
     * @param function The combining tetra-function, f(Value, Argument2, Argument3, Argument4) -> ResultType
     * @param argument2 The second argument to pass to the quad-function, along with this value as the first argument
     * @param argument3 The third argument to pass to the quad-function along with this value
     * @param argument4 The fourth argument to pass to the quad-function along with this value
     * @return The combination of this value and the given arguments, if all arguments are non-null, otherwise, returns
     * [.absent].
     */
    open fun <Argument2, Argument3, Argument4, ResultType> map(
        function: TetraFunction<Value, Argument2?, Argument3?, Argument4?, ResultType?>,
        argument2: Argument2?,
        argument3: Argument3?,
        argument4: Argument4?): Maybe<ResultType?>?
    {
        return if (isPresent && argument2 != null && argument3 != null && argument4 != null)
        {
            tryCatch { newMaybe(function.apply(value, argument2, argument3, argument4)) }
        }
        else newAbsent()
    }

    /**
     * If a value is present, and the given value is also present, applies the given five-argument function to produce a
     * result of a different type.
     *
     * @param function The five-argument function, f(Value, Argument2, Argument3, Argument4, Argument5) -> ResultType
     * @param argument2 The second argument to pass to the quad-function, along with this value as the first argument
     * @param argument3 The third argument to pass to the quad-function along with this value
     * @param argument4 The fourth argument to pass to the quad-function along with this value
     * @param argument5 The fifth argument to pass to the quad-function along with this value
     * @return The combination of this value and the given arguments, if all arguments are non-null, otherwise, returns
     * [.absent].
     */
    open fun <Argument2, Argument3, Argument4, Argument5, ResultType> map(
        function: PentaFunction<Value, Argument2?, Argument3?, Argument4?, Argument5?, ResultType?>,
        argument2: Argument2?,
        argument3: Argument3?,
        argument4: Argument4?,
        argument5: Argument5?): Maybe<ResultType?>?
    {
        return if (isPresent && argument2 != null && argument3 != null && argument4 != null && argument5 != null)
        {
            tryCatch { newMaybe(function.apply(value, argument2, argument3, argument4, argument5)) }
        }
        else newAbsent()
    }

    /**
     * If a value is present, uses the given function to map the value from Value to ResultType, where ResultType is a
     * subclass of [Future]. The mapped value is then wrapped in a Maybe&lt;ResultType&gt; object and returned. If
     * no value is present, returns [.absent]. The effect is that of simply mapping this optional value to a new
     * type.
     *
     * @param mapper A function mapping from Value to ResultType
     * @param <ResultType> The type that Value is being mapped to
     * @return The mapped value or [.absent]
    </ResultType> */
    fun <To, ResultType : Future<To>?> mapTask(
        mapper: Function<in Value, out ResultType>?): Maybe<ResultType>
    {
        return tryCatchDefault({ if (isPresent) newMaybe(ensureNotNull(mapper).apply(value)) else newAbsent() }, newAbsent())
    }

    /**
     * If a value is present, retries the given value mapper up to the given maximum number of times or until the mapper
     * returns a non-null value. If no mapped value can be produced, returns [.absent].
     *
     * @param retries The number of times to retry
     * @param mapper The mapping function
     * @return This value or the value produced by the given source
     */
    fun <ResultType> mapWithRetries(retries: Count,
                                    mapper: Function<in Value, out ResultType>?): Maybe<ResultType>
    {
        if (isPresent)
        {
            retries.loop {
                val maybe: Maybe<ResultType> = tryCatch {
                    val mapped = ensureNotNull(mapper).apply(value)
                    if (mapped != null)
                    {
                        return@tryCatch newMaybe(mapped)
                    }
                    absent()
                }
            }
        }
        return absent()
    }

    /**
     * If there is a value present, returns it, otherwise returns the given default value
     *
     * @param defaultValue The default value to return if there is no value
     * @return The value
     */
    fun orDefaultTo(defaultValue: Value): Value
    {
        return tryCatch { if (isPresent) value else defaultValue }
    }

    /**
     * If there is a value present, returns it, otherwise returns the given default value
     *
     * @param defaultValue The default value to return if there is no value
     * @return The value
     */
    fun orDefaultTo(defaultValue: Source<Value>): Value
    {
        return tryCatch { if (isPresent) value else defaultValue.get() }
    }

    /**
     * If there is a value present, returns this, otherwise returns the value provided by the given [Source]
     *
     * @param source A source of a value, if there is no value present
     * @return This value or the value produced by the given source
     */
    open fun orMaybe(source: Source<Value>?): Maybe<Value>
    {
        return tryCatch { if (isPresent) this else newMaybe(ensureNotNull(source).get()) }
    }

    /**
     * If there is a value present, returns this, otherwise returns the value provided by the given [Source]
     *
     * @param value A source of a [Maybe] if there is no value present
     * @return This value or the value produced by the given source
     */
    open fun orMaybe(value: Value): Maybe<Value>
    {
        return tryCatch { if (isPresent) this else newMaybe(value) }
    }

    /**
     * Throws an exception if there is no value, otherwise returns the value
     *
     * @return The value
     */
    fun orThrow(): Value?
    {
        return orThrow("No value present")
    }

    /**
     * Throws an exception if there is no value, otherwise returns the value
     *
     * @param message The exception message
     * @param arguments The message arguments
     * @return The value
     */
    fun orThrow(message: String?, vararg arguments: Any?): Value?
    {
        if (isAbsent)
        {
            Problem(message, arguments).throwMessage()
        }
        return value
    }

    /**
     * If this value is not null and the given predicate evaluates to true for the value, returns this value, otherwise
     * returns the [.absent] value.
     *
     * @param predicate The predicate to test any non-null value
     * @return This value or a null value
     */
    open fun presentIf(predicate: Function<Value, Boolean?>?): Maybe<Value>?
    {
        return tryCatch { if (value != null && ensureNotNull(predicate).apply(value)) this else newAbsent() }
    }

    /**
     * Sets the given result value
     */
    fun set(value: Value): Maybe<Value>
    {
        this.value = value
        return this
    }

    /**
     * If a value is present, and the given value is also present, applies the given five-argument function to produce a
     * new value of the same type.
     *
     * @param function The combining five-argument function, f(Value, Argument2, Argument3, Argument4, Argument5) ->
     * Value
     * @param argument2 The second argument to pass to the quad-function, along with this value as the first argument
     * @param argument3 The third argument to pass to the quad-function along with this value
     * @param argument4 The fourth argument to pass to the quad-function along with this value
     * @param argument5 The fifth argument to pass to the quad-function along with this value
     * @return The combination of this value and the given arguments, if all arguments are non-null, otherwise, returns
     * [.absent].
     */
    fun <Argument2, Argument3, Argument4, Argument5> then(
        function: PentaFunction<Value, Argument2, Argument3, Argument4, Argument5, Value>,
        argument2: Argument2?,
        argument3: Argument3?,
        argument4: Argument4?,
        argument5: Argument5?): Maybe<Value>?
    {
        return if (isPresent && argument2 != null && argument3 != null && argument4 != null && argument5 != null)
        {
            tryCatch { newMaybe(function.apply(value, argument2, argument3, argument4, argument5)) }
        }
        else newAbsent()
    }

    /**
     * If a value is present, and the given value is also present, applies the given tri-function to produce a new value
     * of the same type.
     *
     * @param function The combining tetra-function, f(Value, Argument2, Argument3, Argument4) -> ResultType
     * @param argument2 The second argument to pass to the quad-function, along with this value as the first argument
     * @param argument3 The third argument to pass to the quad-function along with this value
     * @param argument4 The fourth argument to pass to the quad-function along with this value
     * @return The combination of this value and the given arguments. if all arguments are non-null, otherwise, returns
     * [.absent].
     */
    fun <Argument2, Argument3, Argument4> then(
        function: TetraFunction<Value, Argument2, Argument3, Argument4, Value>,
        argument2: Argument2?,
        argument3: Argument3?,
        argument4: Argument4?): Maybe<Value>?
    {
        return if (isPresent && argument2 != null && argument3 != null && argument4 != null)
        {
            tryCatch { newMaybe(function.apply(value, argument2, argument3, argument4)) }
        }
        else newAbsent()
    }

    /**
     * If a value is present, and the given arguments are non-null, applies the given tri-function to produce a result
     * of the same type.
     *
     * @param function The combining tri-function, f(Value, Argument2, Argument3) -> ResultType
     * @param argument2 The second argument to pass to the tri-function, along with this value as the first argument
     * @param argument3 The third argument to pass to the tri-function
     * @return The combination of this value and the given arguments, if all arguments are non-null, otherwise, returns
     * [.absent].
     */
    fun <Argument2, Argument3> then(
        function: TriFunction<Value, Argument2, Argument3, Value>,
        argument2: Argument2?,
        argument3: Argument3?): Maybe<Value>?
    {
        return if (isPresent && argument2 != null && argument3 != null)
        {
            tryCatch { newMaybe(function.apply(value, argument2, argument3)) }
        }
        else newAbsent()
    }

    /**
     * If a value is present and the given value is not null, applies the given bi-function to produce a result of the
     * same type.
     *
     * @param function The combining bi-function, f(Value, Argument2) -> Value
     * @param argument2 The second argument to pass to the bi-function, along with this value as the first argument
     * @return The combination of this value and the given argument, if both values are non-null, otherwise, returns
     * [.absent].
     */
    fun <Argument2> then(
        function: BiFunction<Value, Argument2, Value>,
        argument2: Argument2): Maybe<Value>?
    {
        return if (isPresent && value != null)
        {
            tryCatch { newMaybe(function.apply(value, argument2)) }
        }
        else newAbsent()
    }

    /**
     * If a value is present, applies the given function to produce a new value of the same type.
     *
     * @param function The function to apply
     * @return The value produced by the given function when applied to this value
     */
    open fun then(function: Function<Value, Value>?): Maybe<Value>?
    {
        return map<Value>(function)
    }

    override fun toString(): String
    {
        return "[Maybe value = $value]"
    }

    /**
     * Overridden in [Result] to return the right subclass
     */
    protected open fun <T> newAbsent(): Maybe<T>?
    {
        return Maybe()
    }

    /**
     * Overridden in [Result] to return the right subclass
     */
    protected open fun <T> newMaybe(value: T?): Maybe<T?>?
    {
        return Maybe(value)
    }

    companion object
    {
        /**
         * Returns maybe value for null
         */
        fun <Value> absent(): Maybe<Value>
        {
            return Maybe()
        }

        /**
         * Returns maybe for the given (null or non-null) value
         */
        fun <Value> maybe(value: Value?): Maybe<Value>
        {
            return if (value == null) absent() else Maybe<Any?>(ensureNotNull(value))
        }

        /**
         * Returns maybe for the given non-null value
         */
        fun <Value> present(value: Value): Maybe<Value?>
        {
            return Maybe<Any?>(ensureNotNull(value))
        }
    }
}