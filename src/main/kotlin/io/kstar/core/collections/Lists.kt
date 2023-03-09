package io.kstar.core.collections

import io.kstar.annotations.documentation.UmlIncludeType
import io.kstar.annotations.quality.Documentation.DOCUMENTED
import io.kstar.annotations.quality.Stability.STABLE_EXTENSIBLE
import io.kstar.annotations.quality.Testing.TESTING_NOT_NEEDED
import io.kstar.annotations.quality.TypeQuality
import io.kstar.internal.Diagrams.DiagramLanguage

@UmlIncludeType(inDiagrams = [DiagramLanguage::class])
@TypeQuality
(
    stability = STABLE_EXTENSIBLE,
    testing = TESTING_NOT_NEEDED,
    documentation = DOCUMENTED
)
object Lists
{
    fun <T> List<T>.join(separator: String): String = joinToString { separator }
}
