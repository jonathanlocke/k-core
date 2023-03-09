package io.kstar.core.collections.map

import com.telenav.kivakit.annotations.code.quality.TypeQuality

/**
 * A [BaseMap] with [LinkedHashMap] as the implementation.
 *
 * @author jonathanl (shibo)
 */
@UmlClassDiagram(diagram = DiagramCollections::class)
@TypeQuality(stability = STABLE_EXTENSIBLE, testing = TESTING_INSUFFICIENT, documentation = DOCUMENTED)
class LinkedMap<Key, Value> @JvmOverloads constructor(maximumSize: Maximum = MAXIMUM) : ObjectMap<Key, Value>(maximumSize, LinkedHashMap())