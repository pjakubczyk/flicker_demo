# flicker_demo
A demo project to be in shape
[![Build Status](https://travis-ci.org/pjakubczyk/flickr_demo.svg?branch=master)](https://travis-ci.org/pjakubczyk/flickr_demo)

# provide flickr API KEY
Place your flickr api key in `flickr.key` so gradle build script can pass it to program.

# build project
There are two main `gradle` tasks:
1. `gradlew ciPullRequest` - Run on every PR on TravisCI. It assembles the project, run unit tests and lint.
2. `gradlew qaCheck` - All above plus espresso tests.

# run project
Use `gradlew installDebug` task to assemble and install application on connected device.
