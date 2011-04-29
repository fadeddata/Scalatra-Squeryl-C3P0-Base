package project

import org.scalatra._
import scala.util.DynamicVariable
import org.squeryl.{Session, SessionFactory}
import javax.servlet.http.{HttpServletRequest, HttpServletResponse}

trait DatabaseSessionSupport extends Handler {
  val dbSession = new DynamicVariable[Session](null)
  abstract override def handle(req: HttpServletRequest, res: HttpServletResponse) {
    dbSession.withValue(SessionFactory.newSession) {
      dbSession.value.bindToCurrentThread
      try {
        super.handle(req, res)
      } finally {
        dbSession.value.close
        dbSession.value.unbindFromCurrentThread
      }
    }
  }
}
