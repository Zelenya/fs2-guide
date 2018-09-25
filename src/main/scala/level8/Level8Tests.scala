package level8

import cats.effect.IO
import cats.implicits._
import fs2.Stream
import level8.impl._
import secret.zstuff._
import testz._

object Level8Tests {

  /**
    * Level 8. Pull
    *
    * 1. Read the tutorial by @Daenyth
    * [[https://gist.github.com/Daenyth/024c5584da01acabe7a435c8a53c4f3c Concurrency]]
    *
    * 2. Fix the method implementations in [[level8.impl]]
    */
  def tests[T](harness: Harness[T]): T = {
    import harness._
    section(
      namedSection("evalMapAccumulate")(
        test("maps accumulates") { () =>
          val s0 = Stream.range(1, 5).covary[IO]
          val s1 = evalMapAccumulate(s0)(0)((acc, i) => IO((i, acc + i)))

          assertEqual(
            s1.compile.toList.unsafeRunSync,
            List((1, 1), (2, 3), (3, 5), (4, 7))
          )
        },
        test("evals") { () =>
          var cache = ' '

          val s0 = Stream("some", "random", "text").covary[IO]
          val s1 = evalMapAccumulate(s0)(0)((acc, s) => IO {
              cache = s.head
              (acc + s.length, s.head)
            })

          combine(
            assertEqual(
              s1.compile.toList.unsafeRunSync,
              List((4, 's'), (10, 'r'), (14, 't'))
            ),
            assert(cache === 't')
          )
        }
      ),
      namedSection("filter")(
        test("filters") { () =>
          assertEqual(
            filter(Stream.range(0, 10))(_ % 2 == 0).toList,
            (0 until 10).filter(_ % 2 == 0).toList
          )
        }
      )
    )
  }

  def main(args: Array[String]): Unit = {
    val output = tests(tutorialHarness)((), Nil)
    println("Level 8. Results")
    output.print()
  }
}
