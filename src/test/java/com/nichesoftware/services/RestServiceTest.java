package com.nichesoftware.services;

import com.nichesoftware.dao.IGiftDao;
import com.nichesoftware.dao.IRoomDao;
import com.nichesoftware.dao.IUserDao;
import com.nichesoftware.model.Gift;
import com.nichesoftware.model.Room;
import com.nichesoftware.model.User;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
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
    private IRoomDao roomDao;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void itShouldAddGift() throws Exception {
        // Arange
        final User user = new User();
        final Room room = new Room(1, "Prenom", "Nom");

        when(userDao.findByUsername(anyString())).thenReturn(user);
        Mockito.doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                user.addRoom(room);
                return null;
            }
        }).when(roomDao).getAllRooms(eq(user));
        Mockito.doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                Gift gift = new Gift(0);
                gift.setPrice(129);
                gift.setName("Train");
                room.addGift(gift);
                return null;
            }
        }).when(giftDao).addGift(eq(user), any(Room.class), anyString(), anyDouble(), anyDouble());

        // Act
        sut.addGift("jean-pierre", 1, "Train", 129, 0);

        // Assert
        verify(giftDao).addGift(eq(user), any(Room.class), anyString(), anyDouble(), anyDouble());
        Assert.assertEquals("Le cadeau n'a pas été correctement ajouté.", room.getGiftList().size(), 1);
        Assert.assertEquals("Le nom du cadeau ne correspond pas.", room.getGiftList().get(0).getName(), "Train");
    }

    @After
    public void tearDown() {

    }
}
