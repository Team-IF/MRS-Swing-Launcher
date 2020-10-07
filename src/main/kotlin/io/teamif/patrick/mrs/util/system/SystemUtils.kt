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