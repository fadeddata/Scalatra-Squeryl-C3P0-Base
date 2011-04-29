package project

import org.scalatra._

import org.squeryl.PrimitiveTypeMode._

class ProjectFilter extends ScalatraFilter with DatabaseInit with DatabaseSessionSupport {
  get("/") {
    "Hello World."
  }
}
