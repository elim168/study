package com.elim.study.hive.jdbc;

import org.apache.hive.jdbc.HiveDriver;

import java.sql.*;

/**
 * 通过JDBC连接Hive，需要Hive服务端是通过hiveserver2启动的
 * 下面的网页有使用JDBC连接hive的示例代码。
 * https://cwiki.apache.org/confluence/display/Hive/HiveServer2+Clients
 */
public class BasicTest {

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        String driverName = "org.apache.hive.jdbc.HiveDriver";
        Class.forName(driverName);
        Connection connection = DriverManager.getConnection("jdbc:hive2://172.18.0.2:10000/test");
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from t_log");
        while (resultSet.next()) {
            int columnCount = resultSet.getMetaData().getColumnCount();
            StringBuilder builder = new StringBuilder();
            for (int i=0; i<columnCount; i++) {
                //JDBC列索引从1开始的
                builder.append("colomn_").append(i).append("=").append(resultSet.getString(i+1)).append(",");
            }
            builder.setLength(builder.length() - 1);
            System.out.println(builder);
        }
        resultSet.close();
        statement.close();
        connection.close();
    }

}
