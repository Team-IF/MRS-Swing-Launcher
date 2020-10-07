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

package io.teamif.patrick.mrs.util.minecraft

import io.teamif.patrick.mrs.util.kotlin.resettableLazy
import io.teamif.patrick.mrs.util.system.SystemUtils
import java.io.File

object MinecraftUtils {
    private const val APPDATA = "APPDATA"

    private const val USER_HOME = "user.home"

    private const val MINECRAFT_DIR = ".minecraft"

    private const val MAC_MINECRAFT = "Library/Application Support/minecraft"

    private const val UNKNOWN_OS = "Unknown OS: %s"

    private val minecraftDirDelegate = resettableLazy {
        when {
            SystemUtils.isWindows() -> {
                val appdata = System.getenv(APPDATA)
                if (appdata != null) {
                    val folder = File(appdata, MINECRAFT_DIR)
                    if (folder.isDirectory) {
                        return@resettableLazy folder to true
                    }
                    folder.mkdirs()
                    return@resettableLazy folder to false
                }
                return@resettableLazy null to false
            }
            SystemUtils.isMac() -> {
                val home = System.getProperty(USER_HOME)
                if (home != null) {
                    val folder = File(home, MAC_MINECRAFT)
                    if (folder.isDirectory) {
                        return@resettableLazy folder to true
                    }
                    folder.mkdirs()
                    return@resettableLazy folder to false
                }
                return@resettableLazy null to false
            }
            SystemUtils.isLinux() -> {
                val home = System.getProperty(USER_HOME)
                if (home != null) {
                    val folder = File(home, MINECRAFT_DIR)
                    if (folder.isDirectory) {
                        return@resettableLazy folder to true
                    }
                    folder.mkdirs()
                    return@resettableLazy folder to false
                }
                return@resettableLazy null to false
            }
            else -> {
                throw UnsupportedOperationException(UNKNOWN_OS.format(SystemUtils.osName))
            }
        }
    }

    internal val minecraftDir by minecraftDirDelegate
}