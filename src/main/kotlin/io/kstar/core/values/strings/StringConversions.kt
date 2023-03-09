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
@file:Suppress("MemberVisibilityCanBePrivate")

package io.kstar.core.values.strings

import io.kstar.annotations.documentation.UmlIncludeType
import io.kstar.annotations.quality.Documentation.DOCUMENTED
import io.kstar.annotations.quality.Stability.STABLE_EXTENSIBLE
import io.kstar.annotations.quality.Testing.*
import io.kstar.annotations.quality.TypeQuality
import io.kstar.internal.Diagrams.DiagramStrings


/**
 * String conversion utilities
 *
 * @author jonathanl (shibo)
 */
@Suppress("unused")
@UmlIncludeType(inDiagrams = [DiagramStrings::class])
@TypeQuality
(
    stability = STABLE_EXTENSIBLE,
    testing = UNTESTED,
    documentation = DOCUMENTED
)
object StringConversions
{
    /**
     * Returns the given text trivially converted to HTML
     */
    fun toHtmlString(text: String): String
    {
        return text.replace("\n".toRegex(), "<br/>").replace(" ".toRegex(), "&nbsp;")
    }

}