package level6

import cats.implicits._
import fs2.Stream
import level6.impl._
import secret.zstuff._
import testz._

object Level6Tests {

  /**
    * Level 6. Statefully transforming streams. Advanced
    *
    * 1. Read the section
    * [[http://fs2.io/guide.html#statefully-transforming-streams Statefully transforming streams]]
    *
    * 2. Fix the tests using implementations in [[level6.impl]]
    */
  def tests[T](harness: Harness[T]): T = {
    import harness._
    section(
      namedSection("takeWhile")(
        test("<3") { () =>
          assertEqual(
            takeWhile(Stream(0, 1) ++ Stream(2, 3, 4, 5))(_ < 3).toList,
            List(0, 1, 2)
          )
        },
        test(">3") { () =>
          val s1 = Stream.range(0, 5) ++ Stream(throw new RuntimeException("Oops!"))

          assertEqual(
            takeWhile(s1)(_ > 3).toList,
            List.empty
          )
        }
      ),
      namedSection("intersperse")(
        test("ones") { () =>
          assertEqual(
            intersperse(Stream(1, 2) ++ Stream(3, 4, 5))(1).toList,
            List(1, 1, 2, 1, 3, 1, 4, 1, 5)
          )
        },
        test("divide et impera") { () =>
          val text = "FS2 comes with lots of concurrent operations"
          val s1 = Stream.emits(text.split(' '))

          assertEqual(
            intersperse(s1)(" ").toList.combineAll,
            text
          )
        }
      ),
      namedSection("scan")(
        test("running sum") { () =>
          assertEqual(
            scan(Stream.range(1, 10))(0)(_ + _).toList,
            (1 until 10).toList.scan(0)(_ + _)
          )
        },
        test("running factorial") { () =>
          assertEqual(
            scan(Stream(1, 2) ++ Stream(3, 4, 5))(1)(_ * _).toList,
            (1 to 5).toList.scan(1)(_ * _)
          )
        },
        test("not running") { () =>
          assertEqual(
            scan(Stream.emits(List.empty[Int]))(42)(_ + _).toList,
            List(42)
          )
        }
      )
    )
  }

  def main(args: Array[String]): Unit = {
    val output = tests(tutorialHarness)((), Nil)
    println("Level 6. Results")
    output.print()
  }
}
