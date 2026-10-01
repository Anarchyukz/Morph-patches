group = "app.morph"

patches {
    about {
        name = "Morph Patches"
        description = "Android TV compatibility patches for Cloudflare 1.1.1.1 + WARP"
        source = "https://github.com/Anarchyukz/Morph-patches"
        author = "Anarchyukz"
        contact = "https://github.com/Anarchyukz"
        website = "https://github.com/Anarchyukz/Morph-patches"
        license = "GPLv3"
    }
}

val patchListGeneratorClasspath = configurations.create("patchListGeneratorClasspath")

dependencies {
    compileOnly(libs.gson)
    patchListGeneratorClasspath(libs.gson)
}

tasks {
    register<JavaExec>("generatePatchesList") {
        description = "Generate the patch list"
        dependsOn(build)
        classpath = sourceSets["main"].runtimeClasspath + patchListGeneratorClasspath
        mainClass.set("util.PatchListGeneratorKt")
    }

    publish {
        dependsOn("generatePatchesList")
    }
}
