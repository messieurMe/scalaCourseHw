package shop.model

case class Receipt(
  items: List[Item]
)

object Receipt:
  def total(receipt: Receipt): Price =
    receipt.items
      .map(_.price)
      .fold(Price.zero)(_ plus _)
end Receipt
