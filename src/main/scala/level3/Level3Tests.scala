package level3

import java.util.concurrent.atomic.AtomicReference

import cats.effect.IO
import cats.implicits._
import fs2.Stream
import secret.zstuff._
import testz._

import scala.util.Try

object Level3Tests {

  /**
    * Level 3. Resource acquisition
    *
    * 1. Read the section
    * [[http://fs2.io/guide.html#resource-acquisition Resource acquisition]]
    *
    * 2. Fix the tests
    */
  def tests[T](harness: Harness[T]): T = {
    import harness._
    section(
      test("resource") { () =>
        val importantResource = new AtomicReference[String]("empty")
        val acquire = IO(importantResource.set("acquired"))
        val release = IO(importantResource.set("released"))

        Try {
          Stream.bracket(acquire)(_ => release)
            .flatMap(_ => Stream(throw new Exception("Boom!")))
            .compile.drain.unsafeRunSync()
        }

        assertEqual(
          importantResource.get(),
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
    println("Level 3. Results")
    output.print()
  }
}
