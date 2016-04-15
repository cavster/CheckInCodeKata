/**
 * Created by Colm on 14/04/2016.
 */

import org.scalatest._

class CheckoutKataTest extends FlatSpec {

  val testRulesList: List[ItemRules] = List(ItemRules("A", 50.0, Some(SpecialRules(3, 130))),
    ItemRules("B", 30.0, Some(SpecialRules(3, 300))), ItemRules("C", 20.0, None),
    ItemRules("D", 15.0, None))

  val checkoutKataTest = new CheckoutKata(testRulesList)

  "checkoutTransaction" should "return zero if their are no items" in {
    assert(checkoutKataTest.getTotalPrice() == 0)
  }
  "checkoutTransaction" should "return the item price" in {
    checkoutKataTest.scanItem("C")
    assert(checkoutKataTest.getTotalPrice() == 20.0)
  }
  "checkoutTransaction" should "sum the item prices up with no speical rules" in {
    checkoutKataTest.scanItem("D")
    assert(checkoutKataTest.getTotalPrice() == 35.0)
  }
  "checkoutTransaction" should "should  apply the special rules if they apply" in {
    checkoutKataTest.scanItem("A")
    checkoutKataTest.scanItem("A")
    checkoutKataTest.scanItem("A")
    assert(checkoutKataTest.getTotalPrice() == 165.0)
  }
  "checkoutTransaction" should "should not apply the special rules if they did not meet it" in {
    checkoutKataTest.scanItem("A")
    assert(checkoutKataTest.getTotalPrice() == 215.0)
  }
  "checkoutTransaction" should "should not care what order they put in " in {
    checkoutKataTest.scanItem("B")
    checkoutKataTest.scanItem("B")
    checkoutKataTest.scanItem("A")
    checkoutKataTest.scanItem("A")
    checkoutKataTest.scanItem("B")
    checkoutKataTest.scanItem("A")

    assert(checkoutKataTest.getTotalPrice() == 645.0)
  }


}
