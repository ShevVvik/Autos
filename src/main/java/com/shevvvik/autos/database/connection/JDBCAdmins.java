package com.shevvvik.autos.database.connection;

import com.shevvvik.autos.services.logger.LogEntity;
import com.shevvvik.autos.web.forms.LoggerForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.shevvvik.autos.database.StoredProcedureList.GET_LOG_EVENTS;

@Service
public class JDBCAdmins {

    @Autowired
    private DataSource dataSource;

    public List<LogEntity> getLogEntities(String dateStart, String dateEnd, Map<Integer, Boolean> events) throws SQLException {
        List<LogEntity> result = new ArrayList<>();
        Connection connection = dataSource.getConnection();
        CallableStatement callableStatement = connection.prepareCall(GET_LOG_EVENTS);
        callableStatement.setDate(1, java.sql.Date.valueOf(dateStart));
        callableStatement.setDate(2, java.sql.Date.valueOf(dateEnd));
        try (ResultSet resultSet = callableStatement.executeQuery()) {
            while (resultSet.next()) {
                if (events.get(resultSet.getInt(6))) {
                    LogEntity logEntity = new LogEntity();
                    logEntity.setUsername(resultSet.getString(1));
                    logEntity.setRole(resultSet.getString(2));
                    logEntity.setEventType(resultSet.getString(3));
                    logEntity.setLogMessage(resultSet.getString(4));
                    logEntity.setDate(resultSet.getDate(5) != null
                            ? (resultSet.getDate(5).toString()) : " ");
                    result.add(logEntity);
                }
            }
        } catch (Exception e) {
            connection.close();
            throw e;
        }
        connection.close();
        return result;
    }
}
