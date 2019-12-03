# hello-windows

Simple Java 8 Swing app showcasing some potential issues with Launch4J and/or the Gradle-Launch4J plugin.

## The Problem
I am trying to build this app (including using Gradle-Launch4J and/or standalone Launch4J GUI tool) on my Mac, then port
it to a Windows 10 64-bit machine and run it. All the details of the problem(s) I'm seeing are explained ad nauseum in
[this StackOverflow question here](https://stackoverflow.com/questions/59073220/gradle-launch4j-build-produces-jre-less-exe-that-windows-doesnt-trust).

But the TLDR; of it is:

1. Clone the project and run `./gradlew clean build shadowJar createExe createDistro`
2. Copy the built `build/dist/hello-windows.zip` ZIP file to any location on a Windows 10 (or any modern Windows) machine that you like
3. Unzip/extract the file
4. Double-click on the `hello-windows.exe` application to run it
5. First you'll see a screen (screenshot is provided in the StackOverflow question mentioned above) saying Windows doesn't trust the app; this isn't a HUGE deal but I am curious what the fix for it is
6. Click "_More info_" and then "_Run anyway_" to bypass the Windows warning
7. You'll see an error screen either saying that the bundled JRE is missing/corrupt or that the app requires a minimum version of Java in order to run. **This is the main problem.** No matter how I muck with the Launch4J configurations inside `build.gradle`, I can't seem to find any that get the app to "see" the bundled JRE (`jre8/` dir) at runtime... 

## Running locally
### Requirements
This app is built using Gradle **however** you do not need Gradle installed to build & run it locally (we use the Gradle Wrapper).

You can run this app as an executable Java application **or** as a native Windows (EXE) app. To run as an executable Java app, you
just need Java 8+ installed. To run as a native Windows app...you need to be on a modern Windows machine ;-)

### Run as executable JAR
```
./gradlew clean build shadowJar && java -jar build/libs/hello-windows.jar
```

### Run as native Windows EXE
```
./gradlew clean build shadowJar createExe
```

If you are building on non-Windows (such as on a Mac or Linux machine) and then need to port the EXE over to a Windows machine,
you can run the following command:

```
./gradlew clean build shadowJar createExe createDistro
```

This will generate a ZIP file at `build/dist/hello-windows.zip` that contains:

```
    hello-windows.zip/
        hello-windows.exe       -->     The Windows EXE built by the 'createExe' task
        lib/*                   -->     The lib/ dir for the EXE that is also built by the `createExe` task 
        jre8/                   -->     OpenJDK JRE8 (copied from the libs/jre8 dir)
```

In theory this _should_ be enough to produce a self-contained native Windows EXE with its own bundled OpenJDK JRE8
runtime (which prevents the end user from needing to first install Java on their machine, and also allows me to control
which JRE is used at runtime).
