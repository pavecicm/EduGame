package hr.fer.edugame.ui.shared

import android.content.Context
import timber.log.Timber
import java.io.BufferedReader
import java.io.InputStreamReader

class WordsUtil {

    private val words: MutableList<String> = mutableListOf()

    constructor(context: Context) {
        var reader: BufferedReader? = null
        try {
            reader = BufferedReader(
                InputStreamReader(context.assets.open("croatian-wordlist.txt"))
            )

            var line: String
            do {
                line = reader.readLine()
                words.add(line)
            } while (line != null)
        } catch (e: Exception) {
            Timber.e(e)
        } finally {
            if (reader != null) {
                try {
                    reader.close()
                } catch (e: Exception) {
                    Timber.e(e)
                }
            }
        }
    }

    fun checkIfWordExists(word: String) =
        words.contains(word.toLowerCase())
}