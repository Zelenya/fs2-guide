package level8

import fs2._

object impl {

  /**
    * Maps the supplied stateful function over each element,
    * outputting the final state and the accumulated outputs
    */
  def evalMapAccumulate[F[_], S, O, O2](s1: Stream[F, O])(init: S)(
    f: (S, O) => F[(S, O2)]): Stream[F, (S, O2)] = {
    def go(s: S, in: Stream[F, O]): Pull[F, (S, O2), Unit] =
      ???

    go(init, s1).stream
  }

  /**
    * Emits only inputs which match the supplied predicate
    */
  def filter[F[_], O](s1: Stream[F, O])(f: O => Boolean): Stream[F, O] = ???
}
