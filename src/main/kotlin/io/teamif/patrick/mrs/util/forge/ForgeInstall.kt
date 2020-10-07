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

import java.io.File
import java.io.OutputStream
import java.net.MalformedURLException
import java.net.URL
import java.net.URLClassLoader
import java.util.function.Predicate

object ForgeInstall {
    private const val CLASS_CLIENT_INSTALL = "net.minecraftforge.installer.actions.ClientInstall"

    private const val CLASS_INSTALL = "net.minecraftforge.installer.json.Install"

    private const val CLASS_UTIL = "net.minecraftforge.installer.json.Util"

    private const val CLASS_PROGRESS_CALLBACK = "net.minecraftforge.installer.actions.ProgressCallback"

    private const val METHOD_LOAD_INSTALL_PROFILE = "loadInstallProfile"

    private const val METHOD_RUN = "run"

    private const val METHOD_WITH_OUTPUTS = "withOutputs"

    private const val REFLECTION_ERROR = "Error while running reflection"

    private const val URL_NOT_VALID = "URL provided is not valid: %s"

    @JvmStatic
    internal fun installForge(url: String, file: File) {
        require(file.exists() && file.isDirectory) {
            "Minecraft directory doesn't exist: ${file.name}"
        }

        try {
            val loader = URLClassLoader(arrayOf(URL(url)))
            val classClientInstall = Class.forName(CLASS_CLIENT_INSTALL, true, loader)
            val classInstall = Class.forName(CLASS_INSTALL, true, loader)
            val classUtil = Class.forName(CLASS_UTIL, true, loader)
            val classProgressCallback = Class.forName(CLASS_PROGRESS_CALLBACK, true, loader)
            val methodLoadInstallProfile = classUtil.getDeclaredMethod(METHOD_LOAD_INSTALL_PROFILE)
            val methodRun = classClientInstall.getDeclaredMethod(METHOD_RUN, File::class.java, Predicate::class.java)
            val methodWithOutputs = classProgressCallback.getDeclaredMethod(METHOD_WITH_OUTPUTS, Array<OutputStream>::class.java)
            val objectInstall = methodLoadInstallProfile.invoke(null)
            val objectProgressCallback = methodWithOutputs.invoke(null, arrayOf(System.out))
            val objectClientInstall = classClientInstall.getDeclaredConstructor(classInstall, classProgressCallback).newInstance(objectInstall, objectProgressCallback)
            methodRun.invoke(objectClientInstall, file, Predicate<String> { true })
        } catch (exception: MalformedURLException) {
            throw IllegalAccessException(URL_NOT_VALID.format(url))
        } catch (exception: ReflectiveOperationException) {
            throw RuntimeException(REFLECTION_ERROR, exception)
        }
    }
}