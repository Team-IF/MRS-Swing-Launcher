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