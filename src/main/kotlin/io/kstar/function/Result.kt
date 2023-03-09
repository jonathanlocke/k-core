////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
// © 2011-2021 Telenav, Inc.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
//
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
package io.kstar.function

import com.telenav.kivakit.annotations.code.quality.TypeQuality
import java.util.*
import java.util.function.Consumer
import java.util.function.Function

/**
 * Represents the result of an operation, capturing any failure [.messages]. If there are no failure messages,
 * the operation was successful and [.get] provides the value of the operation. Note that a [Result] is a
 * subclass of [Maybe]. The list of methods inherited from [Maybe] are duplicated here for convenience.
 *
 *
 * **Construction**
 *
 *
 *
 * A [Result] can be constructed in several ways:
 *
 *
 *  1. [.absent] - A successful [Result] with no value
 *  1. [.result] - Creates a [Result] with the given value
 *  1. [.result] - Creates a [Result] with the given value
 *  1. [.failure] - Creates a [Result] with the given failure message
 *  1. [.failure] - Creates a [Result] with the given failure message
 *
 *
 *
 * **Validation**
 *
 *
 *  * [.succeeded] - True if this result represents success
 *  * [.failed] - True if this result represents a failure
 *  * [.isValid] - True if this result is valid. A valid result must have either a value, or one or more messages, but it cannot have both.
 *  * [.messages] - Any captured error messages
 *
 *
 *
 * **Terminal Operations**
 *
 *
 *  * [.isPresent] - Returns true if a value is present
 *  * [.isAbsent] - Returns true if no value is present
 *  * [.get] - Any value that might be present, or null if no value is present
 *  * [.has] - Returns true if a value is present
 *  * [.orMaybe] - Returns this value, or the default value
 *  * [.orMaybe] - Returns this value, or the default value
 *  * [.orProblem] - Returns this value or broadcasts a problem if this value is not present
 *  * [.orThrow] - Returns this value or throws an exception
 *  * [.orThrow]- Returns this value or throws an exception
 *  * [.asStream] - Converts this value to a stream with zero or one element(s)
 *
 *
 *
 * **Functions**
 *
 *
 *  * [.then] - Returns the result of applying the given function to this value
 *  * [.map] - Returns the result of applying the given two-argument function to this value and the given argument
 *  * [.map] - Returns the result of applying the given three-argument function to this value and the given arguments
 *  * [.map] - Returns the result of applying the given four-argument function to this value and the given arguments
 *  * [.map] - Returns the result of applying the given five-argument function to this value and the given arguments
 *  * [.apply] - Applies the given function to this value
 *  * [.map] - Applies the given function to this value
 *
 *
 *
 * **Conditionals**
 *
 *
 *  * [.presentIf] - Applies the given function to this value, returning this value if it is true, or [.absent] if it is false
 *  * [.ifPresent] - Calls the given consumer if a value is present
 *  * [.ifPresentOr] - Calls the given consumer if a value is present, otherwise calls the given code
 *  * [.or] - If a value is present, returns this value, otherwise returns the [Maybe] supplied by the given [Code]
 *
 *
 * @author jonathanl (shibo)
 * @see Maybe
 *
 * @see Consumer
 *
 * @see Broadcaster
 *
 * @see BiFunction
 *
 * @see Function
 *
 * @see Code
 *
 * @see Source
 */
@Suppress("unused")
@UmlClassDiagram(diagram = DiagramMessaging::class)
@UmlRelation(label = "failure reason", referent = Message::class)
@TypeQuality(stability = STABLE_EXTENSIBLE, testing = TESTING_INSUFFICIENT, documentation = DOCUMENTED)
class Result<Value> : Maybe<Value?>, RepeaterMixin
{
    /** Any broadcaster to listen to  */
    private var broadcaster: Broadcaster? = null

    /** Any messages this result has captured  */
    private val messages: MessageList? = MessageList()

    protected constructor()
    {
        messages.listenTo(this)
    }

    protected constructor(maybe: Maybe<Value>) : super(maybe)
    {
        messages.listenTo(this)
    }

    protected constructor(value: Value) : super(value)
    {
        messages.listenTo(this)
    }

    protected constructor(broadcaster: Broadcaster?)
    {
        messages.listenTo(this)
        listenTo(broadcaster)
        this.broadcaster = broadcaster
    }

    /**
     *
     * *Down-casting override*
     *
     *
     * {@inheritDoc}
     */
    override fun <Mapped> apply(function: Function<in Value?, out Maybe<out Mapped?>?>): Result<Mapped?>
    {
        return super.apply(function) as Result<Mapped?>
    }

