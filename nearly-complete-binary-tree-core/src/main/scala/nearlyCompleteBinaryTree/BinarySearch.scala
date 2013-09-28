package nearlyCompleteBinaryTree

object BinarySearch {

  def binaryIntervalSearch[T](v: IndexedSeq[T], x: T)(implicit ord: Ordering[T]): Int = {
    import ord._

    // perform binary search for sub-interval to which given value x belongs.
    var low = 0
    var high = v.length - 1
    while (high - low > 1) {
      val mid = (low + high) >>> 1 // same as (low + high) / 2 but safer
      if (x < v(mid))
        high = mid
      else
        low = mid
    }
    low
  }

}