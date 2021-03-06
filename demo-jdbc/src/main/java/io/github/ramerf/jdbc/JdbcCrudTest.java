package io.github.ramerf.jdbc;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import java.sql.*;
import java.util.Date;
import javax.sql.DataSource;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;

/**
 * The type Jdbc crud.
 *
 * @author ramer
 */
@Slf4j
public class JdbcCrudTest {
  /** The constant dataSource. */
  public static DataSource dataSource;

  static {
    dataSource = new ComboPooledDataSource("c3p0_config");
  }

  /** 使用连接池连接数据库 @return the connection */
  public Connection getConnection() {
    Connection connection = null;
    try {
      connection = dataSource.getConnection();
    } catch (SQLException e) {
      log.error(e.getMessage(), e);
    }
    return connection;
  }

  /** 直接获取连接 @return the connection origin */
  public Connection getConnectionOrigin() {
    String url = "jdbc:mysql://localhost:3306/spring_web?useSSL=false";
    Connection connection = null;
    try {
      connection = DriverManager.getConnection(url, "root", "ramer");
    } catch (SQLException e) {
      log.warn(e.getMessage());
      log.error(e.getMessage(), e);
    }
    return connection;
  }

  /**
   * Test get connection.
   *
   * @throws Exception the exception
   */
  @Test
  public void testGetConnection() throws Exception {
    Assert.assertNotNull(getConnection());
    Assert.assertNotNull(getConnectionOrigin());
  }

  /**
   * Test create.
   *
   * @throws Exception the exception
   */
  @Test
  public void testCreate() throws Exception {
    String sql = "INSERT INTO user(username,password) VALUES (?,?)";
    ///  PreparedStatement preStmt = connection.prepareStatement(sql);
    // 返回主键
    PreparedStatement preStmt =
        getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
    preStmt.setString(1, "ramer");
    preStmt.setString(2, "ramerpwd");
    preStmt.executeUpdate();
    ResultSet generatedKeys = preStmt.getGeneratedKeys();
    generatedKeys.next();
    log.info("testCreate:[id:{}]", generatedKeys.getInt(1));
  }

  /**
   * Test read.
   *
   * @throws Exception the exception
   */
  @Test
  public void testRead() throws Exception {
    Connection connection = getConnection();
    String sql = "SELECT * FROM user where id =?";
    PreparedStatement preStmt = connection.prepareStatement(sql);
    preStmt.setInt(1, 1);
    ResultSet resultSet = preStmt.executeQuery();
    while (resultSet.next()) {
      //  方式一
      int id = resultSet.getInt(1);
      String password = resultSet.getString(2);
      String username = resultSet.getString(3);
      Date createTime = resultSet.getDate(4);
      log.info(
          "testRead:[id:{},username:{},password:{},createTime:{}]",
          id,
          username,
          password,
          createTime);
      // 方式二 ,注意索引从1开始
      ResultSetMetaData metaData = resultSet.getMetaData();
      for (int i = 1; i < metaData.getColumnCount() + 1; i++) {
        String propName = metaData.getColumnName(i);
        Object propVal = resultSet.getObject(propName);
        log.info("testRead:[propName:{},propVal:{}]", propName, propVal);
      }
    }
  }

  /**
   * Test update.
   *
   * @throws Exception the exception
   */
  @Test
  public void testUpdate() throws Exception {
    Connection connection = getConnection();
    String sql = "UPDATE user SET password=? WHERE id=?";
    PreparedStatement preStmt = connection.prepareStatement(sql);
    preStmt.setString(1, "jerry");
    preStmt.setInt(2, 1);
    preStmt.executeUpdate();
  }

  /**
   * Test delete.
   *
   * @throws Exception the exception
   */
  @Test
  public void testDelete() throws Exception {
    Connection connection = getConnection();
    String sql = "DELETE FROM user WHERE id=?";
    PreparedStatement preStmt = connection.prepareStatement(sql);
    preStmt.setInt(1, 1);
    preStmt.executeUpdate();
  }
}
