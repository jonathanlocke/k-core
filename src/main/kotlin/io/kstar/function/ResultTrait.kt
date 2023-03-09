package io.kstar.function

import com.telenav.kivakit.annotations.code.quality.TypeQuality
import java.util.*
import java.util.function.Predicate

/**
 * Contains methods to facilitate working with [Result] monads.
 *
 *
 * **Execution**
 *
 *
 *  * [.run] - Runs the given code and returns a [Result]. If the code failed, the result it will contain one or more [Message]s
 *
 *
 *
 * **Results**
 *
 *
 *  * [.absent] - Returns a [Result] that represents a missing value
 *  * [.success] - Returns a [Result] for the given value
 *  * [.success] - Returns a [Result] for the given value
 *  * [.failure] - Creates a [Result] with the given failure message
 *  * [.failure] - Creates a [Result] with the given failure message
 *  * [.failure] - Creates a [Result] with the given failure message
 *
 *
 * @author jonathanl (shibo)
 * @see Maybe
 *
 * @see Result
 *
 * @see Code
 */
@Suppress("unused")
@TypeQuality(stability = STABLE_EXTENSIBLE, documentation = DOCUMENTED, testing = UNTESTED)
interface ResultTrait : Repeater
{
    /**
     * Returns a [Result] with no value
     */
    fun <T> absent(): Result<T>?
    {
        return Result.Companion.absent<T>()
    }

    /**
     * Returns a [Result] with the given [Problem] message
     *
     * @param text The message to format
     * @param arguments The arguments
     * @return The result object
     */
    fun <T> failure(text: String?, vararg arguments: Any?): Result<T>?
    {
        return listenTo(Result.Companion.failure(text, *arguments))
    }

    /**
     * Returns a [Result] with the given message
     *
     * @return The result object
     */
    fun <T> failure(message: Message?): Result<T>?
    {
        return listenTo(Result.Companion.failure(message))
    }

    /**
     * Returns a [Result] with the given [Problem] message
     *
     * @param text The message to format
     * @param arguments The arguments
     * @return The result object
     */
    fun <T> failure(throwable: Throwable?, text: String?, vararg arguments: Any?): Result<T>?
    {
        return listenTo(Result.Companion.failure(throwable, text, *arguments))
    }

    /**
     * If the given value is valid when tested by the given predicate, a success [Result] is returned. If the
     * value is not valid, a failure [Result] is returned with the given formatted message.
     *
     * @param value The value to check
     * @param valid The validity predicate
     * @param text The failure message
     * @param arguments Arguments when formatting a failure
     * @return The [Result]
     */
    fun <T> result(value: T, valid: Predicate<T>, text: String?, vararg arguments: Any?): Result<T>?
    {
        return if (valid.test(value)) success(value) else failure(text, *arguments)
    }

    /**
     * If the given value is non-null, a success [Result] is returned. If the value is not valid, a failure
     * [Result] is returned with the given formatted message.
     *
     * @param value The value to check
     * @return The [Result]
     */
    fun <T> result(value: T): Result<T>?
    {
        return result(value, { obj: T -> Objects.nonNull(obj) }, "Value $ is not valid", value)
    }

    /**
     * If the given value is valid when tested by the given predicate, a success [Result] is returned. If the
     * value is not valid, a failure [Result] is returned with the given formatted message.
     *
     * @param value The value to check
     * @param valid The validity predicate
     * @return The [Result]
     */
    fun <T> result(value: T, valid: Predicate<T>): Result<T>?
    {
        return result(value, valid, "Value $ is not valid", value)
    }

    /**
     * Returns the result of executing the given [Code]. Captures any value, or any failure messages broadcast by
     * this object during the call, and returns a [Result].
     *
     * @param code The code to run
     * @return The [Result] of the call
     */
    fun <T> run(code: Code<T>): Result<T>?
    {
        return Result.Companion.result(this, code)
    }

    /**
     * Returns a [Result] for the given optional value
     *
     * @param value The optional value
     * @return The result object
     */
    fun <T> success(value: Maybe<T>?): Result<T>?
    {
        return listenTo(Result.Companion.result(value))
    }

    /**
     * Returns a [Result] for the given value
     *
     * @param value The value
     */
    fun <T> success(value: T): Result<T>?
    {
        return listenTo(Result.Companion.result(value))
    }
}