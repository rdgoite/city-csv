# City CSV Downloader

## Introduction

This is a very simple command line application that downloads location data from GoEuro's Suggest Position API. The data it downloads are formatted into comma separated values (CSV) and written into a file named `location.csv`. By default, this application overrides the said file if it exists in the current running directory.

The downloader application runs as a Spring Boot application that uses Spring Web library to access REST API. The command line code is handled by Spring's built-in `ApplicationRunner` class.

## User Guide

### Gradle

There is no pre-built jar archive in this repository. To be able to run this application, it needs to be built using Gradle; this application was built (and tested) using Gradle 2.10. This repository already includes a Gradle wrapper file that should be able to build the app with no additional manual set up.

Alternatively, Gradle can be installed into the local machine. To set up Gradle locally, consult the [official installation documentation](https://docs.gradle.org/current/userguide/installation.html) for instructions.

### Application Properties

There are few properties extracted into Spring Boot's `application.properties`. In particular, the behavior of forcibly overriding an existent `location.csv` file in the current directory is set using `csv.download.override` property, which is by default set to `true`. This can be configured before building with Gradle.

### Building

Using the Gradle wrapper, it only takes a single command to build the jar file:

    ./gradlew build

After setting up all the artifacts need (downloading of libraries from remote repositories, etc.), this will create a jar file in `build/libs` directory of the project root.

### Execution

To run the application, simply call:

    java -jar city-csv.jar <keyword>

 As mentioned above, this will create a `location.csv` file containing the CSV formatted location data in the current working directory. Depending on which default behavior was set before building, the application may or may not forcibly override an existent output file. To alter the default behavior the `--force` option can be used:

    java -jar city-csv.jar --force=false <keyword>

 With `--force` option set to `false`, the application will throw an exception when an outpufile with the given name already exists in the current directory.

## Developer Notes

There are are few sanity checks implemented in the version of the app as of writing. However, it has not been tested to cover each and every usage scenarios. It also prints a not so user friendly stacktrace when it can't override an existent output file. This was left as is because there are other concerns in handling such errors, particularly in whether to exit the app with a code other than 0 (e.g. `System.exit(1)`) in such cases.