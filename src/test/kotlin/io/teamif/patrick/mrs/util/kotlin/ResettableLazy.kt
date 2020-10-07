package io.teamif.patrick.mrs.util.kotlin

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class ResettableLazy {
    @Suppress("UnnecessaryVariable")
    @Test
    fun resettableLazyTest() {
        val delegate = resettableLazy {
            System.currentTimeMillis()
        }
        val time by delegate
        val firstTime = time
        delegate.reset()
        Thread.sleep(100)
        val secondTime = time
        println(firstTime)
        println(secondTime)
        Assertions.assertNotEquals(secondTime, firstTime)
    }
}