package com.nichesoftware.dao;

import com.nichesoftware.exceptions.KeyAlreadyExistsException;
import com.nichesoftware.model.User;
import org.dbunit.Assertion;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.FilteredTableMetaData;
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Created by n_che on 29/04/2016.
 */
@RunWith(JUnit4.class)
public class UserDaoTest {
    private UserDao sut;

    private IDatabaseConnection getConnection() throws Exception {
        Class.forName("com.mysql.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/giftlist";
        Connection cn = DriverManager.getConnection(url, "scott", "summers");
        IDatabaseConnection cx = new DatabaseConnection(cn);

        return cx;
    }

    @Before
    public void setUp() throws Exception {
        sut = new UserDao();

        IDatabaseConnection dbConnection = getConnection();
        FlatXmlDataSetBuilder builder = new FlatXmlDataSetBuilder();
        ClassLoader classLoader = getClass().getClassLoader();
//        IDataSet data = builder.build(new File(classLoader.getResource("user_data.xml").getFile()));
        IDataSet data = builder.build(new File(classLoader.getResource("data.xml").getFile()));
//        IDataSet fullDataSet = new FilteredDataSet(new DatabaseSequenceFilter(dbConnection), data);

        DatabaseOperation.CLEAN_INSERT.execute(dbConnection, data);
//        DatabaseOperation.CLEAN_INSERT.execute(dbConnection, fullDataSet);
        dbConnection.close();
    }


    @Test
    public void itShouldReturnCorrectPassword() throws Exception {
        // Act
        User user = sut.findByUsername("rr");

        // Assert
        assertNotNull("Utilisateur null", user);
        assertEquals("Mot de passe incorrect", "secret", user.getPassword());
    }

    @Test
    public void itShouldReturnNullWhenUsernameIsUnknown() throws Exception {
        // Act
        User user = sut.findByUsername("inconnu");

        // Assert
        assertNull("Utilisateur null", user);
    }

    @Test(expected = KeyAlreadyExistsException.class)
    public void itShouldReturnThrowUserAlreadyExistsExceptionWhenUsernameIsAlreadyInBase() throws Exception {
        // Arrange
        User user = new User();
        user.setUsername("ap");
        user.setPassword("pass2");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        user.setCreationDate(simpleDateFormat.parse("2009-03-12"));

        // Act
        sut.createUser(user);
    }


    @Test
    public void itShouldCreateUserFrancoisPignon() throws Exception {
        // Arrange
        User u = new User();
        u.setId(3);
        u.setUsername("fp");
        u.setPassword("motdepasse");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        u.setCreationDate(simpleDateFormat.parse("2009-03-12"));

        // Act
        sut.createUser(u);

        // Lecture données actuelles
        IDataSet databaseDataSet = getConnection().createDataSet();
        ITable actualTable = databaseDataSet.getTable("user_data");

        // Lecture des données attendues
        ClassLoader classLoader = getClass().getClassLoader();
        File expected = new File(classLoader.getResource("expected_user_data.xml").getFile());
        IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(expected);
//        IDataSet fullDataSet = new FilteredDataSet(new DatabaseSequenceFilter(getConnection()), expectedDataSet);
        ITable expectedTable = expectedDataSet.getTable("user_data");
//        ITable expectedTable = fullDataSet.getTable("user_data");

        // Vérification données attendues / données actuelles
        // en comparant uniquement les colonnes listées dans
        // le fichier des données attendues
//        ITable filteredTable = DefaultColumnFilter.includedColumnsTable(actualTable, expectedTable.getTableMetaData().getColumns());
        ITable filteredTable = DefaultColumnFilter.excludedColumnsTable(actualTable, new String[] {"id"});
        Assertion.assertEquals(expectedTable, filteredTable);
    }

    @After
    public void tearDown() {
        sut = null;
    }
}
