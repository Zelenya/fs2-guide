package level1

import cats.effect.IO
import cats.implicits._
import fs2.Stream
import secret.zstuff._
import testz._

object Level1Tests {

  /**
    * Level 1. Building streams. Basic operations
    *
    * 1. Read the sections
    * [[http://fs2.io/guide.html#building-streams Building streams]]
    * [[http://fs2.io/guide.html#basic-stream-operations Basic stream operations]]
    *
    * 2. Fix the tests
    */
  def tests[T](harness: Harness[T]): T = {
    import harness._
    section(
      test("example of successful test") { () =>
        assertEqual(
          Stream.emits(List(1, 2, 3)).toList, // actual
          List(1, 2, 3) // expected
        )
      },
      test("++") { () =>
        assertEqual(
          (Stream.emit(10) ++ Stream(20, 30, 40) ++ Stream.emits(Vector(50, 60))).toVector,
          ??? // fix me and others
        )
      },
      test("map") { () =>
        assertEqual(
          Stream(1, 2, 3).map(_.toString).toList,
          ???
        )
      },
      test("fold") { () =>
        assertEqual(
          Stream.range(1, 5).fold(0)(_ + _).toList,
          ???
        )
      },
      test("take") { () =>
        assertEqual(
          Stream.range(1, 5).take(2).toList,
          ???
        )
      },
      test("flatMap") { () =>
        assertEqual(
          Stream(1, 2, 3).flatMap(i => Stream(i, -i)).toList,
          ???
        )
      },
      test(">>") { () =>
        assertEqual(
          // Alias for `flatMap(_ => s2)`
          (Stream("ignore", "me") >> Stream("42")).toList,
          ???
        )
      },
      test("eval") { () =>
        assertEqual(
          Stream.eval(IO(42)).compile.toList.unsafeRunSync(),
          ???
        )
      },
      test("eval + effect") { () =>
        // as an effect, stream writes to a variable
        var cache = ""
        val s1 = Stream.eval(IO {
          cache = "new value"
          "42"
        })

        combine(
          assertEqual(
            s1.compile.toList.unsafeRunSync(),
            ???
          ),
          assert(cache.contains("new value"))
        )
      },
      test(">> + effect") { () =>
        // as an effect, stream writes to a variable
        var cache = ""
        val s1 = Stream.eval(IO {
          cache = "new value"
          "ignore"
        })

        combine(
          assertEqual(
            (s1 >> Stream("42")).compile.toList.unsafeRunSync(),
            ???
          ),
          assert(cache.contains("new value"))
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
    println("Level 1. Results")
    output.print()
  }
}
