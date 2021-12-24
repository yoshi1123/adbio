ADBio
-----

ADBio is an Android app and widget that enables/disables the Android Debug
Bridge (ADB).

[<img src="https://fdroid.gitlab.io/artwork/badge/get-it-on.png"
     alt="Get it on F-Droid"
     height="80">](https://f-droid.org/packages/io.github.yoshi1123.adbio/)

# Images

![Screenshot 1](metadata/en-US/images/phoneScreenshots/1.png)

# Install

    git clone https://github.com/yoshi1123/adbio.git
    cd adbio
    ./setup_signing_key.sh
    ./gradlew assembleRelease
    adb install ./build/outputs/apk/release/adbio-release.apk
