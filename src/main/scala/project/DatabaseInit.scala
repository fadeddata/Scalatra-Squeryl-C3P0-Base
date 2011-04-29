package project

import org.scalatra.Initializable
import com.mchange.v2.c3p0.ComboPooledDataSource
import org.squeryl.{Session, SessionFactory}
import org.squeryl.adapters.MySQLAdapter

trait DatabaseInit extends Initializable {
  val databaseUsername = "username"
  val databasePassword = "password"
  val databaseConnection = "jdbc:mysql://example.com:3306/database"

  var cpds = new ComboPooledDataSource
  abstract override def initialize(config: Config) {
    cpds.setDriverClass("com.mysql.jdbc.Driver")
    cpds.setJdbcUrl(databaseConnection)
    cpds.setUser(databaseUsername)
    cpds.setPassword(databasePassword)

    cpds.setMinPoolSize(1)
    cpds.setAcquireIncrement(1)
    cpds.setMaxPoolSize(50)

    SessionFactory.concreteFactory = Some(() => connection)

    def connection = {
      Session.create(cpds.getConnection, new MySQLAdapter)
    }
  }
}
