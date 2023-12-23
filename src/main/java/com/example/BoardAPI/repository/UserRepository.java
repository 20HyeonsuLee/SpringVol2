package com.example.BoardAPI.repository;

import com.example.BoardAPI.DBConnection.DBConnectionManager;
import com.example.BoardAPI.domain.User;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class UserRepository {
    public List<User> allUser() throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        String sql = "select * from users";
        List<User> list = new ArrayList();
        try {
            connection = DBConnectionManager.getConnection();
            statement = connection.prepareStatement(sql);
            rs = statement.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setUserId(rs.getString("userid"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                list.add(user);
            }
            return list;
        } catch (SQLException e) {
            log.error("selectUser error={}", e);
            throw e;
        } finally {
            closeResource(connection, statement, rs);//사용한 리소스 반환
        }
    }
    public User findById(String userId) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        String sql = "select * from users where userid = ?";

        try {
            connection = DBConnectionManager.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setString(1, userId);
            rs = statement.executeQuery();
            User user = new User();
            while (rs.next()) {
                user.setUserId(rs.getString("userid"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
            }
            return user;
        } catch (SQLException e) {
            log.error("selectUser error={}", e);
            throw e;
        } finally {
            closeResource(connection, statement, rs);//사용한 리소스 반환
        }
    }

    public void createUser(User newUser) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        String sql = "insert into users(username, password, userid) values(?, ?, ?)";//DB에 넘길 SQL 작성

        try {
            connection = DBConnectionManager.getConnection();//DriverManger 통해서 DB커넥션 생성
            statement = connection.prepareStatement(sql);//SQL실행 하기위한 객체 PrepareStatement 생성

            statement.setString(1, newUser.getUsername());//DB컬럼과 자바 오브젝트 필드 바인딩
            statement.setString(2, newUser.getPassword());
            statement.setString(3, newUser.getUserId());

            statement.executeUpdate();

        } catch (SQLException e) {
            log.error("createUser error={}", e);
            throw e;
        } finally {
            closeResource(connection, statement, null);//사용한 리소스 반환
        }
    }

    public void updateUsername(String userid, String newUsername) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        String sql = "update users set username = ? where userid = ?";

        try {
            connection = DBConnectionManager.getConnection();//DriverManger 통해서 DB커넥션 생성
            statement = connection.prepareStatement(sql);//SQL실행 하기위한 객체 PrepareStatement 생성

            statement.setString(1, newUsername);//DB컬럼과 자바 오브젝트 필드 바인딩
            statement.setString(2, userid);

            statement.executeUpdate();
        } catch (SQLException e) {
            log.error("updateUser error={}", e);
            throw e;
        } finally {
            closeResource(connection, statement, null);//사용한 리소스 반환
        }
    }

    public void deleteUser(String userId) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        String sql = "delete from users where userid = ?";

        try {
            connection = DBConnectionManager.getConnection();//DriverManger 통해서 DB커넥션 생성
            statement = connection.prepareStatement(sql);//SQL실행 하기위한 객체 PrepareStatement 생성

            statement.setString(1, userId);//DB컬럼과 자바 오브젝트 필드 바인딩

            statement.executeUpdate();
        } catch (SQLException e) {
            log.error("deleteUser error={}", e);
            throw e;
        } finally {
            closeResource(connection, statement, null);//사용한 리소스 반환
        }
    }
    private void closeResource(Connection connection, PreparedStatement statement, ResultSet resultSet) {
        //반환할 때는 반드시 역순으로 반환해야 함.
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (Exception e) {
                log.error("error", e);
            }
        }
        if (statement != null) {
            try {
                statement.close();
            } catch (Exception e) {
                log.error("error", e);
            }
        }
        if (connection != null) {
            try {
                connection.close();
            } catch (Exception e) {
                log.error("error", e);
            }
        }
    }
}