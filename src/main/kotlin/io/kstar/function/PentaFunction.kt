package io.kstar.function

import io.kstar.annotations.quality.Documentation.DOCUMENTED
import io.kstar.annotations.quality.Stability.STABLE
import io.kstar.annotations.quality.Testing.TESTING_NOT_NEEDED
import io.kstar.annotations.quality.TypeQuality

/**
 * Represents a function that accepts two arguments and produces a result. This is the five-arity specialization of
 * [Function].
 *
 * @param <Argument1> The type of the first argument to the function
 * @param <Argument2> The type of the second argument to the function
 * @param <Argument3> The type of the third argument to the function
 * @param <Argument4> The type of the fourth argument to the function
 * @param <Argument5> The type of the fifth argument to the function
 * @param <Result> the type of the result of the function
 */
@TypeQuality
(
    stability = STABLE,
    testing = TESTING_NOT_NEEDED,
    documentation = DOCUMENTED
)
fun interface PentaFunction<Argument1, Argument2, Argument3, Argument4, Argument5, Result>
{
    /**
     * Applies this function to the given arguments.
     *
     * @param argument1 The first function argument
     * @param argument2 The second function argument
     * @param argument3 The third function argument
     * @param argument4 The fourth function argument
     * @param argument5 The fifth function argument
     * @return The function result
     */
    fun apply(argument1: Argument1,
              argument2: Argument2,
              argument3: Argument3,
              argument4: Argument4,
              argument5: Argument5): Result
}