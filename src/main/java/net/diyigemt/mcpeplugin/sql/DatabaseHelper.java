package net.diyigemt.mcpeplugin.sql;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.io.File;
import java.sql.SQLException;

/**
 * <h2>sqlite数据库连接类</h2>
 * @author diyigemt
 * @since 1.0.0
 */
public class DatabaseHelper {
  private static DatabaseHelper INSTANCE;
  // 数据库文件存放位置
  private static String SQL_URL_BASE = "jdbc:sqlite:";
  // 数据库文件名
  private static String DATABASE_NAME = "diyigemt";


  public static DatabaseHelper getInstance() { return INSTANCE; }

  public DatabaseHelper(String path) {
    SQL_URL_BASE = SQL_URL_BASE + path + '/';
    File file = new File(path + "/");
    if (!file.exists()) {
      file.mkdir();
    }
    INSTANCE = this;
  }
  /**
   * <h2>获取指定数据库名字的文件存放相对位置</h2>
   * <p>当数据库文件或者表不存在时会自动创建
   * <p>使用例子请百度或@see
   * @param dBName 数据库文件吗名
   * @return 一个连接池
   * @throws SQLException 连接池创建失败
   */
  public JdbcConnectionSource getDataSourceURL(String dBName) throws SQLException {
    if (dBName.contains(".db")) dBName = dBName.replace(".db", "");
    String url = SQL_URL_BASE + dBName + ".db";
    return new JdbcConnectionSource(url);
  }

  /**
   * <h2>根据数据库文件名 对应的实体类 和表key对应的实体类获取数据库DAO</h2>
   * @param entity 实体类
   * @param key 主键类
   * @param <T> 实体类
   * @param <TD> 主键类
   * @return 一个连接池失败时返回null
   */
  public <T, TD> Dao<T, TD> getDAO(Class<T> entity, Class<TD> key) {
    Dao<T, TD> dao = null;
    try {
      JdbcConnectionSource url = getDataSourceURL(DATABASE_NAME);
      dao = DaoManager.lookupDao(url, entity);
      if (dao == null) {
        dao = DaoManager.createDao(url, entity);
      }
      if (!dao.isTableExists()) TableUtils.createTable(dao);
    } catch (SQLException e) {
      e.printStackTrace();
      System.err.println("数据库创建失败");
      System.exit(-1);
    }
    return dao;
  }
}
