package com.dstealer.hellobaby.database.common;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by lishiwu on 2016/10/10.
 */
public interface PSParser<T> {
    void parseTo(PreparedStatement preparedStatement, T pojo) throws SQLException;
}
