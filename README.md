[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.osinka.i18n/scala-i18n_2.11/badge.png)](https://maven-badges.herokuapp.com/maven-central/com.osinka.i18n/scala-i18n_2.11)
[![Build Status](https://travis-ci.org/osinka/scala-i18n.svg?branch=master)](https://travis-ci.org/osinka/scala-i18n)

# Introduction

Small library to support i18n messages in Scala "just like in Play Framework".

* properties files
* in UTF-8 (vs. Java properties files in ISO 8859-1)
* formatted with `MessageFormat`

It provides a trivial library to "localize" application entities as well.

# Messages

Mostly just like in [Play
Framework](https://www.playframework.com/documentation/2.3.x/ScalaI18N) (the
differences are outlined below). Messages are in `messages_XXX.txt` (mind the
file extension, Play does not use any) files in UTF-8 encoding in the
application's resources (Play's messages reside in `/conf` ). There is a default
file `messages.txt` where the key is looked up when it cannot be found in the
language-specific file, e.g. `messages_en.txt` or `messages_ru.txt`.

The messages are formatted using Java's `java.util.MessageFormat`. Mind the
apostrophes (see the details in
[Javadoc](http://docs.oracle.com/javase/7/docs/api/java/text/MessageFormat.html))!

`Messages` call depend on the implicit parameter of type `Lang` which represents
the language. It's being used in `Localized` as well, see below.

You would use `Messages` so:

```scala
implicit val userLang = Lang("en")

val msg = Messages("greet")
```

# Localized

You may want to localize for some application's entity, e.g. a user or a
session. In this case, you may implicitly determine this entity's preferred
language:

```scala
case class User(id: Int, lang: Lang)

object User {
  implicit object userLocale extends Localized {
    override def locale(user: User) = user.lang
  }
}
```

and format the whole page or email for any user later:

```
val email =
  Localized(user) { implicit lang =>
    val greet = Messages("email.greet", user.fullName)
    val text = Messages("email.text")
    s"$greet $text"
  }
```

# Using

In SBT:

```
val i18n = "com.osinka.i18n" %% "scala-i18n" % "1.1.0"
```

# Credits

* https://gist.github.com/alaz/1388917
* http://stackoverflow.com/questions/4659929/how-to-use-utf-8-in-resource-properties-with-resourcebundle

# License

Apache 2.0
