package level1

import cats.implicits._
import fs2.Stream
import secret.zstuff._
import testz._

object Level1Tests {

  /**
    * [[http://fs2.io/guide.html#building-streams Building streams]]
    *
    * Read the section and fix the tests.
    */
  def tests[T](harness: Harness[T]): T = {
    import harness._
    section(
      test("example of successful test") { () =>
        assertEqual(
          Stream.emits(List(1, 2, 3)).toList, // actual
          List(1, 2, 3)                       // expected
        )
      },
      test("++") { () =>
        assertEqual(
          (Stream(1, 2, 3) ++ Stream(4, 5)).toList,
          ??? // fix me and others
        )
      },
      test("filter") { () =>
        assertEqual(
          Stream(1, 2, 3).filter(_ % 2 != 0).toList,
          ???
        )
      }
    )
  }

  /**
    * Run to see the test results.
    * No need to touch anything here.
    */
  def main(args: Array[String]): Unit = {
    val output = tests(tutorialHarness)((), Nil)
    println("level 1. Results")
    output.print()
  }
}