    /**
     * If a value is present, converts it to a string and then applies the given [Parsable] class to convert it to
     * a value. The [Parsable] must have a public constructor that takes a [Listener]. String converters in
     * the *kivakit-converter* mini-framework are such [Parsable]s.
     *
     * @param mapperType The [Parsable] class
     * @return A [Maybe] object with the mapped value, or [.absent] if the mapping failed
     */
    fun <Output, Mapper : Parsable<out Output>?> convert(mapperType: Class<Mapper>?): Maybe<Output>
    {
        val outer = this
        return tryCatchDefault({
            if (isPresent)
            {
                val mapper = newInstance(mapperType, Listener::class.java, outer)
                return@tryCatchDefault newMaybe(ensureNotNull(mapper).parse(get().toString()))
            }
            else
            {
                return@tryCatchDefault newAbsent()
            }
        }, newAbsent())
    }

    /**
     * {@inheritDoc}
     */
    override fun equals(`object`: Any): Boolean
    {
        return if (`object` is Result<*>)
        {
            super.equals(`object`) && succeeded() == `object`.succeeded()
        }
        else false
    }

    /**
     * Returns true if this result represents a failure
     */
    fun failed(): Boolean
    {
        return messages != null && messages().failed()
    }

    /**
     * {@inheritDoc}
     */
    override fun hashCode(): Int
    {
        return Objects.hash(super.hashCode(), succeeded())
    }

    /**
     *
     * *Down-casting override*
     *
     *
     * {@inheritDoc}
     */
    override fun ifPresent(consumer: Consumer<Value?>): Result<Value?>
    {
        return super.ifPresent(consumer) as Result<Value?>
    }

    /**
     *
     * *Down-casting override*
     *
     *
     * {@inheritDoc}
     */
    override fun ifPresentOr(consumer: Consumer<Value?>, runnable: UncheckedVoidCode?): Result<Value?>
    {
        return super.ifPresentOr(consumer, runnable) as Result<Value?>
    }

    override val isValid: Boolean
        /**
         * Returns true if this result is valid. A result is invalid if it has a value present, but also has failure
         * messages. Note that a result can have neither a value nor any failure messages (see [.absent]).
         */
        get()
        {
            val invalid = isPresent && failed()
            return !invalid
        }

    /**
     *
     * *Down-casting override*
     *
     *
     * {@inheritDoc}
     */
    override fun <Output> map(mapper: Function<in Value?, out Output?>?): Result<Output?>
    {
        return super.map(mapper) as Result<Output?>
    }

    /**
     *
     * *Down-casting override*
     *
     *
     * {@inheritDoc}
     */
    override fun <Argument2, R> map(function: BiFunction<Value?, Argument2, R>, argument2: Argument2): Result<R?>
    {
        return super.map<Argument2, R>(function, argument2) as Result<R?>
    }

    /**
     *
     * *Down-casting override*
     *
     *
     * {@inheritDoc}
     */
    override fun <Argument2, Argument3, ResultType> map(
        function: TriFunction<Value?, Argument2?, Argument3?, ResultType?>, argument2: Argument2?,
        argument3: Argument3?): Result<ResultType?>
    {
        return super.map(function, argument2, argument3) as Result<ResultType?>
    }

    /**
     *
     * *Down-casting override*
     *
     *
     * {@inheritDoc}
     */
    override fun <Argument2, Argument3, Argument4, ResultType> map(
        function: TetraFunction<Value?, Argument2?, Argument3?, Argument4?, ResultType?>, argument2: Argument2?,
        argument3: Argument3?, argument4: Argument4?): Result<ResultType?>
    {
        return super.map(function, argument2, argument3, argument4) as Result<ResultType?>
    }

    /**
     *
     * *Down-casting override*
     *
     *
     * {@inheritDoc}
     */
    override fun <Argument2, Argument3, Argument4, Argument5, ResultType> map(
        function: PentaFunction<Value?, Argument2?, Argument3?, Argument4?, Argument5?, ResultType?>,
        argument2: Argument2?,
        argument3: Argument3?, argument4: Argument4?, argument5: Argument5?): Result<ResultType?>
    {
        return super.map(function, argument2, argument3, argument4, argument5) as Result<ResultType?>
    }

    /**
     * Returns any messages captured in this result
     */
    fun messages(): MessageList
    {
        // If we were not constructed with a Broadcaster,
        return if (messages == null)
        {
            // return an empty message list.
            emptyMessageList()
        }
        else messages
    }

    /**
     * If this result failed, runs the given [Code], capturing any messages and result of the call. If the code
     * succeeds, returns the result, otherwise returns this result.
     *
     * @return The [Result] of the call
     */
    fun or(code: Code<Value>): Result<Value>
    {
        // If this result failed,
        if (failed())
        {
            // don't listen to the broadcaster further,
            broadcaster.removeListener(this)

            // create a new result to listens to it,
            val result = Result<Value>(broadcaster)
            try
            {
                // call the code and store any result,
                result.set(code.run())
            }
            catch (e: Exception)
            {
                // and if the code throws an exception, store that as a problem.
                problem(e, "Failed")
            }
            return result
        }
        return this
    }

