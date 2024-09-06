package lexicon

import scala.collection.immutable.Set

trait Dictionary {

  /** Добавить в словарь новое слово.
    *
    * @param word
    *   слово для добавления.
    * @return
    *   если слово уже есть в словаре, игнорирует, иначе успешно добавляет слово.
    */
  def addWord(word: String): Dictionary

  /** Проверить текст на наличие неизвестных слов.
    *
    * @param text
    *   текст для проверки.
    * @return
    *   список слов, которых нет в словаре.
    */
  def findUnknownWords(text: String): List[String]
}

object Dictionary:

  def apply(words: List[String]): Dictionary = DictionaryImpl(words.map(_.toLowerCase()).toSet)

  def empty: Dictionary = DictionaryImpl(Set())
end Dictionary

class DictionaryImpl(private val dictionary: Set[String]) extends Dictionary {

  override def addWord(word: String): Dictionary = DictionaryImpl(dictionary + word.toLowerCase())

  override def findUnknownWords(text: String): List[String] = text
    .split("([^a-zA-Z]+)")
    .distinctBy(_.toLowerCase())
    .filter(word => !dictionary.contains(word.toLowerCase()))
    .toList
}

object Main {
  def main(args: Array[String]): Unit = {
    println(Dictionary(List()).findUnknownWords("Ab,ab"))
  }
}
