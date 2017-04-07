package com.osinka.i18n

import java.text.MessageFormat
import java.util.{ResourceBundle, Locale}

/** Messages externalization
  *
  * == Overview ==
  * You would use it like so:
  *
  * {{{
  * Localized(user) { implicit lang =>
  *   val error = Messages("error")
  * }
  * }}}
  *
  * Messages are stored in `messages_XXX.txt` files in UTF-8 encoding in resources.
  * The lookup will fallback to default file `messages.txt` if the string is not found in
  * the language-specific file.
  *
  * Messages are formatted with `java.text.MessageFormat`.
  */
trait Messages {
  val FileName = "messages"
  val FileExt = "txt"

  /** get the message w/o formatting */
  def raw(msg: String)(implicit lang: Lang): String = {
    val bundle = ResourceBundle.getBundle(FileName, lang.locale, UTF8BundleControl)
    bundle.getString(msg)
  }

  def apply(msg: String, args: Any*)(implicit lang: Lang): String = {
    new MessageFormat(raw(msg), lang.locale).format(args.map(_.asInstanceOf[java.lang.Object]).toArray)
  }
}

object Messages extends Messages

// @see https://gist.github.com/alaz/1388917
// @see http://stackoverflow.com/questions/4659929/how-to-use-utf-8-in-resource-properties-with-resourcebundle
private[i18n] object UTF8BundleControl extends ResourceBundle.Control {
  val Format = "properties.utf8"

  override def getFormats(baseName: String): java.util.List[String] = {
    import collection.JavaConverters._

    Seq(Format).asJava
  }

  override def getFallbackLocale(baseName: String, locale: Locale) =
    if (locale == Locale.getDefault) null
    else Locale.getDefault

  override def newBundle(baseName: String, locale: Locale, fmt: String, loader: ClassLoader, reload: Boolean): ResourceBundle = {
    import java.util.PropertyResourceBundle
    import java.io.InputStreamReader

    // The below is an approximate copy of the default Java implementation
    def resourceName = toResourceName(toBundleName(baseName, locale), Messages.FileExt)

    def stream =
      if (reload) {
        for {url <- Option(loader getResource resourceName)
             connection <- Option(url.openConnection)}
        yield {
          connection.setUseCaches(false)
          connection.getInputStream
        }
      } else
        Option(loader getResourceAsStream resourceName)

    (for {format <- Option(fmt) if format == Format
          is <- stream}
     yield new PropertyResourceBundle(new InputStreamReader(is, "UTF-8"))).orNull
  }
}
