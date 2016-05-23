package com.nichesoftware.dao;

import com.nichesoftware.model.Room;
import com.nichesoftware.model.User;
import org.dbunit.Assertion;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.filter.DefaultColumnFilter;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.text.SimpleDateFormat;

/**
 * Created by n_che on 02/05/2016.
 */
@RunWith(JUnit4.class)
public class GiftDaoTest {

    private GiftDao sut;

    private IDatabaseConnection getConnection() throws Exception {
        Class.forName("com.mysql.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/giftlistserver";
        Connection cn = DriverManager.getConnection(url, "scott", "summers");
        IDatabaseConnection cx = new DatabaseConnection(cn);

        return cx;
    }

    @Before
    public void setUp() throws Exception {
        sut = new GiftDao();

        IDatabaseConnection dbConnection = getConnection();
        FlatXmlDataSetBuilder builder = new FlatXmlDataSetBuilder();
        ClassLoader classLoader = getClass().getClassLoader();
        IDataSet data = builder.build(new File(classLoader.getResource("data.xml").getFile()));
        DatabaseOperation.CLEAN_INSERT.execute(dbConnection, data);
        dbConnection.close();
    }

//    @Test
//    public void itShouldReturnGiftsList() throws Exception {
//        // Arrange
//        User u = new User();
//        u.setUsername("ap");
//        u.setPassword("pass");
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        u.setCreationDate(simpleDateFormat.parse("2009-02-10"));
//
//        // Act
//        sut.getGifts(u);
//
//        // Assert
//        assertNotNull("Personne null.", u.getPersonById(1));
//        assertEquals("jean-luc girard n'existe pas.", "jean-luc", u.getPersonById(1).getFirstName());
//    }

    @Test
    public void itShouldAddGift() throws Exception {
        // Arrange
        User u = new User();
        u.setId(1);
        u.setUsername("ap");
        u.setPassword("pass");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        u.setCreationDate(simpleDateFormat.parse("2009-02-10"));

        Room room = new Room(1, "Anniversaire de Jean-Luc", "anniversaire");

        // Act
        sut.addGift(u, room, "Train", 129, 0);

        // Lecture données actuelles
        IDataSet databaseDataSet = getConnection().createDataSet();
        ITable actualTable = databaseDataSet.getTable("gifts");

        // Lecture des données attendues
        ClassLoader classLoader = getClass().getClassLoader();
        File expected = new File(classLoader.getResource("expected_gifts_data_add_gift.xml").getFile());
        IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(expected);
        ITable expectedTable = expectedDataSet.getTable("gifts");

        // Vérification données attendues / données actuelles
        // en comparant uniquement les colonnes listées dans
        // le fichier des données attendues
        ITable filteredTable = DefaultColumnFilter.excludedColumnsTable(actualTable, new String[] {"idGifts", "url"});
        Assertion.assertEquals(expectedTable, filteredTable);
    }

    @After
    public void tearDown() {
        sut = null;
    }
}
