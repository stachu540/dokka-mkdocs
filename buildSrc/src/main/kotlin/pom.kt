import org.gradle.api.publish.maven.MavenPom
import org.gradle.api.publish.maven.MavenPomDeveloperSpec
import org.gradle.api.publish.maven.MavenPomLicenseSpec

fun MavenPom.default() {
    url.set(ThisProject.webUrl)
    issueManagement {
        system.set("GitHub")
        url.set(ThisProject.issues)
    }
    ciManagement {
        system.set("GitHub")
        url.set(ThisProject.ciUrl)
    }
    inceptionYear.set("2019")
    developers { all }
    licenses { mit("repo", ThisProject.mitLicense) }
    scm {
        connection.set(ThisProject.scmHttps)
        developerConnection.set(ThisProject.scmSsh)
        url.set(ThisProject.githubUrl)
    }
    distributionManagement { downloadUrl.set(ThisProject.dlUrl) }
}

fun MavenPomLicenseSpec.mit(distribution: String, url: String) {
    license {
        name.set("MIT Licence")
        this.distribution.set(distribution)
        this.url.set(url)
    }
}

val MavenPomDeveloperSpec.all: Unit
    get() {
        stachu540
    }