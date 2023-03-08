@file:Suppress("unused")

package io.kstar.receptors.objects

import java.util.function.Supplier

interface Source<Value> : Supplier<Value>
