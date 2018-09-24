package level5

import fs2.{Pipe, Pull, Stream}

object impl {

  /**
    * Emits the first `n` elements of this stream
    */
  def takeS[F[_], O](n: Long): Pipe[F, O, O] =
    in => in.scanChunksOpt(n) { n =>
      if (n <= 0) None
      else Some(c => c.size match {
        case m if m < n => (n - m, c)
        case m => (0, c.take(n.toInt))
      })
    }

  /**
    * Emits the first `n` elements of this stream
    */
  def takeP[F[_], O](n: Long): Pipe[F, O, O] = {
    def go(s: Stream[F, O], n: Long): Pull[F, O, Unit] = {
      s.pull.uncons.flatMap[F, O, Unit] {
        case Some((hd, tl)) =>
          hd.size match {
            case m if m <= n =>
              Pull.output(hd) >> go(tl, n - m)
            case _ =>
              Pull.output(hd.take(n.toInt)) >> Pull.done
          }
        case None => Pull.done
      }
    }

    in => go(in, n).stream
  }
}
