package util

import com.google.gson.GsonBuilder
import java.io.File

fun main() {
    val output = File("patches-list.json")
    val root = File("patches/src/main/kotlin")
    val patches = root.walkTopDown()
        .filter { it.isFile && it.extension == "kt" }
        .map { it.nameWithoutExtension }
        .filter { it.endsWith("Patch") }
        .sorted()
        .toList()

    val json = mapOf(
        "version" to (System.getProperty("version") ?: "0.1.0"),
        "patches" to patches.map { mapOf("name" to it) }
    )

    output.writeText(
        GsonBuilder().setPrettyPrinting().create().toJson(json) + "\n"
    )
}
