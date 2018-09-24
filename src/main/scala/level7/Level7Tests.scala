package level7

import cats.effect.{ContextShift, IO, Timer}
import cats.implicits._
import fs2.Stream
import level7.impl._
import secret.zstuff._
import testz._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._

object Level7Tests {

  /**
    * Level 7. Basic concurrency
    *
    * 1. Read the section
    * [[http://fs2.io/guide.html#concurrency Concurrency]]
    *
    * 2. Fix the method implementations in [[level7.impl]]
    */
  def tests[T](harness: Harness[T]): T = {
    import harness._
    namedSection("mergeHaltBoth")(
      test("halts when 1st halts") { () =>
        implicit val ioContextShift: ContextShift[IO] = IO.contextShift(global)
        implicit val timer: Timer[IO] = IO.timer(global)

        val s1 = Stream.range(1, 10).covary[IO]
        val s2 = Stream.sleep_(1.minute) ++ Stream(1).repeat

        assertEqual(
          mergeHaltBoth(s1, s2).compile.toList.unsafeRunSync(),
          s1.compile.toList.unsafeRunSync()
        )
      },
      test("at least all the elements from 1 stream") { () =>
        implicit val ioContextShift: ContextShift[IO] = IO.contextShift(global)

        val result = mergeHaltBoth(
          Stream(1).covary[IO].repeat.take(5),
          Stream(2).covary[IO].repeat.take(5)
        ).compile.toList.unsafeRunSync()

        assert(result.count(_ === 1) === 5 || result.count(_ === 2) === 5)
      },
      test("\n 0 - - 2 \n - 1 - - - 42") { () =>
        implicit val ioContextShift: ContextShift[IO] = IO.contextShift(global)
        implicit val timer: Timer[IO] = IO.timer(global)

        import Stream._
        val s1 = emit(0) ++ sleep_(200.millis) ++ emit(2)
        val s2 = sleep_(100.millis) ++ emit(1) ++ sleep_(300.millis) ++ emit(42)

        assertEqual(
          mergeHaltBoth(s1, s2).compile.toList.unsafeRunSync(),
          List(0, 1, 2)
        )
      }
    )
  }

  def main(args: Array[String]): Unit = {
    val output = tests(tutorialHarness)((), Nil)
    println("Level 7. Results")
    output.print()
  }
}
