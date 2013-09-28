package nearlyCompleteBinaryTree

import org.scalameter.api._

object BinarySearchVsTreeBenchmark extends PerformanceTest.Quickbenchmark {
  val sizes: Gen[Int] = Gen.range("size")(30000, 1530000, 300000)

  val ranges: Gen[Range] = for {
    size <- sizes
  } yield 0 until size

  val values: Gen[IndexedSeq[Int]] = for {
    size <- sizes
    random = new java.util.Random(0L)
  } yield {
    IndexedSeq.tabulate(size) {
      i => random.nextInt()
    }
  }

  val trees: Gen[(NearlyCompleteBinaryTree[Int], List[Int])] = for {
    v <- values
  } yield {
    val tree = NearlyCompleteBinaryTree(v: _*)
    tree -> tree.toList
  }

  performance of "Nearly complete binary tree" in {
    measure method "index of element" in {
      using(trees) in {
        case (t, elems) =>
          for {
            e <- elems
          } {
            t.find(e)
          }
      }
    }
  }
  
  performance of "Binary search" in {
    measure method "index of element" in {
      using(values) in {
        elems =>
          for {
            e <- elems
          } {
            BinarySearch.binaryIntervalSearch(elems, e)
          }
      }
    }
  }

}
