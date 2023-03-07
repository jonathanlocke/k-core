package io.kstar.core.messaging

import io.kstar.annotations.documentation.UmlIncludeType
import io.kstar.core.internal.Diagrams.DiagramMessaging

/**
 * A marker interface for an object that can be transmitted and received.
 *
 * @author Jonathan Locke
 */
@UmlIncludeType(inDiagrams = [DiagramMessaging::class])
interface Transmittable