    /**
     *
     * *Down-casting override*
     *
     *
     * {@inheritDoc}
     */
    override fun orMaybe(value: Value?): Result<Value?>
    {
        return super.orMaybe(value) as Result<Value?>
    }

    /**
     *
     * *Down-casting override*
     *
     *
     * {@inheritDoc}
     */
    override fun orMaybe(source: Source<Value?>?): Result<Value?>
    {
        return super.orMaybe(source) as Result<Value?>
    }

    /**
     * Broadcasts a problem if no value is present
     *
     * @return Any value that might be present
     */
    fun orProblem(message: String?, vararg arguments: Any?): Value?
    {
        if (isAbsent)
        {
            problem(message, arguments)
        }
        return get()
    }

    /**
     *
     * *Down-casting override*
     *
     *
     * {@inheritDoc}
     */
    override fun presentIf(predicate: Function<Value?, Boolean?>?): Result<Value?>
    {
        return super.presentIf(predicate) as Result<Value?>
    }

    /**
     * Returns true if this result represents success
     */
    fun succeeded(): Boolean
    {
        return messages == null || messages().succeeded()
    }

    /**
     *
     * *Down-casting override*
     *
     *
     * {@inheritDoc}
     */
    override fun then(function: Function<Value?, Value?>?): Result<Value?>
    {
        return super.then(function) as Result<Value?>
    }

    override fun toString(): String
    {
        return if (succeeded()) "Succeeded: " + get()
        else """
     Failed:
     
     ${messages().bulleted(4)}
     """.trimIndent()
    }

    /**
     *
     * *Down-casting override*
     *
     *
     * {@inheritDoc}
     */
    override fun <T> newAbsent(): Maybe<T>
    {
        return RESULT_ABSENT as Result<T>
    }

    /**
     *
     * *Down-casting override*
     *
     *
     * {@inheritDoc}
     */
    override fun <T> newMaybe(value: T?): Result<T?>
    {
        return Result(value)
    }

    companion object
    {
        /**
         * A [Result] with no value
         */
        private val RESULT_ABSENT: Result<*> = Result<Any>()

        /**
         * Returns a [Result] whose value is absent
         */
        fun <Value> absent(): Result<Value>
        {
            return RESULT_ABSENT as Result<Value>
        }

        /**
         * Returns a failure result with the given [Problem]
         */
        fun <T> failure(cause: Throwable?, message: String?, vararg arguments: Any?): Result<T>
        {
            return failure(Problem(cause, message, arguments))
        }

        /**
         * Returns a failure result with the given [Problem]
         */
        fun <T> failure(message: String?, vararg arguments: Any?): Result<T>
        {
            return failure(Problem(message, arguments))
        }

        /**
         * Returns a failure result with the given [Problem]
         */
        fun <T> failure(result: Result<T>, message: String, vararg arguments: Any?): Result<T>
        {
            return failure(Problem(result.toString() + " => " + message, arguments))
        }

        /**
         * Returns a failure result with the given message
         */
        fun <T> failure(message: Message?): Result<T>
        {
            val result = Result<T>()
            result.receive(message)
            return result
        }

        /**
         * Returns a failure result with the given value
         */
        fun <T> failure(value: T, message: String?, vararg arguments: Any?): Result<T>
        {
            val result = Result(value)
            result.receive(Problem(message, arguments))
            return result
        }

        /**
         * Returns the result of executing the given [UncheckedCode]. Captures any value, or any failure messages
         * broadcast by the code during the call, and returns a [Result].
         *
         * @return The [Result] of the call
         */
        fun <T> result(code: UncheckedCode<T>): Result<T>
        {
            val result = Result<T>(code)
            try
            {
                result.set(code.run())
            }
            catch (e: Exception)
            {
                result.problem(e, "Operation failed with exception")
            }
            return result
        }

        /**
         * Returns the result of executing the given [Code]. Captures any value, or any failure messages broadcast by
         * the given broadcaster during the call, and returns a [Result].
         *
         * @return The [Result] of the call
         */
        fun <T> result(broadcaster: Broadcaster?, code: Code<T>): Result<T>
        {
            // Create an empty result that captures messages from this object,
            val result = Result<T>(broadcaster)
            try
            {
                // call the code and store any result,
                result.set(code.run())
            }
            catch (e: Exception)
            {
                // and if the code throws an exception, store that as a problem.
                result.problem(e, "Operation failed with exception")
            }

            // Return the result of the method call.
            return result
        }

        /**
         * Returns a [Result] for a value that may or may not be present
         */
        fun <Value> result(value: Maybe<Value>?): Result<Value>
        {
            return Result(value)
        }

        /**
         * Returns a result with the given value
         */
        fun <T> result(value: T): Result<T>
        {
            return Result(value)
        }
    }
}