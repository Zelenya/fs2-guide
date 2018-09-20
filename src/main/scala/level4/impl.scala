package level4

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

  /**
    * Runs an effect and ignores its output
    */
  def eval_[F[_], A](fa: F[A]): Stream[F, INothing] = ???

  /**
    * Catches any errors produced by a stream
    */
  def attempt[F[_], O](s: Stream[F, O]): Stream[F, Either[Throwable, O]] = ???
}
