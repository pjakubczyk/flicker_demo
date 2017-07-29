# flicker_demo
A demo project to be in shape

# build project
There are two main `gradle` tasks:
1. `gradlew ciPullRequest` - Run on every PR on TravisCI. It assembles the project, run unit tests and lint.
2. `gradlew qaCheck` - All above plus espresso tests.

# run project
Use `gradlew installDebug` task to assemble and install application on connected device.
