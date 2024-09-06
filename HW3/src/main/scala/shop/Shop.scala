package shop

import shop.error.{NoSuchCardException, NoSuchItemError, ShopError}
import shop.model.*

trait Shop {

  /** Данный метод создаёт чек по списку покупок, исходя из стоимости товаров, постоянных скидок на категории и скидки
    * по бонусной карте. Скидка рассчитывается в пользу покупателя (т.е. если скидка по бонусной карте больше чем скидка
    * по категории, то применяется скидка по карте, иначе применяется скидка по категории). Если не существует какого-то
    * товара или бонусной карты, то возвращается ошибка.
    *
    * @param items
    *   список с покупаемыми товарами.
    * @param bonusCard
    *   уникальный номер бонусной карты.
    * @return
    *   в случае успеха - чек, в противном случае - ошибка с подробным описанием.
    */
  def createReceipt(
    items: List[Barcode],
    bonusCard: Option[LoyaltyNumber]
  ): Either[ShopError, Receipt]

  /** Добавить/изменить товар.
    *
    * @param code
    *   уникальный код товара.
    * @param item
    *   добавляемый товар.
    * @return
    *   если товар с указанным кодом уже есть, то обновляет его цену и категорию, иначе добавляет новый товар.
    */
  def addItem(
    code: Barcode,
    item: Item
  ): Shop

  /** Устанавливает скидку по бонусной карте покупателя.
    *
    * @param number
    *   уникальный номер карты лояльности клиента.
    * @param discount
    *   размер скидки предоставляемой по этой карте на все товары.
    * @return
    *   если такая карта существует, то устанавливает новый размер скидки, иначе создает новую карту.
    */
  def addLoyaltyCard(
    number: LoyaltyNumber,
    discount: Discount
  ): Shop

  /** Устанавливает скидку на товары в выбранной категории.
    *
    * @param category
    *   категория к которой теперь нужно применять скидку.
    * @param discount
    *   размер применяемой скидки.
    */
  def addCategoryDiscount(
    category: Category,
    discount: Discount
  ): Shop
}

object Shop:
  def empty: Shop = ShopImpl(Map(), Map(), Map())

  // Вы можете добавить дополнительные конструкторы (например с первияной инициализацией товаров и скидок),
  // для облегчения тестирования.
end Shop

class ShopImpl(
  val shopItems: Map[Barcode, Item],
  val loyaltyCards: Map[LoyaltyNumber, Discount],
  val categoryDiscounts: Map[Category, Discount]
) extends Shop {

  private val noDiscount = Discount(0f).get
  override def createReceipt(items: List[Barcode], bonusCard: Option[LoyaltyNumber]): Either[ShopError, Receipt] = {

    val loyaltyCard: Either[ShopError, Discount] = bonusCard match
      case Some(value) =>
        if (loyaltyCards.contains(value)) Right(loyaltyCards(value)) else Left(NoSuchCardException(value))
      case None => Right(noDiscount)

    val itemsData: Seq[Either[ShopError, Item]] = items
      .map(barcode => if (shopItems.contains(barcode)) Right(shopItems(barcode)) else Left(NoSuchItemError(barcode)))

    if (loyaltyCard.isLeft && itemsData.nonEmpty) {
      loyaltyCard.asInstanceOf[Either[ShopError, Receipt]]
    } else if (itemsData.exists(_.isLeft)) {
      itemsData.find(_.isLeft).get.asInstanceOf[Left[ShopError, Receipt]]
    } else {
      Right(
        Receipt(
          itemsData
            .map(x => x.asInstanceOf[Right[ShopError, Item]].value)
            .map { value =>
              value.copy(price =
                value.price.normalDiscount(
                  categoryDiscounts
                    .getOrElse(value.category, noDiscount)
                    .max(loyaltyCard.getOrElse(noDiscount))
                )
              )
            }
            .toList
        )
      )
    }
  }

  override def addItem(code: Barcode, item: Item): Shop =
    ShopImpl(shopItems + (code -> item), loyaltyCards, categoryDiscounts)

  override def addLoyaltyCard(number: LoyaltyNumber, discount: Discount): Shop =
    ShopImpl(shopItems, loyaltyCards + (number -> discount), categoryDiscounts)

  override def addCategoryDiscount(category: Category, discount: Discount): Shop =
    ShopImpl(shopItems, loyaltyCards, categoryDiscounts + (category -> discount))
}
