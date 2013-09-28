package nearlyCompleteBinaryTree

import org.scalacheck.{Gen, Prop}

class NearlyCompleteBinaryTreeTest extends BaseSpecification {

  def genValues: Gen[List[Int]] = for {
    v <- Gen.listOf(Gen.choose[Int](-100, 100))
  } yield v

  "random values" in {
    Prop.forAll(genValues) {
      (values: List[Int]) =>
        val tree = NearlyCompleteBinaryTree(values: _*)
        tree.toList must be_==(values.sorted)
    }
  }

  "find values" in {
    Prop.forAll(genValues) {
      (values: List[Int]) =>
        val tree = NearlyCompleteBinaryTree(values: _*)
        val set = values.toSeq
        values.foreach {
          v =>
            tree.find(v) must be_!=(-1)
            tree.contains(v - 1) must be_==(set.contains(v - 1))
            tree.contains(v + 1) must be_==(set.contains(v + 1))
        }
        ok
    }
  }
}
