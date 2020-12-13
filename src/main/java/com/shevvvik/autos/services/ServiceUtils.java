package com.shevvvik.autos.services;

import com.shevvvik.autos.database.connection.JDBCConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ServiceUtils {

    @Autowired
    private JDBCConnection jdbcConnection;

    public Map<Integer, String> getCities() {
        Map<Integer, String> map = new HashMap<>();
        try {
            map = jdbcConnection.getCities();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

}
