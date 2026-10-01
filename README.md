# Morph-patches

Custom Morphe patches for making Cloudflare 1.1.1.1 + WARP more usable on Android TV.

## Target

- Package: `com.cloudflare.onedotonedotonedotone`
- Version: `6.38.9` (version code 5641)
- Patch: **WARP TV compatibility**

The first patch focuses on Android TV discoverability, landscape presentation, and remote-launch compatibility. UI-specific improvements will be added incrementally after testing the generated patch on a real Android TV device.

## Build

The GitHub Actions workflow builds the Morphe patch bundle and publishes it as a workflow artifact.

Morphe can consume a released `.mpp` bundle directly. See the Morphe documentation for patch-bundle usage.
