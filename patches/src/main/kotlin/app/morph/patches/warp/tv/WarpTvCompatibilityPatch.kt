package app.morph.patches.warp.tv

import app.morphe.patcher.patch.resourcePatch
import app.morph.patches.warp.shared.COMPATIBILITY_WARP
import org.w3c.dom.Element

@Suppress("unused")
val warpTvCompatibilityPatch = resourcePatch(
    name = "WARP TV compatibility",
    description = "Adds Android TV launcher metadata, disables the touchscreen requirement, and forces the main launcher activity into landscape mode.",
    default = true
) {
    compatibleWith(COMPATIBILITY_WARP)

    execute {
        document("AndroidManifest.xml").use { document ->
            val manifest = document.documentElement

            fun addUsesFeature(name: String, required: String) {
                val existing = document.getElementsByTagName("uses-feature").let { nodes ->
                    (0 until nodes.length)
                        .map { nodes.item(it) as? Element }
                        .firstOrNull { it.getAttribute("android:name") == name }
                }
                if (existing == null) {
                    val feature = document.createElement("uses-feature")
                    feature.setAttribute("android:name", name)
                    feature.setAttribute("android:required", required)
                    manifest.appendChild(feature)
                }
            }

            addUsesFeature("android.software.leanback", "false")
            addUsesFeature("android.hardware.touchscreen", "false")

            val activities = document.getElementsByTagName("activity")
            for (i in 0 until activities.length) {
                val activity = activities.item(i) as? Element ?: continue
                val filters = activity.getElementsByTagName("intent-filter")
                var launcherFilter: Element? = null

                for (j in 0 until filters.length) {
                    val filter = filters.item(j) as? Element ?: continue
                    val categories = filter.getElementsByTagName("category")
                    var hasMain = false
                    var hasLauncher = false

                    for (k in 0 until categories.length) {
                        val category = categories.item(k) as? Element ?: continue
                        when (category.getAttribute("android:name")) {
                            "android.intent.category.MAIN" -> hasMain = true
                            "android.intent.category.LAUNCHER" -> hasLauncher = true
                        }
                    }

                    if (hasMain && hasLauncher) {
                        launcherFilter = filter
                        break
                    }
                }

                if (launcherFilter != null) {
                    val existingLeanback = launcherFilter.getElementsByTagName("category").let { categories ->
                        (0 until categories.length)
                            .map { categories.item(it) as? Element }
                            .any { it?.getAttribute("android:name") == "android.intent.category.LEANBACK_LAUNCHER" }
                    }
                    if (!existingLeanback) {
                        val category = document.createElement("category")
                        category.setAttribute(
                            "android:name",
                            "android.intent.category.LEANBACK_LAUNCHER"
                        )
                        launcherFilter.appendChild(category)
                    }
                    activity.setAttribute("android:screenOrientation", "landscape")
                    break
                }
            }
        }
    }
}
