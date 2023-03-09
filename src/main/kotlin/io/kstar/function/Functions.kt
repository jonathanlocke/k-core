@file:Suppress("unused", "UNUSED_PARAMETER", "MemberVisibilityCanBePrivate")

package io.kstar.function

import io.kstar.annotations.quality.Documentation.*
import io.kstar.annotations.quality.Stability.STABLE_EXTENSIBLE
import io.kstar.annotations.quality.Testing.*
import io.kstar.annotations.quality.TypeQuality
import java.util.function.Function

/**
 * Methods helpful for working with functions.
 *
 * @author jonathanl (shibo)
 */
@TypeQuality
(
    stability = STABLE_EXTENSIBLE,
    testing = TESTING_NOT_NEEDED,
    documentation = DOCUMENTED
)
object Functions
{
    /**
     * Applies the given function to the input value if it is non-null, returns null otherwise. In some cases, a monad
     * such as [Maybe] or [Result] may be a better approach.
     *
     * @param value The value to apply the function to
     * @param function The function to apply to the value
     * @return The mapped value, or null if the value was null.
     * @see Maybe
     *
     * @see Result
     */
    fun <Input, Output> applyTo(value: Input?, function: Function<Input, Output>): Output?
    {
        return if (value != null) function.apply(value) else null
    }

    /**
     * Applies the given functions to the input value if each function returns a non-null value, returns null otherwise.
     * In some cases, a monad such as [Maybe] or [Result] may be a better approach.
     *
     * @param value The value to apply the function to
     * @param function The function to apply to the value
     * @param function2 The second function to apply
     * @return The output value, or null if the value was null.
     * @see Maybe
     *
     * @see Result
     */
    fun <Input, T1, Output> applyTo(value: Input,
                                    function: Function<Input, T1>,
                                    function2: Function<T1, Output>): Output?
    {
        val output = applyTo(value, function)
        return if (output != null)
        {
            function2.apply(output)
        }
        else null
    }

    /**
     * Applies the given functions to the input value if each function returns a non-null value, returns null otherwise.
     * In some cases, a monad such as [Maybe] or [Result] may be a better approach.
     *
     * @param value The value to apply the function to
     * @param function The function to apply to the value
     * @param function2 The second function to apply
     * @param function3 The third function to apply
     * @return The output value, or null if the value was null.
     * @see Maybe
     *
     * @see Result
     */
    fun <Input, T1, T2, Output> applyTo(value: Input,
                                        function: Function<Input, T1>,
                                        function2: Function<T1, T2>,
                                        function3: Function<T2, Output>): Output?
    {
        val output = applyTo(value, function, function2)
        return if (output != null)
        {
            function3.apply(output)
        }
        else null
    }

    /**
     * Applies the given functions to the input value if each function returns a non-null value, returns null otherwise.
     * In some cases, a monad such as [Maybe] or [Result] may be a better approach.
     *
     * @param value The value to apply the function to
     * @param function The function to apply to the value
     * @param function2 The second function to apply
     * @param function3 The third function to apply
     * @param function4 The fourth function to apply
     * @return The output value, or null if the value was null.
     * @see Maybe
     *
     * @see Result
     */
    fun <Input, T1, T2, T3, Output> applyTo(value: Input,
                                            function: Function<Input, T1>,
                                            function2: Function<T1, T2>,
                                            function3: Function<T2, T3>,
                                            function4: Function<T3, Output>): Output?
    {
        val output = applyTo(value, function, function2, function3)
        return if (output != null)
        {
            function4.apply(output)
        }
        else null
    }

    /**
     * Applies the given functions to the input value if each function returns a non-null value, returns null otherwise.
     * In some cases, a monad such as [Maybe] or [Result] may be a better approach.
     *
     * @param value The value to apply the function to
     * @param function The function to apply to the value
     * @param function2 The second function to apply
     * @param function3 The third function to apply
     * @param function4 The fourth function to apply
     * @param function5 The fifth function to apply
     * @return The output value, or null if the value was null.
     * @see Maybe
     *
     * @see Result
     */
    fun <Input, T1, T2, T3, T4, Output> applyTo(value: Input,
                                                function: Function<Input, T1>,
                                                function2: Function<T1, T2>,
                                                function3: Function<T2, T3>,
                                                function4: Function<T3, T4>,
                                                function5: Function<T4, Output>): Output?
    {
        val output = applyTo(value, function, function2, function3, function4)
        return if (output != null)
        {
            function5.apply(output)
        }
        else null
    }

