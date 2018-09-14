package levelX

import fs2.{INothing, Stream}

object impl {

  /**
    * Repeats a stream indefinitely
    *
    * Implement me and others!!!
    */
  def repeat[F[_], O](s: Stream[F, O]): Stream[F, O] = ???

  /**
    * Strips all output from a stream
    */
  def drain[F[_], O](s: Stream[F, O]): Stream[F, INothing] = ???

}
