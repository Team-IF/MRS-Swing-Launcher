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

import com.google.gson.JsonObject
import com.google.gson.JsonParser
import org.apache.http.HttpException
import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.HttpClients
import org.apache.http.util.EntityUtils

object ForgeDownload {
    private lateinit var LAST_MODIFIED: String

    private lateinit var JSON_CACHE: JsonObject

    private const val JSON_ADDR = "https://files.minecraftforge.net/maven/net/minecraftforge/forge/promotions_slim.json"

    private const val INSTALLER_ADDR = "https://files.minecraftforge.net/maven/net/minecraftforge/forge/%s/forge-%s-installer.jar"

    private const val VERSION_KEY = "promos"

    private const val LAST_MODIFIED_KEY = "last-modified"

    private const val LATEST_KEY = "latest"

    private const val RECOMMENDED_KEY = "recommended"

    private const val ENTITY_NOT_FOUND = "Entity not found from $JSON_ADDR"

    private const val VERSION_NOT_FOUND = "Latest Forge %s not found. Did you properly set the version...?"

    @JvmStatic
    internal fun downloadForge(version: String, preferRecommended: Boolean = false): ByteArray {
        val url = getForgeURL(version, preferRecommended)

        val client = HttpClients.createMinimal()
        val request = HttpGet(url)

        val response = client.execute(request)
        val entity = response.entity

        val content = entity?.content?.readBytes() ?: throw HttpException(ENTITY_NOT_FOUND)

        response.close()
        client.close()

        return content
    }

    @JvmStatic
    internal fun getForgeURL(version: String, preferRecommended: Boolean = false): String {
        refreshJson()

        val type = if (preferRecommended) RECOMMENDED_KEY else LATEST_KEY

        val forgeVersion = if (JSON_CACHE.has("$version-$type")) {
            JSON_CACHE.getAsJsonPrimitive("$version-$type").asString
        } else {
            if (preferRecommended) {
                if (JSON_CACHE.has("$version-$LATEST_KEY")) {
                    JSON_CACHE.getAsJsonPrimitive("$version-$LATEST_KEY").asString
                }
            }
            throw IllegalArgumentException(VERSION_NOT_FOUND.format(version))
        }

        return INSTALLER_ADDR.format("$version-$forgeVersion", "$version-$forgeVersion")
    }

    private fun refreshJson() {
        val client = HttpClients.createMinimal()
        val request = HttpGet(JSON_ADDR)

        val response = client.execute(request)

        val lastModified = response.getFirstHeader(LAST_MODIFIED_KEY).value

        if (!this::LAST_MODIFIED.isInitialized || lastModified != LAST_MODIFIED) {
            val entity = response.entity

            if (entity != null) {
                val json = EntityUtils.toString(entity)
                JSON_CACHE = JsonParser.parseString(json).asJsonObject.getAsJsonObject(VERSION_KEY)
            } else {
                throw HttpException(ENTITY_NOT_FOUND)
            }
        }

        response.close()
        client.close()
    }
}