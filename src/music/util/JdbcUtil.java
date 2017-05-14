package music.util;

import java.io.InputStream;
import java.sql.*;
import java.util.*;

public class JdbcUtil {
    private static String USERNAME;
    private static String PASSWORD;
    private static String DRIVER;
    private static String URL;
    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    static {
        loadConfig();
    }
    /**
     * 加载数据库配置信息，并给相关属性赋值
     */
    public static void loadConfig(){
        try {
            InputStream inputStream = JdbcUtil.class.getResourceAsStream("/jdbc.properties");
            Properties prop = new Properties();
            prop.load(inputStream);
            USERNAME = prop.getProperty("jdbc.username");
            PASSWORD = prop.getProperty("jdbc.password");
            DRIVER = prop.getProperty("jdbc.driver");
            URL = prop.getProperty("jdbc.url");
        } catch (Exception e) {
            throw new RuntimeException("读取数据库配置文件异常！！！", e);
        }
    }
    public JdbcUtil() {
    }

    /**
     * 获得数据库连接对象
     * @return
     */
    public Connection getConnection(){
        try {
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (Exception e) {
            throw new RuntimeException("get connection error!!!");
        }
        return connection;
    }
    
    /**
     * 执行增删改sql语句
     * @param sql 要执行的sql语句
     * @param params 参数列表
     * @return 执行成功返回true，否则返回false
     * @throws SQLException
     */
    public boolean updateByPreparedStatement(String sql, List<?> params) throws SQLException {
        int result = -1;    //表示当用户执行添加删除和修改的时候所影响数据库的行数
        preparedStatement = connection.prepareStatement(sql);
        int index = 1;
        if (params != null && !params.isEmpty()){
            for (int i = 0; i < params.size(); i++) {
                preparedStatement.setObject(index++, params.get(i));
            }
        }
        result = preparedStatement.executeUpdate();
        return result > 0 ? true : false;
    }
    
    /**
     * 执行查询语句
     * @param sql 要执行的sql语句
     * @param params 参数列表
     * @return 返回一个由<列名,值>组成的Map列表
     * @throws SQLException
     */
    public List<Map<String, Object>> findResult(String sql, List<?> params) throws SQLException {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        int index = 1;
        preparedStatement = connection.prepareStatement(sql);
        if (params != null && !params.isEmpty()){
            for (int i = 0; i < params.size(); i++) {
                preparedStatement.setObject(index++, params.get(i));
            }
        }
        resultSet = preparedStatement.executeQuery();
        ResultSetMetaData metaData = resultSet.getMetaData();
        int cols_len = metaData.getColumnCount();
        while (resultSet.next()){
            Map<String, Object> map = new HashMap<String, Object>();
            for (int i = 0; i < cols_len; i++) {
                String cols_name = metaData.getColumnName(i + 1);
                Object cols_value = resultSet.getObject(cols_name);
                if (cols_value == null){
                    cols_value = "";
                }
                map.put(cols_name, cols_value);
            }
            list.add(map);
        }
        return list;
    }
    
    /**
     * 释放资源
     */
    public void releaseConn(){
        if (resultSet != null){
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (preparedStatement != null){
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (connection != null){
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
