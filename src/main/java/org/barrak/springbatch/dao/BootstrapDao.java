package org.barrak.springbatch.dao;

import javax.annotation.PostConstruct;
import org.springframework.stereotype.Component;

/**
 *
 * @author Emilien Guenichon <emilien.guenichon@cgi.com>
 */
@Component
public class BootstrapDao extends SpringBatchExampleDao {

    private static final String CREATE_PERSON_TABLE = "CREATE TABLE IF NOT EXISTS people ("
            + "FIRST_NAME VARCHAR(20),"
            + "LAST_NAME VARCHAR(20)"
            + ")";

    @PostConstruct
    public void init() {
        getJdbcTemplate().execute(CREATE_PERSON_TABLE);
    }

}
