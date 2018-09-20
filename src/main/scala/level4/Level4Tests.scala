package level4

import cats.effect.IO
import cats.implicits._
import fs2.Stream
import level4.impl._
import secret.zstuff._
import testz._

object Level4Tests {

  /**
    * Level 4.
    *
    * Fix the method implementations in [[level4.impl]]
    */
  def tests[T](harness: Harness[T]): T = {
    import harness._
    section(
      namedSection("repeat")(
        test("1, 2, 1, 2, ...") { () =>
          assertEqual(
            repeat(Stream(1, 2)).take(4).toList,
            List(1, 2, 1, 2)
          )
        },
        test("hi, hi, hi, ...") { () =>
          assertEqual(
            repeat(Stream("hi")).take(3).toList,
            List.fill(3)("hi")
          )
        }
      ),
      namedSection("drain")(
        test("strips all output") { () =>
          assert(drain(Stream(1, 2, 3)).toList.isEmpty)
        },
        test("runs effect") { () =>
          var cache = 0
          val s1 = Stream.eval(IO {
            cache = 420
            420
          })

          combine(
            assert(drain(s1).compile.toList.unsafeRunSync.isEmpty),
            assert(cache === 420)
          )
        }
      ),
      namedSection("eval_")(
        test("strips all output") { () =>
          var cache = 0
          val s1 = eval_(IO {
            cache = 420
            420
          })

          combine(
            assert(s1.compile.toList.unsafeRunSync.isEmpty),
            assert(cache === 420)
          )
        }
      ),
      namedSection("attempt")(
        test("catches") { () =>
          val s1 = Stream(1, 2, 3).attempt.toList

          assertEqual(
            s1.map(_.leftMap(_.getMessage)),
            List(1.asRight, 2.asRight, 3.asRight)
          )
        },
        test("catches errors") { () =>
          val error = "boom!"
          val s1 = (Stream(1, 2) ++ (throw new Exception(error))).attempt.toList

          assertEqual(
            s1.map(_.leftMap(_.getMessage)),
            List(1.asRight, 2.asRight, error.asLeft)
          )
        }
      )
    )
  }

  def main(args: Array[String]): Unit = {
    val output = tests(tutorialHarness)((), Nil)
    println("Level 4. Results")
    output.print()
  }
}
