package level6

import fs2.Stream

object impl {

  /**
    * Emits the longest prefix for which all elements test true according to predicates
    */
  def takeWhile[F[_], O](s: Stream[F, O])(p: O => Boolean): Stream[F, O] = ???

  /**
    * Emits the separator between every pair of elements in the stream
    */
  def intersperse[F[_], O](s: Stream[F, O])(separator: O): Stream[F, O] = ???

  /**
    * Left fold which outputs all intermediate results
    */
  def scan[F[_], O, O2](s: Stream[F, O])(z: O2)(f: (O2, O) => O2): Stream[F, O2] = s.scan(z)(f)
}
