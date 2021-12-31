import monix.eval.Task
import org.specs2.matcher.Scope
import org.specs2.mutable.Specification
import monix.execution.Scheduler.Implicits.global

class MonixTaskCreationSpec extends Specification {
  //https://www.youtube.com/watch?v=wi97X8_JQUk&ab_channel=ScalaDaysConferences
  "monix" should {
    "run to future" in new Context {
      val t = Task {
        Thread.sleep(100)
        println("in task")
      }
      println("pre-run")
      val future = t.runToFuture
      future.map((_) => println("future complete"))
      println("post-run")
    }

    "Task.now" in new Context {
      val t = Task.now {println("in task"); "immediate"}
      println("post-run")
      val f = t.runToFuture
      println(f.value)
    }

    "run multiple times executes body multiple times" in new Context {
      val t = Task {
        println("2 or 3")
      }
      println("1")
      t.runToFuture
      t.runToFuture
    }

    "Task.now run multiple times runs body once" in new Context {
      val t = Task.now {println("1"); "immediate"}
      println("2")
      t.runToFuture
      t.runToFuture
    }

    "Task.evalOnce run multiple times runs body once" in new Context {
      val t = Task.evalOnce {println("2"); "memoized"}
      println("1")
      t.runToFuture
      t.runToFuture
    }

    "Task.eval run multiple times runs body multiple times" in new Context {
      val t = Task.eval {println("2 or 3"); "always"}
      println("1")
      t.runToFuture
      t.runToFuture
    }

    "Task.evalAsync runs async" in new Context {
      val t = Task.evalAsync {Thread.sleep(100); println("4 or 5"); "always"}
      println("1")
      t.runToFuture
      println("2")
      t.runToFuture
      println("3")
      Thread.sleep(200)
      println("6")
    }

    "Task.defer run multiple times runs body multiple times" in new Context {
      val t = Task.defer(Task.now {println("2 or 3"); "always"})
      println("1")
      t.runToFuture
      t.runToFuture
    }

    "Task.fork run multiple times runs body multiple times" in new Context {
      val t = Task.fork(Task.eval {Thread.sleep(100); println("4 or 5"); "always"})
      println("1")
      t.runToFuture
      println("2")
      t.runToFuture
      println("3")
      Thread.sleep(200)
      println("6")
    }

    "Task.executeAsync run multiple times runs body multiple times" in new Context {
      val t = Task.eval {Thread.sleep(100); println("4/6"); "always"}.executeAsync
      println("1")
      t.runToFuture.map((_) => println("5"))
      println("2")
      Thread.sleep(50)
      t.runToFuture.map((_) => println("7"))
      println("3")
      Thread.sleep(200)
      println("8")
    }
  }

  trait Context extends Scope{

  }


}
