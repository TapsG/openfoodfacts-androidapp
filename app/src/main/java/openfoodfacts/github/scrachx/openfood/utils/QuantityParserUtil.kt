package openfoodfacts.github.scrachx.openfood.utils

import android.util.Log
import android.widget.Spinner
import android.widget.TextView

fun isModifierEqualsToGreaterThan(view: CustomValidatingEditTextView) = isModifierEqualsToGreaterThan(view.modSpinner!!)

fun isModifierEqualsToGreaterThan(text: Spinner) = GREATER_THAN == MODIFIERS[text.selectedItemPosition]

fun TextView.isBlank() = text.toString().isBlank()

fun TextView.isNotBlank() = !isBlank()

/**
 * Retrieve the float value from strings like "> 1.03"
 *
 * @param initText value to parse
 * @return the float value or null if not correct
 */
fun getFloatValue(initText: String?) = getDoubleValue(initText)?.toFloat()

fun containFloatValue(text: String?) = getFloatValue(text) != null

/**
 * @return the float value or null if not correct
 * @see .getFloatValue
 */
fun TextView.getFloatValue(): Float? {
    if (text == null) return null
    return getFloatValue(text.toString())
}

fun TextView.getFloatValueOr(defaultValue: Float) = getFloatValue() ?: defaultValue

fun TextView.containFloatValue() = containFloatValue(text?.toString())

/**
 * Retrieve the float value from strings like "> 1.03"
 *
 * @param initText value to parse
 * @return the float value or null if not correct
 */
fun getDoubleValue(initText: String?): Double? {
    if (initText.isNullOrBlank()) return null
    try {
        return initText.trim().replace(",", ".").toDouble()
    } catch (ex: NumberFormatException) {
        Log.w("Utils", "Can't parse text '$initText'")
    }
    return null
}

fun containDoubleValue(text: String?) = getDoubleValue(text) != null

/**
 * @return the float value or null if not correct
 * @see .getFloatValue
 */
fun TextView.getDoubleValue() = getDoubleValue(text?.toString())