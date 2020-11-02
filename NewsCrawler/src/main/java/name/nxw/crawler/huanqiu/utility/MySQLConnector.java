package name.nxw.crawler.huanqiu.utility;

import name.nxw.crawler.huanqiu.HuanqiuCrawler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLConnector {

    static final Logger logger = LoggerFactory.getLogger(MySQLConnector.class);
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";

    private final String host;
    private final int port;
    private final String database;
    private final String user;
    private final String password;

    private Connection conn = null;

    public MySQLConnector(String host, int port, String database, String user, String password) throws ClassNotFoundException {
        this.host = host;
        this.port = port;
        this.database = database;
        this.user = user;
        this.password = password;

        Class.forName(JDBC_DRIVER);
    }

    private String getDatabaseURL() {
        return String.format("jdbc:mysql://%s:%d/%s?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC",
                host, port, database);
    }

    public Connection getConn() throws SQLException {
        return DriverManager.getConnection(getDatabaseURL(), user, password);
    }

    public void close() throws SQLException {
        conn.close();
    }

}
