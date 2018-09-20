package secret

import cats.Eq
import cats.syntax.eq._
import testz.PureHarness.Uses
import testz._

/**
  * Not a test.
  * Nothing to see here.
  */
object zstuff {
  private[this] val printer: (Result, List[String]) => Unit =
    (result, name) => println(s"${name.reverse.mkString("[", " - ", "]:")} $result")

  private[this] val pureHarness: Harness[PureHarness.Uses[Unit]] =
    PureHarness.makeFromPrinter(printer)

  val tutorialHarness: Harness[Uses[Unit]] = sneakyHarness(pureHarness)

  def combine: (Result, Result) => Result = Result.combine

  def assertEqual[A: Eq](a1: A, a2: A): Result =
    assert(a1 === a2)

  def sneakyHarness[T](self: Harness[T]): Harness[T] =
    new Harness[T] {
      def test(name: String)(assertions: () => Result): T =
        self.test(name) { () =>
          try {
            assertions()
          } catch {
            case _: NotImplementedError =>
              Fail()
            case ex: Exception =>
              ex.printStackTrace()
              Fail()
          }
        }

      def namedSection(name: String)(test1: T, tests: T*): T =
        self.namedSection(name)(test1, tests: _*)

      def section(test1: T, tests: T*): T =
        self.section(test1, tests: _*)
    }
}
