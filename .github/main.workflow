workflow "Push" {
  resolves = [
    "Test JDK8",
    "Test JDK11",
    "Test JDK12",
  ]
  on = "push"
}

workflow "Release" {
  resolves = [
    "Test JDK8",
    "Test JDK11",
    "Test JDK12",
    "Publish",
  ]
  on = "release"
}

workflow "Pull Request" {
  resolves = [
    "Test JDK8",
    "Test JDK11",
    "Test JDK12",
  ]
  on = "pull_request"
}

action "Test JDK8" {
  uses = "MrRamych/gradle-actions/openjdk-8@2.1"
  args = "test -x dokka"
}

action "Test JDK11" {
  uses = "MrRamych/gradle-actions/openjdk-11@2.1"
  args = "test -x dokka"
}

action "Test JDK12" {
  uses = "MrRamych/gradle-actions/openjdk-12@2.1"
  args = "test -x dokka"
}

action "Publish" {
  uses = "MrRamych/gradle-actions/openjdk-8@2.1"
  args = "publish -x test"
  needs = ["branch-filter"]
  secrets = ["GITHUB_TOKEN", "BINTRAY_KEY"]
}

action "branch-filter" {
  uses = "actions/bin/filter@master"
  args = "branch master"
  needs = ["Test JDK8", "Test JDK11", "Test JDK12"]
}
