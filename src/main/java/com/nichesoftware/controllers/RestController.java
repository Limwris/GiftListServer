package com.nichesoftware.controllers;

import com.nichesoftware.dto.GiftDto;
import com.nichesoftware.dto.RoomDto;
import com.nichesoftware.dto.UserDto;
import com.nichesoftware.model.Gift;
import com.nichesoftware.model.Room;
import com.nichesoftware.model.User;
import com.nichesoftware.services.IRestService;
import com.nichesoftware.services.RestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by n_che on 27/04/2016.
 * Sources:   - http://www.journaldev.com/2651/spring-mvc-exception-handling-exceptionhandler-controlleradvice-handlerexceptionresolver-json-response-example
 *            - https://dzone.com/articles/exception-handling-spring-rest
 *            - https://www.javacodegeeks.com/2016/01/exception-handling-spring-restful-web-service.html
 *            - http://stackoverflow.com/questions/15051712/how-to-do-authentication-with-a-rest-api-right-browser-native-clients
 *
 * Tought:    - Pensez à passer les ServerException et GenericException (surtout) en RuntimeException
 */
@org.springframework.web.bind.annotation.RestController
public class RestController {
    private static final Logger logger = LoggerFactory.getLogger(RestController.class.getSimpleName());

    /**
     * Service
     */
    private IRestService restService;

    @Autowired
    public RestController(RestService restService) {
        this.restService = restService;
    }

    @RequestMapping(value = "invite", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public boolean inviteUserToRoom(@RequestHeader(value="X-Auth-Token") String token, @RequestBody RoomDto roomDto) throws Exception {
        logger.info("[Entering] inviteUserToRoom");
        User user = TokenUtils.getUserFromToken(token);
        restService.inviteUserToRoom(user.getUsername(), roomDto.getRoomId());
        return true;
    }

    @RequestMapping(value = "gifts", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public List<Gift> getGifts(@RequestHeader(value="X-Auth-Token") String token, @RequestBody RoomDto roomDto) throws Exception {
        logger.info("[Entering ] getGifts");
        User user = TokenUtils.getUserFromToken(token);
        return restService.getGifts(user.getUsername(), roomDto.getRoomId());
    }

    @RequestMapping(value = "rooms", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Room> getRooms(@RequestHeader(value="X-Auth-Token") String token) throws Exception {
        logger.info("[Entering] getRooms");
        User user = TokenUtils.getUserFromToken(token);
        return restService.getCompleteRooms(user.getUsername());
    }

    @RequestMapping(value = "gift", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public boolean addGift(@RequestHeader(value="X-Auth-Token") String token, @RequestBody GiftDto giftDto) throws Exception {
        logger.info("[Entering] addGift");
        User user = TokenUtils.getUserFromToken(token);
        restService.addGift(user.getUsername(), giftDto.getRoomId(), giftDto.getName(), giftDto.getPrice(), giftDto.getAmount());
        return true;
    }

    @RequestMapping(value = "gift", method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public boolean updateGift(@RequestHeader(value="X-Auth-Token") String token, @RequestBody GiftDto giftDto) throws Exception {
        logger.info("[Entering] updateGift");
        User user = TokenUtils.getUserFromToken(token);
        restService.updateGift(user.getUsername(), giftDto.getRoomId(), giftDto.getId(), giftDto.getAmount());
        return true;
    }

    @RequestMapping(value = "room", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public boolean addRoom(@RequestHeader(value="X-Auth-Token") String token, @RequestBody RoomDto roomDto) throws Exception {
        logger.info("[Entering] addRoom");
        User user = TokenUtils.getUserFromToken(token);
        restService.addRoom(user.getUsername(), roomDto.getRoomName(), roomDto.getOccasion());
        return true;
    }

    @RequestMapping(value = "gift", method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public boolean deleteGift(@RequestHeader(value="X-Auth-Token") String token, @RequestBody GiftDto giftDto) throws Exception {
        logger.info("[Entering] deleteGift");
        User user = TokenUtils.getUserFromToken(token);
        restService.deleteGift(user.getUsername(), giftDto.getRoomId());
        return true;
    }

    @RequestMapping(value = "room", method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public boolean deleteRoom(@RequestHeader(value="X-Auth-Token") String token, @RequestBody RoomDto roomDto) throws Exception {
        logger.info("[Entering] deleteRoom");
        User user = TokenUtils.getUserFromToken(token);
        restService.deleteRoom(user.getUsername(), roomDto.getRoomId());
        return true;
    }

    @RequestMapping(value = "authentication", method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE)
    public String authenticate(@RequestBody final UserDto userDto) throws Exception {
        logger.info("[Entering] authenticate");
        User user = restService.authenticate(userDto.getUsername(), userDto.getPassword());
        return TokenUtils.generateToken(user);
    }

    @RequestMapping(value = "authentication", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public boolean authenticate(@RequestHeader(value="X-Auth-Token") String token) throws Exception {
        logger.info("[Entering] authenticate (delete)");
        // Todo: Supprimer token - jouer sur validité
        return false;
    }

    @RequestMapping(value = "register", method = RequestMethod.POST,
            produces = MediaType.TEXT_PLAIN_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public String register(@RequestBody final UserDto userDto) throws Exception {
        logger.info("[Entering] register");
        User user = restService.createUser(userDto.getUsername(), userDto.getPassword());
        return TokenUtils.generateToken(user);
    }

    @RequestMapping(value = "lulu", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Room> lulu(@RequestHeader(value="X-Auth-Token") String token) throws Exception {
        logger.info("[Entering] lulu");
        List<Room> rooms = new ArrayList<>();
        List<Gift> gifts = new ArrayList<>();

        User user1 = new User();
        user1.setUsername("Jean-Bertrand");
        user1.setId(1);
        User user2 = new User();
        user2.setUsername("Marie-Pierre");
        user2.setId(2);

        Gift gift = new Gift(1);
        gift.setName("Ours en peluche");
        gift.setPrice(22.05);
        gift.getAmountByUser().put(1, 7d);
        gift.getUserById().put(1, user1);
        gift.getAmountByUser().put(2, 12d);
        gift.getUserById().put(2, user2);
        gifts.add(gift);
        gift = new Gift(2);
        gift.setName("Playstation 8");
        gift.setPrice(455.99);
        gift.getAmountByUser().put(1, 60d);
        gift.getUserById().put(1, user1);
        gift.getAmountByUser().put(2, 35d);
        gift.getUserById().put(2, user2);
        gifts.add(gift);
        rooms.add(new Room(0, "John Doe", "Anniversaire", gifts));
        rooms.add(new Room(1, "Jane Doe", "Noël"));
        rooms.add(new Room(2, "Jean-Charles Dupond", "Pot de départ"));
        return rooms;
    }
}
