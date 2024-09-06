package lexicon

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import scala.List

class DictionarySpec extends AnyFlatSpec with Matchers {
  "Dictionary empty" should "return all words" in {
    Dictionary.empty
      .findUnknownWords("Lorem ipsum dolor sit amet, lorem") should contain theSameElementsAs
      List("Lorem", "ipsum", "dolor", "sit", "amet")
  }

  it should "add new word" in {
    Dictionary.empty
      .addWord("lorem")
      .findUnknownWords("Lorem ipsum dolor sit amet, lorem") should contain theSameElementsAs
      List("ipsum", "dolor", "sit", "amet")
  }

  it should "not add new word if it already exists" in {
    Dictionary.empty
      .addWord("lorem")
      .addWord("Lorem")
      .addWord("lorem")
      .findUnknownWords("Lorem ipsum dolor! sit amet, lorem") should contain theSameElementsAs
      List("ipsum", "dolor", "sit", "amet")
  }

  it should "add few new word" in {
    Dictionary.empty
      .addWord("lorem")
      .addWord("Sit")
      .findUnknownWords("Lorem ipsum dolor! sit amet, lorem") should contain theSameElementsAs
      List("ipsum", "dolor", "amet")
  }

  it should "work with spaces" in {
    Dictionary.empty
      .addWord("lorem")
      .addWord("Sit")
      .findUnknownWords("Lorem ipsum dolor! sit\tamet\nlorem") should contain theSameElementsAs
      List("ipsum", "dolor", "amet")
  }

  "Dictionary" should "create dictionary from list of words" in {
    val dict = List("lorem", "nibh", "ipsum", "dolor", "sit", "amet", "erat")
    val dictionary = Dictionary(dict)

    dictionary.findUnknownWords(loremIpsum) should contain allOf ("ultricies", "justo", "Sed")
    dictionary.findUnknownWords(loremIpsum) should contain noElementsOf dict
  }

  "Dictionary" should "be able to add words after creation" in {
    val words = List("a")
    val dictionary = Dictionary(words).addWord("a").addWord("b")

    dictionary.findUnknownWords("a,b,c") should contain theSameElementsAs List("c")
  }

  private lazy val loremIpsum =
    """
      |Lorem ipsum dolor sit amet, consectetur adipiscing elit.
      |Pellentesque eget tincidunt augue. Sed at dui vitae velit
      |consequat congue. Sed euismod, justo id congue tincidunt,
      |nibh dui congue augue, non ultricies nibh erat non nibh.
      |""".stripMargin
}
