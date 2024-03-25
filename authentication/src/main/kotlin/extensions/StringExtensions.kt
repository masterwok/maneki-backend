package extensions

private val alphaNumericCharacters = ('A'..'Z') + ('a'..'z') + ('0'..'9')

fun String.Companion.randomAlphaNumeric(length: Int): String = List(length) {
    alphaNumericCharacters.random()
}.joinToString("")
