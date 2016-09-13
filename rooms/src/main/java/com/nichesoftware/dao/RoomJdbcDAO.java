package com.nichesoftware.dao;

import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;

/**
 * Created by n_che on 12/09/2016.
 */
public class RoomJdbcDAO extends AbstractJdbcDAO implements IRoomDAO {
    // Fields   --------------------------------------------------------------------------------------------------------
    /**
     * Data source
     */
    @Autowired
    private DataSource dataSource;

    // Methods   -------------------------------------------------------------------------------------------------------

    // Getters   -------------------------------------------------------------------------------------------------------
    /**
     * Getter on the data source
     * @return dataSource
     */
    public DataSource getDataSource() {
        return dataSource;
    }

    // Setters   -------------------------------------------------------------------------------------------------------
    /**
     * Setter on the data source
     * @param dataSource
     */
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
