package io.teamif.patrick.mrs.util.kotlin

import java.util.concurrent.atomic.AtomicReference
import kotlin.reflect.KProperty

internal fun <T> resettableLazy(initializer: () -> T) : ResettableDelegate<T> {
    return ResettableDelegate(initializer)
}

internal class ResettableDelegate<T>(private val initializer: () -> T) {
    private val lazyRef: AtomicReference<Lazy<T>> = AtomicReference(lazy(initializer))

    operator fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return lazyRef.get().getValue(thisRef, property)
    }

    internal fun reset() {
        lazyRef.set(lazy(initializer))
    }
}