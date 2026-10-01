package app.morph.patches.warp.shared

import app.morphe.patcher.patch.ApkFileType
import app.morphe.patcher.patch.AppTarget
import app.morphe.patcher.patch.Compatibility

internal object Constants {
    val COMPATIBILITY_WARP = Compatibility(
        name = "1.1.1.1 + WARP",
        packageName = "com.cloudflare.onedotonedotonedotone",
        apkFileType = ApkFileType.APKM,
        targets = listOf(
            AppTarget(
                version = "6.38.9",
                minSdk = 24,
                isExperimental = false
            )
        )
    )
}
