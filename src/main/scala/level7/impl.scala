package level7

import cats.effect.Concurrent
import fs2.Stream

object impl {

  /**
    * Interleaves the two inputs nondeterministically, halts as soon as _either_ branch halts
    */
  def mergeHaltBoth[F[_]: Concurrent, O](s1: Stream[F, O], s2: Stream[F, O]): Stream[F, O] = ???
}
