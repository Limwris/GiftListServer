package com.nichesoftware.dao;

import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;


/**
 * Created by n_che on 27/04/2016.
 */
@RunWith(JUnit4.class)
public class PersonDaoTest {
    private PersonDao sut;

    private IDatabaseConnection getConnection() throws Exception {
        Class.forName("com.mysql.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/giftlist";
        Connection cn = DriverManager.getConnection(url, "scott", "summers");
        IDatabaseConnection cx = new DatabaseConnection(cn);

        return cx;
    }

    @Before
    public void setUp() throws Exception {
        sut = new PersonDao();

        IDatabaseConnection dbConnection = getConnection();
        FlatXmlDataSetBuilder builder = new FlatXmlDataSetBuilder();
        ClassLoader classLoader = getClass().getClassLoader();
//        IDataSet data = builder.build(new File(classLoader.getResource("person_data.xml").getFile()));
        IDataSet data = builder.build(new File(classLoader.getResource("data.xml").getFile()));
        DatabaseOperation.CLEAN_INSERT.execute(dbConnection, data);
        dbConnection.close();
    }

    @After
    public void tearDown() {
        sut = null;
    }
}
