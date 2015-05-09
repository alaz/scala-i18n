organization := "com.osinka.i18n"

name := "scala-i18n"

homepage := Some(url("https://github.com/osinka/scala-i18n"))

startYear := Some(2014)

scalaVersion := "2.11.2"

crossScalaVersions := Seq("2.11.2", "2.10.4")

licenses += "Apache License, Version 2.0" -> url("http://www.apache.org/licenses/LICENSE-2.0")

organizationName := "Osinka"

description := """Play-like internationalized messages for any Scala"""

scalacOptions ++= List("-deprecation", "-unchecked", "-feature")

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "2.2.2" % "test"
)

credentials <+= (version) map { version: String =>
  val file =
    if (version.trim endsWith "SNAPSHOT") "credentials_osinka"
    else "credentials_sonatype"
  Credentials(Path.userHome / ".ivy2" / file)
}

pomIncludeRepository := { x => false }

publishTo <<= (version) { version: String =>
  Some(
    if (version.trim endsWith "SNAPSHOT")
      "Osinka Internal Repo" at "http://repo.osinka.int/content/repositories/snapshots/"
    else
      "Sonatype OSS Staging" at "https://oss.sonatype.org/service/local/staging/deploy/maven2/"
  )
}

useGpg := true

pomExtra := <xml:group>
    <developers>
      <developer>
        <id>alaz</id>
        <email>azarov@osinka.com</email>
        <name>Alexander Azarov</name>
        <timezone>+3</timezone>
      </developer>
      <developer>
        <id>lavrov</id>
        <email>lavrov@osinka.com</email>
        <name>Vitaly Lavrov</name>
        <timezone>+4</timezone>
      </developer>
    </developers>
    <scm>
      <connection>scm:git:git://github.com/osinka/scala-i18n.git</connection>
      <developerConnection>scm:git:git@github.com:osinka/scala-i18n.git</developerConnection>
      <url>http://github.com/osinka/scala-i18n</url>
    </scm>
    <issueManagement>
      <system>github</system>
      <url>http://github.com/osinka/scala-i18n/issues</url>
    </issueManagement>
  </xml:group>
