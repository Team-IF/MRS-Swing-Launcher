package io.teamif.patrick.mrs.util.forge

import io.teamif.patrick.mrs.util.minecraft.MinecraftUtils
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class ForgeInstallTest {
    @Test
    fun forgeInstallTest() {
        Assertions.assertDoesNotThrow {
            val url = ForgeDownload.getForgeURL("1.12.2")
            val result = MinecraftUtils.minecraftDir

            if (result.second) {
                val folder = requireNotNull(MinecraftUtils.minecraftDir.first)
                ForgeInstall.installForge(url, folder)
            }
        }
    }
}