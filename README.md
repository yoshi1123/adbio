[![F-Droid version](https://img.shields.io/f-droid/v/io.github.yoshi1123.adbio.svg)](https://f-droid.org/en/packages/io.github.yoshi1123.adbio)
<!--![Current release](https://img.shields.io/github/release/yoshi1123/adbio.svg?logo=github)-->

ADBio is an Android app and widget that enables/disables the Android Debug
Bridge (ADB).

<a href="https://f-droid.org/en/packages/io.github.yoshi1123.adbio"><img src="https://fdroid.gitlab.io/artwork/badge/get-it-on.png" alt="Get it on F-Droid" height="75"></a>

# Images

![Screenshot 1](metadata/en-US/images/phoneScreenshots/1.png)

# Install

    git clone https://github.com/yoshi1123/adbio.git
    cd adbio
    ./setup_signing_key.sh
    ./gradlew assembleRelease
    adb install ./build/outputs/apk/release/adbio-release.apk
