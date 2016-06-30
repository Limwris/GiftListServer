package com.nichesoftware.controllers;

import com.nichesoftware.dto.*;
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
    public boolean inviteUserToRoom(@RequestHeader(value="X-Auth-Token") String token,
                                    @RequestBody InvitationDto invitationDto) throws Exception {
        logger.info("[Entering] inviteUserToRoom");
        TokenUtils.getUserFromToken(token);
        restService.inviteUserToRoom(invitationDto.getUsername(),
                invitationDto.getRoomId());
        return true;
    }

    @RequestMapping(value = "accept", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public boolean acceptInvitationToRoom(@RequestHeader(value="X-Auth-Token") String token,
                                          @RequestBody AcceptInvitationDto acceptInvitationDto) throws Exception {
        logger.info("[Entering] acceptInvitationToRoom");
        User user = TokenUtils.getUserFromToken(token);
        restService.acceptInvitationToRoom(user.getUsername(), acceptInvitationDto.getToken(),
                acceptInvitationDto.getRoomId());
        return true;
    }

    @RequestMapping(value = "gifts", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public List<Gift> getGifts(@RequestHeader(value="X-Auth-Token") String token,
                               @RequestBody RoomDto roomDto) throws Exception {
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
    public Gift addGift(@RequestHeader(value="X-Auth-Token") String token,
                        @RequestBody GiftDto giftDto) throws Exception {
        logger.info("[Entering] addGift");
        User user = TokenUtils.getUserFromToken(token);
        return restService.addGift(user.getUsername(), giftDto.getRoomId(), giftDto.getName(),
                giftDto.getPrice(), giftDto.getAmount());
    }

    @RequestMapping(value = "gift", method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Gift updateGift(@RequestHeader(value="X-Auth-Token") String token,
                           @RequestBody GiftDto giftDto) throws Exception {
        logger.info("[Entering] updateGift");
        User user = TokenUtils.getUserFromToken(token);
        return restService.updateGift(user.getUsername(), giftDto.getRoomId(), giftDto.getId(), giftDto.getAmount());
    }

    @RequestMapping(value = "room/{roomId}", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Room getRoomInformation(@RequestHeader(value="X-Auth-Token") String token,
                        @PathVariable("roomId") int roomId) throws Exception {
        logger.info("[Entering] getRoomInformation");
        User user = TokenUtils.getUserFromToken(token);
        return restService.getRoom(roomId);
    }

    @RequestMapping(value = "room", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Room addRoom(@RequestHeader(value="X-Auth-Token") String token,
                        @RequestBody RoomDto roomDto) throws Exception {
        logger.info("[Entering] addRoom");
        User user = TokenUtils.getUserFromToken(token);
        return restService.addRoom(user.getUsername(), roomDto.getRoomName(), roomDto.getOccasion());
    }

    @RequestMapping(value = "gift", method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public boolean deleteGift(@RequestHeader(value="X-Auth-Token") String token,
                              @RequestBody GiftDto giftDto) throws Exception {
        logger.info("[Entering] deleteGift");
        User user = TokenUtils.getUserFromToken(token);
        restService.deleteGift(user.getUsername(), giftDto.getRoomId());
        return true;
    }

    @RequestMapping(value = "room", method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public boolean deleteRoom(@RequestHeader(value="X-Auth-Token") String token,
                              @RequestBody RoomDto roomDto) throws Exception {
        logger.info("[Entering] deleteRoom");
        User user = TokenUtils.getUserFromToken(token);
        restService.deleteRoom(user.getUsername(), roomDto.getRoomId());
        return true;
    }

    @RequestMapping(value = "authentication", method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE)
    public String authenticate(@RequestBody final UserDto userDto) throws Exception {
        logger.info("[Entering] authenticate");
        User user = restService.authenticate(userDto.getUsername(), userDto.getPassword());
        return TokenUtils.generateUserToken(user);
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
        User user = restService.createUser(userDto.getUsername(), userDto.getPassword(), userDto.getPhoneNumber());
        return TokenUtils.generateUserToken(user);
    }

    @RequestMapping(value = "gcm/{registerId}", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public boolean registerDevice(@RequestHeader(value="X-Auth-Token") String token,
                                  @PathVariable final String registerId) throws Exception {
        logger.info("[Entering] registerDevice");
        User user = TokenUtils.getUserFromToken(token);
        restService.updateGcmToken(user.getUsername(), registerId);
        return true;
    }

    @RequestMapping(value = "contacts", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public List<User> retreiveAvailableContacts(@RequestHeader(value="X-Auth-Token") String token,
                                                @RequestBody final ContactDto contactDto) throws Exception {
        logger.info("[Entering] retreiveAvailableContacts");
        TokenUtils.getUserFromToken(token); // Throws NotAuthorizedException if not valid token
        return restService.retreiveAvailableUsers(contactDto.getPhoneNumbers(), contactDto.getRoomId());
    }
}
