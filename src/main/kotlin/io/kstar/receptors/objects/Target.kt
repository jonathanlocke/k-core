@file:Suppress("unused")

package io.kstar.receptors.objects

import java.util.function.Consumer

interface Target<Value> : Consumer<Value>
