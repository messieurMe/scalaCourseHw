package shop.error

import shop.model.{Barcode, LoyaltyNumber}

// Создайте свою модель ошибок
sealed trait ShopError {
  val message: String
}

class NoSuchCardException(card: LoyaltyNumber) extends ShopError {
  override val message: String = s"There is no card with id '$card'"
}

class NoSuchItemError(barcode: Barcode) extends ShopError {
  override val message: String = s"Cannot find item with barcode '$barcode'"
}
