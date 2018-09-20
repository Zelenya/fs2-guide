package level2

import cats.effect.IO
import cats.implicits._
import fs2.Stream
import secret.zstuff._
import testz._

import scala.util.Try

object Level2Tests {

  /**
    * Level 2. Error handling
    *
    * 1. Read the section
    * [[http://fs2.io/guide.html#error-handling Error handling]]
    *
    * 2. Fix the tests
    */
  def tests[T](harness: Harness[T]): T = {
    import harness._
    section(
      test("exception in pure") { () =>
        val stream = Stream(1, 2, 3) ++ Stream(throw new Exception("Boom!"))
        assertEqual(
          Try(stream.toList).getOrElse(List(42)),
          List(???)
        )
      },
      test("exception in effect") { () =>
        val stream = Stream(1, 2, 3) ++ Stream.raiseError[IO](new Exception("Boom!"))
        assertEqual(
          Try(stream.compile.toList.unsafeRunSync).getOrElse(List(42)),
          List(???)
        )
      },
      test("handle exception 1") { () =>
        val error = "Boom!"
        val stream = Stream.raiseError[IO](new Exception(error))
        assertEqual(
          stream.handleErrorWith(e => Stream(e.getMessage)).compile.toList.unsafeRunSync(),
          List(???)
        )
      },
      test("handle exception 2") { () =>
        val error = "Boom!"
        val stream = Stream("1", "2", "3") ++ Stream(throw new Exception(error))
        assertEqual(
          stream.handleErrorWith(e => Stream(e.getMessage)).toList,
          List(???)
        )
      },
      test("handle exception 3") { () =>
        val error = "Boom!"
        val stream = Stream("1") ++ Stream(throw new Exception(error)) ++ Stream("3")
        assertEqual(
          stream.handleErrorWith(e => Stream(e.getMessage)).toList,
          List(???)
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
    println("Level 2. Results")
    output.print()
  }
}
