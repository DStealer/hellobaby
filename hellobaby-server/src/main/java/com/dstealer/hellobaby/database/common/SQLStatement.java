package com.dstealer.hellobaby.database.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * SQLStatement just for Mysql
 * Created by lishiwu on 2016/7/18.
 */
public class SQLStatement {
    private static final Logger LOGGER = LoggerFactory.getLogger(SQLStatement.class);
    private String sql;
    private List<Object> paramList;

    private SQLStatement(String sql, List<Object> paramList) {
        this.sql = sql;
        this.paramList = paramList;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public String getSql() {
        return sql;
    }

    public List<Object> getParamList() {
        return paramList;
    }

    public static final class Builder {
        private static final int MAX_FETCH_SIZE = 5000;
        private StringBuilder sqlBuilder;
        private List<Object> paramList;

        private Builder() {
            this.sqlBuilder = new StringBuilder(32);
            this.paramList = new ArrayList<>();
        }

        public Builder append(String sqlSegment) {
            this.sqlBuilder.append(sqlSegment);
            return this;
        }

        public Builder append(String sqlSegment, Object... params) {
            this.sqlBuilder.append(sqlSegment);
            if (params != null && params.length > 0) {
                Collections.addAll(this.paramList, params);
            }
            return this;
        }

        public Builder append(boolean condition, String sqlSegment, Object... params) {
            if (condition && params != null && params.length > 0) {
                this.append(sqlSegment, params);
            }
            return this;
        }

        public Builder limit(int indexFrom, int limit) {
            if (indexFrom < 0) {
                indexFrom = 0;
            }
            if (limit < 0 || limit > MAX_FETCH_SIZE) {
                limit = MAX_FETCH_SIZE;
            }
            this.sqlBuilder.append(" LIMIT ").append(indexFrom).append(",").append(limit);
            return this;
        }

        public Builder limitOne() {
            this.sqlBuilder.append(" LIMIT 1");
            return this;
        }

        public SQLStatement build() {
            LOGGER.debug("SQLStatement sql{}:", sqlBuilder.toString());
            return new SQLStatement(sqlBuilder.toString(), Collections.unmodifiableList(this.paramList));
        }

    }
}
