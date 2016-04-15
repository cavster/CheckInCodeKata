/**
 * Created by Colm on 14/04/2016.
 */

class CheckoutKata(itemRules: List[ItemRules]) {

  val itemsRulesAsMap = itemRules.map(ir => ir.SKU -> ir).toMap
  var Items = scala.collection.mutable.ListBuffer.empty[String]

  def scanItem(item: String) = Items += item

  def getTotalPrice(): Double = {
    calculateTotalPrice(Items.toList)
  }

  def calculateTotalPrice(items: List[String]) = {
    val itemsWithRulesToCount = items.map(i => itemsRulesAsMap(i)).groupBy(i => i).map(ir => (ir._1, ir._2.length))
    itemsWithRulesToCount.map(ir => ir._1 match {
      case ItemRules(_, up, None) => up * ir._2
      case ItemRules(_, up, Some(sr)) => calculateDiscountedPrice(sr, ir._2, up)
    }).sum
  }

  def calculateDiscountedPrice(itemSpecialRules: SpecialRules,
                               quantityOfItems: Int,
                               unitPrice: Double): Double = {
    val discountedGoods = quantityOfItems / itemSpecialRules.quantityForDiscount * itemSpecialRules.setPrice
    discountedGoods + (quantityOfItems % itemSpecialRules.quantityForDiscount) * unitPrice
  }
}

case class ItemRules(SKU: String, unitPrice: Double, specialPrice: Option[SpecialRules])

case class SpecialRules(quantityForDiscount: Int, setPrice: Double)