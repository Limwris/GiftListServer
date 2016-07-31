package com.nichesoftware.dao;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.dataset.FlatXmlDataSetLoader;
import com.nichesoftware.model.Room;
import com.nichesoftware.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.text.SimpleDateFormat;

/**
 * Created by n_che on 02/05/2016.
 */
@EnableWebMvc //mvc:annotation-driven
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "GiftDaoTest-context.xml")
@DbUnitConfiguration(dataSetLoader = FlatXmlDataSetLoader.class,
        databaseConnection="ds")
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, // standard Spring listeners
        DirtiesContextTestExecutionListener.class, // standard Spring listeners
        TransactionalTestExecutionListener.class, // standard Spring listeners
        DbUnitTestExecutionListener.class })  // process DBUnit annotations
@DatabaseSetup(connection="ds", value="classpath:data.xml")
public class GiftDaoTest {

    @Autowired
    @Qualifier(value = "giftDao")
    private GiftDao sut;

    @Test
    @ExpectedDatabase(connection="ds", value = "classpath:expected_gifts_data_add_gift.xml")
    @DatabaseTearDown
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
        sut.addGift(u, room, "Train", 129, 0, "Zoli description");
    }
}
