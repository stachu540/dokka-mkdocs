object ThisProject {
    const val repoSlug = "stachu540/dokka-mkdocs"
    const val githubUrl = "https://github.com/$repoSlug"
    const val ciUrl = "https://travis-ci.com/$repoSlug"
    const val scmHttps = "scm:git:$githubUrl.git"
    const val scmSsh = "scm:git:git@github.com:$repoSlug.git"

    const val dlUrl = "$githubUrl/releases"
    const val mitLicense = "$githubUrl/blob/master/LICENCE.md"
    const val issues = "$githubUrl/issues"
    const val pullRequests = "$githubUrl/pulls"
    const val actions = "$githubUrl/actions"

    const val webUrl = githubUrl
}
