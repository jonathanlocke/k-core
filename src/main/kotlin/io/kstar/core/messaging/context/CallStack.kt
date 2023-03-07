package io.kstar.core.messaging.context

import com.telenav.kivakit.annotations.code.quality.TypeQuality

/**
 * A stack of KivaKit [Method] objects for a given thread ([.callstack] or the current thread
 * [.callstack].
 *
 *
 * The caller of a given class on the stack (the "callee") can be determined with
 * [.callerOf], which takes the callee type and a variable number
 * of classes to ignore. Matching of the caller and its distance from the callee are specified by the first and second
 * parameters respectively.
 *
 *
 * For example, the class **A** might want to determine who called it (for a concrete example see [Debug],
 * which finds its caller in order to switch on/off debugging for that class). This makes class **A** the callee. It
 * might also wish to ignore another class in the same package, class **B**, which might be on the stack between the
 * caller and the callee. In this case, CallStack.callerOf(..., A.class, B.class) would return the code which is calling
 * method(s) in **A** whether it called through method(s) in **B** or not.
 *
 * @author Jonathan Locke
 */
@UmlIncludeType(diagram = DiagramContext::class)
@TypeQuality(stability = STABLE_EXTENSIBLE, testing = UNTESTED, documentation = DOCUMENTED)
object CallStack
{
    fun callerOf(proximity: Proximity?, matching: Matching, calleeType: Class<*>?): Method?
    {
        return callerOf(proximity, matching, calleeType, Matching.EXACT)
    }

    /**
     * Finds the (KivaKit) [Method] that called the given callee class. The [Matching] parameter is used to
     * specify whether classes should exactly match the given class parameters or if subclasses are acceptable as well.
     * Because intervening frames may be uninteresting, the [Proximity] type can be used to determine if the
     * caller must be the immediate caller or if a more distant caller is acceptable.  It is possible to specify a
     * variable-length list of classes to ignore when determining the calling method after finding the callee. This
     * makes it easy to skip over intervening frames that don't matter.
     *
     * @param proximity Either [Proximity.IMMEDIATE] if the caller must be immediately before the callee on the
     * stack or [Proximity.DISTANT] if the caller can be anywhere on the stack
     * @param matching Either [Matching.EXACT] or [Matching.SUBCLASS] to determine if matching of the type
     * parameters should be exact or if any subclass will do
     * @param calleeType The class that we want the caller of
     * @param ignoreMatching Matching for the classes to be ignored
     * @param ignores Any intermediate classes to ignore
     * @return The class that most recently called the given callee on this thread's execution stack
     */
    fun callerOf(proximity: Proximity?,
                 matching: Matching,
                 calleeType: Class<*>?,
                 ignoreMatching: Matching,
                 vararg ignores: Class<*>?): Method?
    {
        // Get call stack
        val stack: `var` = callstack()

        // Find the index of the callee on the stack using the matching rules
        val callee: `var` = if (calleeType == null) 0 else findCallee(matching, proximity, stack, calleeType)

        // If we found the callee
        if (callee != -1)
        {
            // the caller is the next index
            var caller: `var` = callee + 1

            // except that we may need to ignore some methods
            while (caller < stack.size() && shouldIgnore(stack.get(caller), ignoreMatching, ignores))
            {
                caller++
            }
            return if (caller < stack.size()) stack.get(caller) else null
        }
        return null
    }

    fun callstack(): List<Method>
    {
        return callstack(Thread.currentThread())
    }

    @Suppress("unused")
    fun callstack(thread: Thread?): List<Method>
    {
        val stack: `var` = ArrayList<Method>()
        for (frame in Thread.currentThread().stackTrace)
        {
            val method: `var` = method(frame)
            if (method != null)
            {
                stack.add(method)
            }
        }
        return stack
    }

    private fun findCallee(matching: Matching,
                           proximity: Proximity?,
                           stack: List<Method>,
                           calleeType: Class<*>): Int
    {
        var callee: `var` = -1
        var index: `var` = 0
        for (method in stack)
        {
            val matches: `var` = if (matching == Matching.EXACT) calleeType == method.parentType().asJavaType() else calleeType.isAssignableFrom(method.parentType().asJavaType())
            when (proximity)
            {
                Proximity.DISTANT ->
                {
                    if (matches)
                    {
                        return index
                    }
                }

                Proximity.IMMEDIATE ->
                {
                    if (matches)
                    {
                        callee = index
                    }
                    else
                    {
                        if (callee > -1)
                        {
                            return callee
                        }
                    }
                }
            }
            index++
        }
        return callee
    }

    private fun shouldIgnore(caller: Method?, matching: Matching, vararg ignores: Class<*>): Boolean
    {
        var ignored: `var` = false
        val exact: `var` = matching == Matching.EXACT
        if (caller != null)
        {
            for (ignore in ignores)
            {
                if (exact && ignore === caller.parentType().asJavaType() || !exact && ignore.isAssignableFrom(caller.parentType().asJavaType()))
                {
                    ignored = true
                }
            }
        }
        return ignored
    }

    enum class Matching
    {
        /** The class must exactly match  */
        EXACT,

        /** The class must be a subclass of the specified class  */
        SUBCLASS
    }

    enum class Proximity
    {
        /** The class can be anywhere on the call stack  */
        DISTANT,

        /** The class must be at the top of the call stack  */
        IMMEDIATE
    }
}