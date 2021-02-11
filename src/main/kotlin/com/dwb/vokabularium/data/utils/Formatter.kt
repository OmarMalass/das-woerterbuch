package com.dwb.vokabularium.data.utils


private val extraSpacesPattern = Regex("\\s+")
private val startTrailingSpaces = Regex("(^\\s)|(\\s$)")
private fun removeExtraSpaces(str: String): String {
    val f1 = startTrailingSpaces.replace(str, "")
    val f2 = extraSpacesPattern.replace(f1, " ")
    return f2
}

private val whitespacePattern = Regex("\\s")
fun normalizeWhitespace(str: String): String {
    val f1 = whitespacePattern.replace(str, " ")
    return f1
}

fun formatText(str: String, extraSpaces: Boolean = true, normWhitespaces: Boolean = true): String {
    var formatted = str

    if (extraSpaces)
        formatted = removeExtraSpaces(str)

    if (normWhitespaces)
        formatted = normalizeWhitespace(str)

    return formatted

}
