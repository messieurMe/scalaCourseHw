package shop

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import shop.ShopSpec.*
import shop.model.*
import shop.model.Category.Food

import scala.List

class ShopSpec extends AnyFlatSpec with Matchers {
  "Shop empty" should "return error for any product" in {
    Shop.empty.createReceipt(List(Barcode("123")), None).isLeft shouldBe true
  }

  it should "ok for empty list" in {
    Shop.empty.createReceipt(List.empty, None) shouldBe Right(Receipt(List.empty))
  }

  it should "ok for empty list, but wrong card" in {
    Shop.empty.createReceipt(List.empty, Some(LoyaltyNumber("12345678"))) shouldBe Right(Receipt(List.empty))
  }

  "Shop with products" should "be free for owner" in {
    testShop.createReceipt(List(chipsCode), Some(ownerCard)) match
      case Left(error)  => fail(s"Expected Right, but got Left($error")
      case Right(value) => Receipt.total(value) shouldBe Price.zero
  }

  it should "correctly calculate total price with discount" in {
    testShop
      .addCategoryDiscount(Category.Food, Discount(0.2f).get)
      .createReceipt(List(chipsCode, fishCode, catFoodCode), None) match
      case Left(error)  => fail(s"Expected Right, but got Left($error")
      case Right(value) => Receipt.total(value) shouldBe Price(Math.round((100 + 99 + 149) * (1 - 0.2f))).get
  }

  it should "correctly calculate total price with personal discount" in {
    testShop
      .addCategoryDiscount(Category.Food, Discount(0.05f).get)
      .createReceipt(List(chipsCode, fishCode, catFoodCode, computerCode), Some(wonkaCard)) match
      case Left(error)  => fail(s"Expected Right, but got Left($error")
      case Right(value) => Receipt.total(value) shouldBe Price(Math.round((100 + 99 + 149 + 1242) * (1 - 0.1f))).get

    testShop
      .addCategoryDiscount(Category.Food, Discount(0.2f).get)
      .createReceipt(List(chipsCode, fishCode, catFoodCode, computerCode), Some(wonkaCard)) match
      case Left(error) => fail(s"Expected Right, but got Left($error")
      case Right(value) =>
        Receipt.total(value) shouldBe Price(Math.round((100 + 99 + 149) * (1 - 0.2f) + 1242 * (1 - 0.1f))).get
  }

  it should "return error for unknown product" in {
    testShop
      .createReceipt(List(chipsCode, fishCode, unknownCode, computerCode), Some(wonkaCard))
      .isLeft shouldBe true
  }

  it should "return error for unknown card" in {
    testShop
      .createReceipt(List(chipsCode, computerCode), Some(unknownCard))
      .isLeft shouldBe true
  }

  it should "return readable error if not all categories available" in {
    testShop
      .createReceipt(List(Barcode("x")), None) match
      case Left(value)  => value.message shouldBe "Cannot find item with barcode 'x'"
      case Right(value) => fail("Expected error")
  }
  it should "return readable error if loyal number is absent" in {
    testShop
      .createReceipt(List(chipsCode), Some(LoyaltyNumber("x"))) match
      case Left(value)  => value.message shouldBe "There is no card with id 'x'"
      case Right(value) => fail("Expected error")
  }
}

object ShopSpec:
  private val wonkaCard = LoyaltyNumber("wonka")
  private val ownerCard = LoyaltyNumber("owner")
  private val makaCard = LoyaltyNumber("maka")
  private val unknownCard = LoyaltyNumber("unknown")

  private val chipsCode = Barcode("123")
  private val fishCode = Barcode("124")
  private val catFoodCode = Barcode("125")
  private val computerCode = Barcode("42")
  private val unknownCode = Barcode("unknown")

  private val testShop =
    Shop.empty
      .addItem(chipsCode, Item(ItemName("chips"), Price(100).get, Category.Food))
      .addItem(fishCode, Item(ItemName("fish"), Price(99).get, Category.Food))
      .addItem(catFoodCode, Item(ItemName("cat foos"), Price(149).get, Category.Food))
      .addItem(Barcode("321"), Item(ItemName("t-shirt"), Price(1500).get, Category.Clothes))
      .addItem(computerCode, Item(ItemName("computer"), Price(1242).get, Category.Electronics))
      .addLoyaltyCard(wonkaCard, Discount(0.1f).get)
      .addLoyaltyCard(ownerCard, Discount(1.0f).get)
      .addLoyaltyCard(makaCard, Discount(0.01f).get)
end ShopSpec
