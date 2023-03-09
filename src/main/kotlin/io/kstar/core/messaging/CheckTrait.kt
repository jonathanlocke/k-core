package io.kstar.core.messaging

import com.telenav.kivakit.annotations.code.quality.TypeQuality

/**
 * Executes a block of code, handling exceptions and problem messages.
 *
 *
 *  * [.check] - Runs the given code, broadcasting the given problem message if it fails
 *  * [.check] - Runs the given code, broadcasting the given problem message if
 * the code or the given predicate fail
 *  * [.check] - Runs the given code, broadcasting the given problem message if it fails.
 * returns a boolean [Result] indicating success or failure
 *  * [.check] - Runs the given code, broadcasting a problem message if it fails.
 * returns a boolean [Result] indicating success or failure
 *
 *
 *
 * **Example**
 *
 *
 *
 * Here is a (contrived) example that handles exceptions and failure messages automatically.
 * The check method executes the code block, which returns a [Result]. If the result
 * represents failure because [Result.failed] is true, a failure [Result] is returned
 * containing a [Problem] message with the given text. If an optional post-condition [Matcher]
 * (which extends [Predicate]) fails to match (in this case the text returned by readText()),
 * the same thing occurs. If the result represents success, as determined by [Result.succeeded],
 * the result is returned unchanged.
 *
 *
 * <pre>
 * Result&lt;String&gt; read(File file)
 * {
 * return check("Could not read string", s -> !s.isBlank(), () ->
 * {
 * return success(listenTo(file).reader().readText());
 * });
 * }
</pre> *
 *
 *
 * **Example**
 *
 *
 *
 * Here, a method is called, returning a boolean result. If reading the file, or processing it broadcasts a problem,
 * the [Result] will be a failure containing the broadcast message(s).
 *
 *
 * <pre>
 * Result&lt;Boolean&gt; process(File file)
 * {
 * return check(() -> processText(listenTo(file).reader().readText()));
 * }
</pre> *
 *
 * @author Jonathan Locke
 */
@TypeQuality(stability = STABLE_EXTENSIBLE, testing = UNTESTED, documentation = DOCUMENTED)
interface CheckTrait : Repeater, ResultTrait
{
    /**
     * Executes the given code block, trapping any exceptions or failures
     *
     * @param code The code to execute, returning a [Result]
     * @param postCondition A predicate to test on the return value of the code
     * @param message The problem message to return if there's a failure
     * @return The result, signaling either one or more failures, or success with a value.
     */
    fun <T> check(message: String?, postCondition: Matcher<T>, code: Code<Result<T>?>): Result<T>?
    {
        return try
        {
            val result = code.run()
            if (result.failed())
            {
                return failure("Code failed: $", message)
            }
            if (!ok())
            {
                return failure("Code broadcast failure: $", message)
            }
            if (!postCondition.test(result.get()))
            {
                failure("Result failed post-condition: $", message)
            }
            else result
        }
        catch (e: Exception)
        {
            failure(e, "Code threw exception: $", message)
        }
    }

    /**
     * Executes the given code block, trapping any exceptions or failures
     *
     * @param code The code to execute, returning a [Result]
     * @param message The problem message to return if there's a failure
     * @return The result, signaling either one or more failures, or success with a value.
     */
    fun <T> check(message: String?, code: Code<Result<T>?>): Result<T>?
    {
        return check<Any>(message, Matcher<T> { ignored -> true }, code)
    }

    /**
     * Executes the given code block, returning a boolean for success, if no messages are broadcast
     *
     * @param code The code to execute
     * @return The boolean result
     */
    fun check(code: Runnable): Result<Boolean?>?
    {
        return check(null, code)
    }

    /**
     * Executes the given code block, trapping any exceptions or failures
     *
     * @param code The code to execute, returning a [Result]
     * @param message The problem message to return if there's a failure
     */
    fun check(message: String?, code: Runnable): Result<Boolean?>?
    {
        val result = failure(message)
        return try
        {
            result.listenTo(this)
            code.run()
            if (!ok())
            {
                val formatted = stringList("Code broadcast failure")
                    .appendingIfNotNull(message)
                    .join(": ")
                problem(formatted)
                return failure(formatted)
            }
            success(true)
        }
        catch (e: Exception)
        {
            val formatted = stringList("Code threw exception")
                .appendingIfNotNull(message)
                .join(": ")
            problem(e, formatted)
            failure(e, formatted)
        }
        finally
        {
            removeListener(result)
        }
    }
}