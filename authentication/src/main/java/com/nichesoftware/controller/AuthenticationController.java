package com.nichesoftware.controller;

import com.nichesoftware.User;
import com.nichesoftware.dto.DTO;
import com.nichesoftware.dto.Metadata;
import com.nichesoftware.dto.TokenDTO;
import com.nichesoftware.dto.UserDTO;
import com.nichesoftware.service.AuthenticationService;
import com.nichesoftware.service.IAuthenticationService;
import com.nichesoftware.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by n_che on 08/09/2016.
 */
@RestController
public class AuthenticationController extends BaseController {
    // Fields   --------------------------------------------------------------------------------------------------------
    private static final String REGISTER_PATH = "register";
    private static final String AUTHENTICATION_PATH = "authentication";
    private static final String AUTHORIZATION_PATH = "authorization";

    /**
     * Service
     */
    private IAuthenticationService service;

    // Constructors   --------------------------------------------------------------------------------------------------
    @Autowired
    public AuthenticationController(AuthenticationService service) {
        this.service = service;
    }

    // Methods   -------------------------------------------------------------------------------------------------------
    @RequestMapping(value = AUTHENTICATION_PATH,
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DTO<TokenDTO, Metadata>> authenticate(@RequestBody final UserDTO userDTO) throws Exception {
        logger.info("[Entering] authenticate");
        User user = service.authenticate(userDTO.getUsername(), userDTO.getPassword());
        String token = TokenUtils.generateUserToken(user);

        TokenDTO tokenDTO = new TokenDTO();
        tokenDTO.setToken(token);

        Metadata metadata = new Metadata();
        metadata.setUrl(AUTHENTICATION_PATH);

        DTO<TokenDTO, Metadata> dto = new DTO<>();
        dto.setData(tokenDTO);
        dto.setMetadata(metadata);

        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @RequestMapping(value = AUTHENTICATION_PATH,
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DTO<Boolean, Metadata>> authenticate(@RequestHeader(value="X-Auth-Token") String token) throws Exception {
        logger.info("[Entering] authenticate (delete)");
        // Todo: Supprimer token - jouer sur validité

        Metadata metadata = new Metadata();
        metadata.setUrl(AUTHENTICATION_PATH);

        DTO<Boolean, Metadata> dto = new DTO<>();
        // Todo : Modifier valeur retour lorsqu'implémenté
        dto.setData(Boolean.FALSE);
        dto.setMetadata(metadata);

        return new ResponseEntity<>(dto, HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = REGISTER_PATH,
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DTO<TokenDTO, Metadata>> register(@RequestBody final UserDTO userDTO) throws Exception {
        logger.info("[Entering] register");
        User user = service.createUser(userDTO.getUsername(), userDTO.getPassword(), userDTO.getPhoneNumber());
        String token = TokenUtils.generateUserToken(user);

        TokenDTO tokenDTO = new TokenDTO();
        tokenDTO.setToken(token);

        Metadata metadata = new Metadata();
        metadata.setUrl(REGISTER_PATH);

        DTO<TokenDTO, Metadata> dto = new DTO<>();
        dto.setData(tokenDTO);
        dto.setMetadata(metadata);

        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @RequestMapping(value = AUTHORIZATION_PATH,
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DTO<UserDTO, Metadata>> authorization(@RequestHeader(value="X-Auth-Token") String token) throws Exception {
        User userFromToken = TokenUtils.getUserFromToken(token);
        User generatedUser = service.findByUsername(userFromToken.getUsername());

        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(generatedUser.getUsername());
        userDTO.setId(generatedUser.getId());

        Metadata metadata = new Metadata();
        metadata.setUrl(AUTHORIZATION_PATH);

        DTO<UserDTO, Metadata> dto = new DTO<>();
        dto.setData(userDTO);
        dto.setMetadata(metadata);

        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
}
