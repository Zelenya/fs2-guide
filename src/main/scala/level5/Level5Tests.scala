package level5

import cats.implicits._
import fs2.Stream
import level5.impl._
import secret.zstuff._
import testz._

object Level5Tests {

  /**
    * Level 5. Statefully transforming streams
    *
    * 1. Read the section
    * [[http://fs2.io/guide.html#statefully-transforming-streams Statefully transforming streams]]
    *
    * 2. Fix the tests using implementations in [[level5.impl]]
    */
  def tests[T](harness: Harness[T]): T = {
    import harness._
    section(
      namedSection("take")(
        test("using the scanChunksOpt") { () =>
          assertEqual(
            (Stream(10, 20) ++ Stream(30, 40)).through(takeS(3)).toList,
            List(???)
          )
        },
        test("using the Pull") { () =>
          assertEqual(
            (Stream(10, 20) ++ Stream(30, 40)).through(takeP(3)).toList,
            List(???)
          )
        }
      )
    )
  }

  def main(args: Array[String]): Unit = {
    val output = tests(tutorialHarness)((), Nil)
    println("Level 5. Results")
    output.print()
  }
}
