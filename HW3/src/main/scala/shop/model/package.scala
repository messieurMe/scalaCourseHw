package shop

package object model {
  opaque type Barcode = String

  object Barcode:
    def apply(value: String): Barcode = value
  end Barcode

  opaque type LoyaltyNumber = String

  object LoyaltyNumber:
    def apply(value: String): LoyaltyNumber = value
  end LoyaltyNumber

  opaque type Price = Int

  extension (x: Price)
    infix def plus(y: Price): Price = x + y
    infix def discount(y: Discount): Price = Math.round(x * y)
    infix def normalDiscount(y: Discount): Price = Math.round(x * (1f - y))

  object Price:
    def apply(value: Int): Option[Price] =
      if value >= 0 then Some(value) else None

    def zero: Price = 0
  end Price

  opaque type Discount = Float

  object Discount:
    def apply(value: Float): Option[Discount] =
      if value >= 0.0f && value <= 1.0f then Some(value) else None
  end Discount

  extension (x: Discount) def max(y: Discount): Discount = Discount(math.max(x, y)).get

  opaque type ItemName = String

  object ItemName:
    def apply(value: String): ItemName = value
  end ItemName
}
