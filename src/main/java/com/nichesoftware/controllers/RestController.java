package com.nichesoftware.controllers;

import com.nichesoftware.dto.GiftDto;
import com.nichesoftware.dto.PersonDto;
import com.nichesoftware.dto.UserDto;
import com.nichesoftware.model.Gift;
import com.nichesoftware.model.Person;
import com.nichesoftware.model.User;
import com.nichesoftware.services.IRestService;
import com.nichesoftware.services.RestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
@Controller
public class RestController {

    /**
     * Service
     */
    private IRestService restService;

    @Autowired
    public RestController(RestService restService) {
        this.restService = restService;
    }

    @RequestMapping(value = "persons", method = RequestMethod.GET)
    @ResponseBody
    public List<Person> getPersons(@RequestHeader(value="X-Auth-Token") String token) throws Exception {
        User user = TokenUtils.getUserFromToken(token);

        return restService.getAllPersons(user.getUsername());
    }

    @RequestMapping(value = "person", method = RequestMethod.POST)
    @ResponseBody
    public boolean addPerson(@RequestHeader(value="X-Auth-Token") String token, @RequestBody PersonDto personDto) throws Exception {
        User user = TokenUtils.getUserFromToken(token);
        restService.addPerson(user.getUsername(), personDto.getFirstName(), personDto.getLastName());
        // Si aucune exception n'a été levée
        return true;
    }

    @RequestMapping(value = "person", method = RequestMethod.GET)
    @ResponseBody
    public List<Person> getPersons(@RequestHeader(value="X-Auth-Token") String token, @RequestBody PersonDto personDto) throws Exception {
        User user = TokenUtils.getUserFromToken(token);
        return restService.getPersons(user.getUsername(), personDto.getFirstName(), personDto.getLastName());
    }

    @RequestMapping(value = "gifts", method = RequestMethod.GET)
    @ResponseBody
    public List<Person> getGifts(@RequestHeader(value="X-Auth-Token") String token) throws Exception {
        User user = TokenUtils.getUserFromToken(token);
        return restService.getGifts(user.getUsername());
    }

    @RequestMapping(value = "gifts", method = RequestMethod.POST)
    @ResponseBody
    public boolean addGift(@RequestHeader(value="X-Auth-Token") String token, @RequestBody GiftDto giftDto) throws Exception {
        User user = TokenUtils.getUserFromToken(token);
        restService.addGift(user.getUsername(), giftDto.getPersonId(), giftDto.getName(), giftDto.getPrice(), giftDto.getAmount());

        return true;
    }

    @RequestMapping(value = "lulu", method = RequestMethod.GET)
    @ResponseBody
    public List<Person> lulu(@RequestHeader(value="X-Auth-Token") String token) throws Exception {
        List<Person> persons = new ArrayList<Person>();
        List<Gift> gifts = new ArrayList<Gift>();
        Gift gift = new Gift(1);
        gift.setName("Ours en peluche");
        gift.setPrice(22.05);
        gift.setAmount(10);
        gifts.add(gift);
        gift = new Gift(2);
        gift.setName("Playstation 8");
        gift.setPrice(455.99);
        gift.setAmount(60);
        gifts.add(gift);
        persons.add(new Person(0, "John", "Doe", gifts));
        persons.add(new Person(1, "Jane", "Doe"));
        persons.add(new Person(2, "Jean-Charles", "Dupond"));
        return persons;
    }

    @RequestMapping(value = "authentication", method = RequestMethod.POST)
    @ResponseBody
    public String authenticate(@RequestBody final UserDto userDto) throws Exception {
        User user = restService.authenticate(userDto.getUsername(), userDto.getPassword());
        final String token = TokenUtils.generateToken(user);
        return token;
    }

    @RequestMapping(value = "authentication", method = RequestMethod.DELETE)
    @ResponseBody
    public boolean authenticate(@RequestHeader(value="X-Auth-Token") String token) throws Exception {
        // Todo: Supprimer token - jouer sur validité
        return false;
    }

    @RequestMapping(value = "register", method = RequestMethod.POST)
    @ResponseBody
    public String register(@RequestBody final UserDto userDto) throws Exception {
        User user = restService.createUser(userDto.getUsername(), userDto.getPassword());
        final String token = TokenUtils.generateToken(user);
        return token;

    }

}
