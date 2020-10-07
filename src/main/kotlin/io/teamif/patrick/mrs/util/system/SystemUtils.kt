package io.teamif.patrick.mrs.util.system

object SystemUtils {
    @JvmStatic
    internal val osName by lazy {
        System.getProperty("os.name")
    }

    @JvmStatic
    internal fun isWindows(): Boolean {
        return osName != null && osName.toLowerCase().contains("windows")
    }

    @JvmStatic
    internal fun isLinux(): Boolean {
        return osName != null && osName.toLowerCase().contains("linux")
    }

    @JvmStatic
    internal fun isMac(): Boolean {
        return osName != null && osName.toLowerCase().contains("mac")
    }
}