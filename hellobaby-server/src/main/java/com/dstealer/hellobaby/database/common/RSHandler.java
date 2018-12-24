package com.dstealer.hellobaby.database.common;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by lishiwu on 2016/7/14.
 */
public interface RSHandler<T> {
    T parseFrom(ResultSet rs) throws SQLException;
}
