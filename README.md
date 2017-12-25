# HackerNews


## Technical Requirements
----
* Support API 11 to 25
* Use Proguard to obfuscate
* Use Github
* Use CI/CD to run all the tests and generate code coverage 


### Steps to generate code coverage report
----

Go to project root directory

To generate coverage report for debug build:

```sh
$ ./gradlew createDebugUnitTestCoverageReport
$ cd app/build/reports/jacoco/createDebugUnitTestCoverageReport/html
```

To generate coverage report for release build:

```sh
$ ./gradlew createDebugUnitTestCoverageReport
$ cd app/build/reports/jacoco/createReleaseUnitTestCoverageReport/html
```

To view the report from terminal

```sh
$ open index.html
```

> or double click index.html to open with default browser.