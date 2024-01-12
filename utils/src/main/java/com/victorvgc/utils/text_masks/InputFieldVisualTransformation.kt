package com.victorvgc.utils.text_masks

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.core.text.isDigitsOnly
import com.victorvgc.utils.extensions.toText
import kotlin.math.max


class TextMaskTransformation(private val maskFilter: (AnnotatedString) -> TransformedText) :
    VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        return maskFilter(text)
    }

}

object NumberAppearance {

    // ##.###
    fun numberMaskFilter(text: AnnotatedString): TransformedText {
        val originalText = text.text.trim()

        if (originalText.isEmpty()) {

            return TransformedText(text, OffsetMapping.Identity)
        }

        if (originalText.isDigitsOnly().not()) {

            return TransformedText(text, OffsetMapping.Identity)
        }

        val formattedText = originalText.toLong().toText()

        return TransformedText(
            AnnotatedString(formattedText),
            NumberOffsetMapping(originalText, formattedText)
        )
    }

    // ##.###,00
    fun currencyMaskFilter(text: AnnotatedString): TransformedText {
        val originalText = text.text.trim()

        val intPart = if (originalText.length > 2) {
            originalText.subSequence(0, originalText.length - 2)
        } else {
            "0"
        }

        var fractionPart = if (originalText.length >= 2) {
            originalText.subSequence(originalText.length - 2, originalText.length)
        } else {
            originalText
        }
        if (fractionPart.length < 2) {
            fractionPart = fractionPart.padStart(2, '0')
        }

        val thousandsReplacementPattern = Regex("\\B(?=(?:\\d{3})+(?!\\d))")
        val formattedIntWithThousandsSeparator =
            intPart.replace(
                thousandsReplacementPattern,
                "."
            )
        val newText = AnnotatedString(
            "$formattedIntWithThousandsSeparator,$fractionPart",
            text.spanStyles,
            text.paragraphStyles
        )

        return TransformedText(
            newText,
            CurrencyOffsetMapping(intPart.length, originalText.length)
        )
    }
}

internal class NumberOffsetMapping(
    originalText: String,
    formattedText: String,
) : OffsetMapping {
    private val originalLength: Int = originalText.length
    private val indexes = findDigitIndexes(originalText, formattedText)

    private fun findDigitIndexes(firstString: String, secondString: String): List<Int> {
        val digitIndexes = mutableListOf<Int>()
        var currentIndex = 0
        for (digit in firstString) {
            val index = secondString.indexOf(digit, currentIndex)
            if (index != -1) {
                digitIndexes.add(index)
                currentIndex = index + 1
            } else {
                return emptyList()
            }
        }
        return digitIndexes
    }

    override fun originalToTransformed(offset: Int): Int {
        if (offset >= originalLength) {
            return indexes.last() + 1
        }
        return indexes[offset]
    }

    override fun transformedToOriginal(offset: Int): Int {
        return indexes.indexOfFirst { it >= offset }.takeIf { it != -1 } ?: originalLength
    }

}

internal class CurrencyOffsetMapping(
    private val originalIntegerLength: Int,
    private val originalTextLength: Int
) : OffsetMapping {
    override fun originalToTransformed(offset: Int): Int {
        return when (offset) {
            0, 1, 2 -> 4
            else -> offset + 1 + calculateThousandsSeparatorCount(originalIntegerLength)
        }
    }

    override fun transformedToOriginal(offset: Int): Int {

        return originalTextLength
    }

    private fun calculateThousandsSeparatorCount(
        intDigitCount: Int
    ) = max((intDigitCount - 1) / 3, 0)

}
