workflow "Push" {
  resolves = [
    "Test"
  ]
  on = "push"
}

workflow "Release" {
  resolves = [
    "Test",
    "Publish",
  ]
  on = "release"
}

workflow "Pull Request" {
  resolves = [
    "Test"
  ]
  on = "pull_request"
}

action "Test" {
  uses = "MrRamych/gradle-actions/openjdk-8@2.1"
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
  needs = ["Test"]
}
