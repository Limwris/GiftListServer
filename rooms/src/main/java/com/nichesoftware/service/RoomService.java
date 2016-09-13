package com.nichesoftware.service;

import com.nichesoftware.dao.IRoomDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by n_che on 12/09/2016.
 */
@Service
public class RoomService implements IRoomService {
    // Fields   --------------------------------------------------------------------------------------------------------
    private static final Logger logger = LoggerFactory.getLogger(RoomService.class.getSimpleName());

    @Autowired
    private IRoomDAO roomDAO;

    // Methods   -------------------------------------------------------------------------------------------------------

    // Getters   -------------------------------------------------------------------------------------------------------
    public IRoomDAO getRoomDAO() {
        return roomDAO;
    }

    // Setters   -------------------------------------------------------------------------------------------------------
    public void setRoomDAO(IRoomDAO roomDAO) {
        this.roomDAO = roomDAO;
    }
}
