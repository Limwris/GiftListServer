package com.nichesoftware.dao;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import com.github.springtestdbunit.dataset.FlatXmlDataSetLoader;
import com.nichesoftware.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.context.web.WebAppConfiguration;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Created by n_che on 29/04/2016.
 */
//@EnableWebMvc //mvc:annotation-driven
@ComponentScan({ "com.nichesoftware.dao" })
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class) // Créé le contexte Spring avec le fichier indiqué dans l'annotation @ContextConfiguration
@ContextConfiguration(locations = "UserDaoTest-context.xml")
@DbUnitConfiguration(dataSetLoader = FlatXmlDataSetLoader.class,
        databaseConnection = "ds")
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, // Permet d'utiliser les annotations d'injection de dépendance (@Autowired,@Resource,...)
        DirtiesContextTestExecutionListener.class, // standard Spring listeners
        TransactionalTestExecutionListener.class, // standard Spring listeners
        DbUnitTestExecutionListener.class }) // process DBUnit annotations
@DatabaseSetup(connection = "ds", value = "classpath:data.xml")
public class UserJdbcDAOTest {
    @Autowired
    @Qualifier(value = "dao")
    private UserJdbcDAO sut;

    @Test
    @DatabaseTearDown
    public void itShouldReturnCorrectPassword() throws Exception {
        // Act
        User user = sut.findByUsername("rr");

        // Assert
        assertNotNull("Utilisateur null", user);
        assertEquals("Mot de passe incorrect", "secret", user.getPassword());
    }

    @Test
    @DatabaseTearDown
    public void itShouldReturnNullWhenUsernameIsUnknown() throws Exception {
        // Act
        User user = sut.findByUsername("inconnu");

        // Assert
        assertNull("Utilisateur null", user);
    }

    @Test(expected = DAOException.class)
    @DatabaseTearDown
    public void itShouldThrowUserAlreadyExistsExceptionWhenUsernameIsAlreadyInBase() throws Exception {
        // Arrange
        User user = new User();
        user.setUsername("ap");
        user.setPassword("pass2");
        user.setPhoneNumber("0102030405");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        user.setCreationDate(simpleDateFormat.parse("2009-03-12"));

        // Act
        sut.createUser(user);
    }


    @Test
    @DatabaseTearDown
    @ExpectedDatabase(connection="ds", assertionMode = DatabaseAssertionMode.NON_STRICT, value = "classpath:expected_user.xml")
    public void itShouldCreateUserFrancoisPignon() throws Exception {
        // Arrange
        User u = new User();
        u.setId(3);
        u.setUsername("fp");
        u.setPassword("motdepasse");
        u.setPhoneNumber("0304050607");
        // Todo: Dynamically set creationDate (http://stackoverflow.com/questions/2856840/date-relative-to-current-in-the-dbunit-dataset)
//        u.setCreationDate(new Date());
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        u.setCreationDate(simpleDateFormat.parse("2009-03-12"));

        // Act
        sut.createUser(u);
    }
}
