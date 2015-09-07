package com.getlevel.test.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class JDBCUtils {

	private final String USERNAME = "root";
	private final String PASSWORD = "13298727600tian";
	// 驱动信息
	public final static String DRIVER = "com.mysql.jdbc.Driver";
	private final String URL = "jdbc:mysql://localhost:3306/wifidb";
	// 数据库连接
	private Connection connection;
	// sql语句执行对象
	private PreparedStatement preparedStatement;
	// 返回结果
	private ResultSet resultSet;

	public JDBCUtils() {
		// TODO Auto-generated constructor stub
		try {
			Class.forName(DRIVER);
			System.out.println("注册成功");
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	// 建立连接
	public Connection getConnection() {
		try {
			connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return connection;
	}

	// update
	public boolean updateByPreparedStatement(String sql, List<Object> params)
			throws SQLException {
		boolean flag = false;
		int result = -1;// 表示当用户执行添加删除和修改的时候所影响数据库的行数
		preparedStatement = connection.prepareStatement(sql);
		int index = 1;
		if (params != null && !params.isEmpty()) {
			for (int i = 0; i < params.size(); i++) {
				preparedStatement.setObject(index++, params.get(i));
			}
		}
		result = preparedStatement.executeUpdate();
		flag = result > 0 ? true : false;
		return flag;
	}

	// 一级表定位
	public String locationFirstPos(String[] nameString) throws SQLException {
		String tableName = "";
		String sql = "select * from tableindex";
		resultSet = (connection.prepareStatement(sql)).executeQuery();
		System.out.println(nameString[0] + nameString[1] + nameString[2]);
		while (resultSet.next()) {
			if ((nameString[0].equals(resultSet.getString(2))
					|| nameString[0].equals(resultSet.getString(3)) || nameString[0]
						.equals(resultSet.getString(4)))
					&& (nameString[1].equals(resultSet.getString(2))
							|| nameString[1].equals(resultSet.getString(3)) || nameString[1]
								.equals(resultSet.getString(4)))
					&& (nameString[2].equals(resultSet.getString(2))
							|| nameString[2].equals(resultSet.getString(3)) || nameString[2]
								.equals(resultSet.getString(4)))) {
				tableName = resultSet.getString(5);
			}
		}
		if (tableName.equals(""))
			tableName = "other";
		return tableName;
	}

	// 二级表定位
	public String locationClient(int[] level, String sql) throws SQLException {
		resultSet = (connection.prepareStatement(sql)).executeQuery();
		resultSet.next();
		int mini_dis = 0;// 最小RSSI距离
		String min_dis_pos = "no loc";

		if (level[0] != 0 && level[1] != 0 && level[2] != 0) {
			mini_dis = Math.abs(level[0] - resultSet.getInt(1))
					+ Math.abs(level[1] - resultSet.getInt(2))
					+ Math.abs(level[2] - resultSet.getInt(3));
		}
		min_dis_pos = resultSet.getString(5);

		while (resultSet.next()) {
			int temp_dis = Math.abs(level[0] - resultSet.getInt(1))
					+ Math.abs(level[1] - resultSet.getInt(2))
					+ Math.abs(level[2] - resultSet.getInt(3));
			if (temp_dis < mini_dis) {
				mini_dis = temp_dis;
				min_dis_pos = resultSet.getString(5);
			}
		}

		return min_dis_pos;
	}

}
