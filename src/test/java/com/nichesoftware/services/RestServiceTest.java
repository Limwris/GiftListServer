package com.nichesoftware.services;

import com.nichesoftware.dao.IGiftDao;
import com.nichesoftware.dao.IPersonDao;
import com.nichesoftware.dao.IUserDao;
import com.nichesoftware.model.Gift;
import com.nichesoftware.model.Person;
import com.nichesoftware.model.User;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by n_che on 27/04/2016.
 */
public class RestServiceTest {
    @InjectMocks
    private RestService sut;
    @Mock
    private IGiftDao giftDao;
    @Mock
    private IUserDao userDao;
    @Mock
    private IPersonDao personDao;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void itShouldAddGift() throws Exception {
        // Arange
        User user = new User();
        final Person person = new Person(1, "Prenom", "Nom");
        List<Person> persons = new ArrayList<>();
        persons.add(person);
        when(userDao.findByUsername(anyString())).thenReturn(user);
        when(personDao.getAllPersons(anyString())).thenReturn(persons);
        Mockito.doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                Gift gift = new Gift(0);
                gift.setAmount(0);
                gift.setPrice(129);
                gift.setName("Train");
                person.addGift(gift);
                return null;
            }
        }).when(giftDao).addGift(eq(user), any(Person.class), anyString(), anyDouble(), anyDouble());


        // Act
        sut.addGift("jean-pierre", 1, "Train", 129, 0);

        // Assert
        verify(giftDao).addGift(eq(user), any(Person.class), anyString(), anyDouble(), anyDouble());
        Assert.assertEquals("Le cadeau n'a pas été correctement ajouté.", person.getGiftList().size(), 1);
        Assert.assertEquals("Le nom du cadeau ne correspond pas.", person.getGiftList().get(0).getName(), "Train");
    }

    @After
    public void tearDown() {

    }
}
