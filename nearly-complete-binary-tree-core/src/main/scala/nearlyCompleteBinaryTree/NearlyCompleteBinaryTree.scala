package nearlyCompleteBinaryTree

import scala.annotation.tailrec

case class NearlyCompleteBinaryTree[T](elems: IndexedSeq[T])(implicit ord: Ordering[T]) {

  import NearlyCompleteBinaryTree._
  import ord._

  def toList: List[T] = recurseInOrder(0)

  @tailrec
  final def find(e: T, index: Int = 0): Int = {
    if (index < elems.length) {
      val t: T = elems(index)
      if (t < e) {
        // value is smaller, need to go right
        find(e, rightChildIndexFor(index))
      } else if (t > e) {
        find(e, leftChildIndexFor(index))
      } else {
        index
      }
    } else {
      -1
    }
  }

  final def contains(e: T): Boolean = find(e) != -1

  private def recurseInOrder(i: Int = 0): List[T] = {
    if (i < elems.length) {
      val left: Int = leftChildIndexFor(i)
      val right: Int = rightChildIndexFor(i)
      recurseInOrder(left) ::: elems(i) :: recurseInOrder(right)
    } else {
      Nil
    }
  }

  override def toString: String = {

    val values = recurseInOrder()
    s"NearlyCompleteBinaryTree(${values.mkString(",")} : ${elems.mkString(",")})"
  }
}

// TODO: create tree / rebalance tree on the fly...
object NearlyCompleteBinaryTree {

  /**
   * Tree is organized according to BFS:
   * {{{
   *    1
   *  2   3
   * 4 5 6 7
   * }}}
   *
   * create empty tree with ceil(2^n) nodes
   * do bfs until you have enough elements (in order to get almost complete tree)
   * empty tree contains now shape of target tree
   * fill tree
   * walk tree and store in array
   */
  def apply[T](values: T*)(implicit ord: Ordering[T]): NearlyCompleteBinaryTree[T] = {

    val sorted = values.sorted(ord).toIndexedSeq

    val indices = treeIndices(sorted.size)

    val t = sorted.zip(indices).sortBy(_._2).unzip._1.toIndexedSeq

    new NearlyCompleteBinaryTree(t)
  }

  def treeIndices(size: Int): List[Int] = {
    def recurseInOrder(i: Int = 0): List[Int] = {
      if (i < size) {
        val left = leftChildIndexFor(i)
        val right = rightChildIndexFor(i)
        recurseInOrder(left) ::: i :: recurseInOrder(right)
      } else {
        Nil
      }
    }

    recurseInOrder()
  }

  def leftChildIndexFor(k: Int) = {
    2 * k + 1
  }

  def rightChildIndexFor(k: Int) = {
    2 * k + 2
  }

  def main(args: Array[String]) {
    val vs = Seq(23, 64, 77, 1, 4, 9, 78, 22 /*, 33, 45, 76, 99, 55, 43, 12, 33*/)
    val tree = NearlyCompleteBinaryTree(vs: _*)
    println(tree)
  }
}