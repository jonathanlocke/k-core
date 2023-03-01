@file:Suppress("MemberVisibilityCanBePrivate")

package io.kstar.annotations.quality

import io.kstar.annotations.quality.Audience.PUBLIC
import io.kstar.annotations.quality.Documentation.*
import io.kstar.annotations.quality.Stability.*
import io.kstar.annotations.quality.Testing.*

/**
 * The intended audience for a class or interface. Types annotated with [TypeQuality] default to an audience of
 * [PUBLIC].
 *
 * @author  Jonathan Locke
 * @see TypeQuality
 */
@Suppress("unused")
@TypeQuality
(
    stability = STABLE,
    testing = TESTING_NOT_NEEDED,
    documentation = DOCUMENTED,
    reviewers = ["Jonathan Locke"]
)
enum class Audience
{
    /** The annotated code is public and intended for end-users  */
    PUBLIC,

    /** The annotated code is internal and should not be used outside the framework  */
    INTERNAL;

    val isInternal: Boolean get() = this == INTERNAL

    val isPublic: Boolean get() = this == PUBLIC
}