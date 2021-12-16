#!/usr/bin/env bash
set -e

function question() {
    # question(question, ans_len, lower, default)
    # question: the question string
    # ans_len: -1 for any length
    # lower: boolean (true or false) if to make lowercase
    # default (optional): the default answer if only enter is pressed

    read -p "$1" c
    [[ -n "$4" ]] && c=${c:-$4}
    [[ $3 == "true" ]] && c=$(echo $c | tr '[:upper:]' '[:lower:]')
    [[ $2 -ne -1 ]] && c=${c:0:1}
    echo -n $c
}


newkey=$(question "generate a new key? [y]: " 1 true "y")

if [[ "$newkey" == "y" ]]; then

    rm -f ./adbio_release_key.jks

    kspath=./adbio_release_key.jks
    keyalias=$(question "key alias [adbio]: " -1 false "adbio")
    kspass=$(question "keystore password: " -1 false)
    keypass=$(question "key password [use keystore password]: " -1 false "$kspass")
    export kspass

    keytool -genkey -v -keystore ./adbio_release_key.jks \
        -storepass:env kspass -alias adbio \
        -keyalg RSA -keysize 4068 -keypass "$keypass" --validity 10000

else

    kspath=$(question "keystore location: " -1 false)
    kspass=$(question "keystore password: " -1 false)
    keyalias=$(question "key alias [adbio]: " -1 false "adbio")
    keypass=$(question "key password [use keystore password]: " -1 false "$kspass")

fi

if [[ -f gradle.properties ]]; then
    sed -i '/^RELEASE_/d' gradle.properties
fi
echo "RELEASE_STORE_FILE=${kspath}" >> gradle.properties
echo "RELEASE_STORE_PASSWORD=${kspass}" >> gradle.properties
echo "RELEASE_KEY_ALIAS=${keyalias}" >> gradle.properties
echo "RELEASE_KEY_PASSWORD=${keypass}" >> gradle.properties
