package com.ddmc.autotestspringboot.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.sql.*;
import java.util.Locale;
import java.util.ResourceBundle;

public class Mysql {

    private static final ResourceBundle bundle = ResourceBundle.getBundle("application", Locale.CHINA);
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";

    private static Connection connect(String db) throws ClassNotFoundException, SQLException {
        String env = Env.getEnv();
        String url = "";
        String user = "";
        String pwd = "";
        if (env.equals("t0") || env.equals("T0")) {
            url = bundle.getString("t0.datasource.url") + db;
            user = bundle.getString("t0.datasource.username");
            pwd = bundle.getString("t0.datasource.password");
        } else if (env.equals("t1") || env.equals("T1")) {
            url = bundle.getString("t1.datasource.url") + db;
            user = bundle.getString("t1.datasource.username");
            pwd = bundle.getString("t1.datasource.password");
        }

        // 注册 JDBC 驱动
        Class.forName(JDBC_DRIVER);
        System.out.println(url);
        return DriverManager.getConnection(url, user, pwd);
    }

    public static JSONArray query(String sql, String db) {
        JSONArray res = new JSONArray();
        Connection conn = null;
        Statement statement = null;
        ResultSet result = null;
        try {
            conn = connect(db);
            statement = conn.createStatement();
//            statement.setFetchSize(20);
//            statement.setFetchDirection(ResultSet.FETCH_FORWARD);
//            statement.setMaxFieldSize(1000);
            result = statement.executeQuery(sql);
//            System.out.println(result.getFetchSize());

            // 拿到每行数据，从第一个遍历
            while (result.next()) {
                JSONObject temp = new JSONObject();
                for (int index = 1; index <= result.getMetaData().getColumnCount(); index++) {
                    int columnType = result.getMetaData().getColumnType(index);
                    switch (columnType) {
                        case Types.ARRAY:
                            temp.put(result.getMetaData().getColumnName(index), result.getArray(index));
                            break;
                        case Types.BIGINT:
                            temp.put(result.getMetaData().getColumnName(index), result.getLong(index));
                            break;
                        case Types.BOOLEAN:
                            temp.put(result.getMetaData().getColumnName(index), result.getBoolean(index));
                            break;
                        case Types.BLOB:
                            temp.put(result.getMetaData().getColumnName(index), result.getBlob(index));
                            break;
                        case Types.DOUBLE:
                            temp.put(result.getMetaData().getColumnName(index), result.getDouble(index));
                            break;
                        case Types.FLOAT:
                            temp.put(result.getMetaData().getColumnName(index), result.getFloat(index));
                            break;
                        case Types.INTEGER:
                            temp.put(result.getMetaData().getColumnName(index), result.getInt(index));
                            break;
                        case Types.NVARCHAR:
                            temp.put(result.getMetaData().getColumnName(index), result.getNString(index));
                            break;
                        case Types.VARCHAR:
                            temp.put(result.getMetaData().getColumnName(index), result.getString(index));
                            break;
                        case Types.TINYINT:
                            temp.put(result.getMetaData().getColumnName(index), result.getInt(index));
                            break;
                        case Types.SMALLINT:
                            temp.put(result.getMetaData().getColumnName(index), result.getInt(index));
                            break;
                        case Types.DATE:
                            temp.put(result.getMetaData().getColumnName(index), result.getDate(index));
                            break;
                        case Types.TIMESTAMP:
                            temp.put(result.getMetaData().getColumnName(index), result.getTimestamp(index));
                            break;
                        case Types.BIT:
                            temp.put(result.getMetaData().getColumnName(index), result.getByte(index));
                            break;
                        default:
                            temp.put(result.getMetaData().getColumnName(index), result.getObject(index));
                            break;
                    }
                }
                res.add(temp);

            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (result != null) {
                    result.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return res;

    }

    public static JSONObject queryOne(String sql, String db) {
        JSONArray result = query(sql, db);
        if (result.size() == 1) {
            return result.getJSONObject(0);
        }else if (result.size() == 0) {
            throw new RuntimeException("查询到的数据为空");
        }else {
            throw new RuntimeException("查询到的数据大于1条");
        }

    }

    public static String update(String sql, String db) {
        Connection conn = null;
        Statement statement = null;
        try {
            sql = sql.trim();
            if (sql.toLowerCase().contains("delete") && !sql.toLowerCase().contains("where")) {
                return null;
            }
            conn = connect(db);
            statement = conn.createStatement();
            statement.execute(sql);
            return "0";

        } catch (SQLException | ClassNotFoundException  throwables) {
            throwables.printStackTrace();
            return throwables.getMessage();
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }

    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        //        JSONArray res = query("SELECT * FROM student where age = 27", "test");
        JSONArray res = query("SELECT * FROM user where age = 24", "course");
//        JSONObject res = queryOne("SELECT * FROM user where age = 20", "course");
//        String res = update("SELECT * FROM user where age = 20", "course");
        System.out.println("size:" + res.size());
        System.out.println(res);
    }
}
