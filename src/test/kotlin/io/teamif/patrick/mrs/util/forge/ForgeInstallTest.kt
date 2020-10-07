/*
 * Copyright (C) 2020 PatrickKR
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
 *
 * Contact me on <mailpatrickkr@gmail.com>
 */

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