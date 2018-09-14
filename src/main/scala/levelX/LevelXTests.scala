package levelX

import cats.implicits._
import fs2.Stream
import levelX.impl._
import secret.zstuff._
import testz._

object LevelXTests {

  /**
    * Fix the method implementations in [[levelX.impl]]
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
        }
      )
    )
  }

  def main(args: Array[String]): Unit = {
    val output = tests(tutorialHarness)((), Nil)
    println("level X. Results")
    output.print()
  }
}
