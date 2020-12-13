package com.shevvvik.autos.database.connection;

import com.shevvvik.autos.database.StoredProcedureList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.*;

@Service
public class JDBCValidation {

    @Autowired
    private DataSource dataSource;

    public Integer getOrderStatusById(Integer id) throws SQLException {
        Integer result = 0;
        Connection connection = dataSource.getConnection();
        CallableStatement callableStatement = connection.prepareCall(StoredProcedureList.GET_ORDER_STATUS);
        callableStatement.setInt(2, id);
        callableStatement.registerOutParameter(1, Types.INTEGER);
        callableStatement.execute();
        result = callableStatement.getInt(1);
        return result;
    }

    public Integer checkLogin(String login) throws SQLException {
        Integer result = 0;
        Connection connection = dataSource.getConnection();
        CallableStatement callableStatement = connection.prepareCall(StoredProcedureList.CHECK_LOGIN);
        callableStatement.setString(2, login);
        callableStatement.registerOutParameter(1, Types.INTEGER);
        callableStatement.execute();
        result = callableStatement.getInt(1);
        return result;
    }
}