    /**
     * Applies the given functions to the input value if each function returns a non-null value, returns null otherwise.
     * In some cases, a monad such as [Maybe] or [Result] may be a better approach.
     *
     * @param value The value to apply the function to
     * @param function The function to apply to the value
     * @param function2 The second function to apply
     * @param function3 The third function to apply
     * @param function4 The fourth function to apply
     * @param function5 The fifth function to apply
     * @param function6 The sixth function to apply
     * @return The output value, or null if the value was null.
     * @see Maybe
     *
     * @see Result
     */
    fun <Input, T1, T2, T3, T4, T5, Output> applyTo(value: Input,
                                                    function: Function<Input, T1>,
                                                    function2: Function<T1, T2>,
                                                    function3: Function<T2, T3>,
                                                    function4: Function<T3, T4>,
                                                    function5: Function<T4, T5>,
                                                    function6: Function<T5, Output>): Output?
    {
        val output = applyTo(value, function, function2, function3, function4, function5)
        return if (output != null)
        {
            function6.apply(output)
        }
        else null
    }

    /**
     * Applies the given functions to the input value if each function returns a non-null value, returns null otherwise.
     * In some cases, a monad such as [Maybe] or [Result] may be a better approach.
     *
     * @param value The value to apply the function to
     * @param function The function to apply to the value
     * @param function2 The second function to apply
     * @param function3 The third function to apply
     * @param function4 The fourth function to apply
     * @param function5 The fifth function to apply
     * @param function6 The sixth function to apply
     * @param function7 The seventh function to apply
     * @return The output value, or null if the value was null.
     * @see Maybe
     *
     * @see Result
     */
    fun <Input, T1, T2, T3, T4, T5, T6, Output> applyTo(value: Input,
                                                        function: Function<Input, T1>,
                                                        function2: Function<T1, T2>,
                                                        function3: Function<T2, T3>,
                                                        function4: Function<T3, T4>,
                                                        function5: Function<T4, T5>,
                                                        function6: Function<T5, T6>,
                                                        function7: Function<T6, Output>): Output?
    {
        val output = applyTo(value, function, function2, function3, function4, function5, function6)
        return if (output != null)
        {
            function7.apply(output)
        }
        else null
    }

    /**
     * Applies the given functions to the input value if each function returns a non-null value, returns null otherwise.
     * In some cases, a monad such as [Maybe] or [Result] may be a better approach.
     *
     * @param value The value to apply the function to
     * @param function The function to apply to the value
     * @param function2 The second function to apply
     * @param function3 The third function to apply
     * @param function4 The fourth function to apply
     * @param function5 The fifth function to apply
     * @param function6 The sixth function to apply
     * @param function7 The seventh function to apply
     * @param function8 The eighth function to apply
     * @return The output value, or null if the value was null.
     * @see Maybe
     *
     * @see Result
     */
    fun <Input, T1, T2, T3, T4, T5, T6, T7, Output> applyTo(value: Input,
                                                            function: Function<Input, T1>,
                                                            function2: Function<T1, T2>,
                                                            function3: Function<T2, T3>,
                                                            function4: Function<T3, T4>,
                                                            function5: Function<T4, T5>,
                                                            function6: Function<T5, T6>,
                                                            function7: Function<T6, T7>,
                                                            function8: Function<T7, Output>): Output?
    {
        val output = applyTo(value, function, function2, function3, function4, function5, function6, function7)
        return if (output != null)
        {
            function8.apply(output)
        }
        else null
    }

    /**
     * Convenient functional interface for any two-parameter callback
     */
    fun doNothing(ignored: Any?, ignored2: Any?)
    {
    }

    /**
     * Convenient functional interface for any three-parameter callback
     */
    fun doNothing(ignored: Any?, ignored2: Any?, ignored3: Any?)
    {
    }

    /**
     * Convenient functional interface for any one-parameter callback
     */
    fun doNothing(ignored: Any?)
    {
    }

    /**
     * Runs the given list of [Function]s, until one returns a non-null value.
     *
     * @param from The value to transform
     * @param functions The functions to apply
     * @return The value returned by the first function that returns a non-null value.
     */
    @Suppress("unused")
    @SafeVarargs
    fun <From, To> firstSuccessfulFunction(from: From, vararg functions: Function<From, To>): To?
    {
        for (function in functions)
        {
            val result = function.apply(from)
            if (result != null)
            {
                return result
            }
        }
        return null
    }
}