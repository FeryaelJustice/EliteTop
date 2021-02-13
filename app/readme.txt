MAIN:
    *JAVA:
        -root: (/com.solucionescreativasdemallorca.elitetop) Root Folder ->
            ·MainActivity.kt (main activity logged of all app, the container)
            ·SplashScreen.kt
        -base: (/com.solucionescreativasdemallorca.elitetop.base) Base Classes
        -dataclass: (/com.solucionescreativasdemallorca.elitetop.dataclass) Kotlin Data Classes
        -login: (/com.solucionescreativasdemallorca.elitetop.login) Login
        -recoverpassword: (/com.solucionescreativasdemallorca.elitetop.recoverpassword) Recover Password
        -register: (/com.solucionescreativasdemallorca.elitetop.register) Register
        -main: (/com.solucionescreativasdemallorca.elitetop.main) Root of Main App ->
            ·athlete: (/com.solucionescreativasdemallorca.elitetop.main.athlete) Athlete
            ·trainer: (/com.solucionescreativasdemallorca.elitetop.main.trainer) Trainer

    *RES:
        -anim: Animations of the app
        -drawable: Image resources of the app (icons and images, preferently in xml and svg)
        -layouts: Layouts of the app
        -mipmap: Icon of the app (includes subfolders for icon variations)
        -values: Values of the app ->
            ·colors.xml: Colors of the app
            ·dimens: Dimensions of the app (includes different files for different screen sizes)
            ·strings: Strings of the app (includes different files for different languages of the device)
            ·themes: Themes of the app (includes different files for day/night purpose)

    *AndroidManifest.xml: The Official Android Manifest of the App.