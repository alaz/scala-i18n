organization := "com.osinka.i18n"

name := "scala-i18n"

homepage := Some(url("https://github.com/osinka/scala-i18n"))

startYear := Some(2014)

val Scala211 = "2.11.12"
val Scala212 = "2.12.19"
val Scala213 = "2.13.14"
val Scala3 = "3.5.0"

scalaVersion := Scala213

crossScalaVersions := Seq(Scala211, Scala212, Scala213, Scala3)

licenses += "Apache License, Version 2.0" -> url("http://www.apache.org/licenses/LICENSE-2.0")

organizationName := "Osinka"

description := """Play-like internationalized messages for any Scala"""

scalacOptions ++= List("-deprecation", "-unchecked", "-feature")

libraryDependencies ++= Seq("org.scalatest" %% "scalatest" % "3.2.19" % Test)

credentials += {
  val file =
    if (version.value.trim endsWith "SNAPSHOT") "credentials_osinka"
    else "credentials_sonatype"
  Credentials(Path.userHome / ".ivy2" / file)
}

pomIncludeRepository := { x => false }

publishTo := {
  Some(
    if (version.value.trim endsWith "SNAPSHOT")
      "Osinka Internal Repo" at "https://r.osinka.co/content/repositories/snapshots/"
    else
      "Sonatype OSS Staging" at "https://oss.sonatype.org/service/local/staging/deploy/maven2/"
  )
}

pomExtra := <xml:group>
    <developers>
      <developer>
        <id>alaz</id>
        <email>azarov@osinka.com</email>
        <name>Alexander Azarov</name>
        <timezone>Europe/Riga</timezone>
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
