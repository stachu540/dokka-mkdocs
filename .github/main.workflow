workflow "Push" {
  resolves = [
    "Test",
    "Publish",
  ]
  on = "push"
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
  secrets = ["BINTRAY_KEY"]
}

action "branch-filter" {
  uses = "actions/bin/filter@master"
  args = "tag"
  needs = ["Test"]
}
