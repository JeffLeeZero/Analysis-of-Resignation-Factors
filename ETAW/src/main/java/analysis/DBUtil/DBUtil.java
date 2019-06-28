package analysis.DBUtil;

import oracle.jdbc.driver.OracleDriver;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.sql.*;

public class DBUtil {
    private static String dbDriver;
    private static String url;
    private static String user;
    private static String password;

    //静态变量初始化
    static {
        try {
            InputStream in = DBUtil.class.getResourceAsStream("/db.properties");
            Properties properties = new Properties();
            properties.load(in);
            dbDriver = properties.getProperty("driver");
            url = properties.getProperty("url");
            user = properties.getProperty("user");
            password = properties.getProperty("password");
            Class.forName(dbDriver);
        }catch (IOException e){
            System.err.println("数据库配置文件读取异常.");
        }catch(ClassNotFoundException e){
            System.err.println("驱动配置异常");
        }
    }

    public static Connection getConnection(){
        try {
            Connection connection = DriverManager.getConnection(url, user, password);
            System.out.println("链接信息：" + connection);
            return connection;
        }catch (SQLException e){
            System.err.println("链接信息异常");
            return null;
        }
    }

    public static void closeConn(Connection conn){
        if(conn != null){
            try{
                conn.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
            conn = null;
        }
    }

    public static void rollback(Connection conn){
        if(conn != null){
            try{
                conn.rollback();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        conn = null;
    }

}
