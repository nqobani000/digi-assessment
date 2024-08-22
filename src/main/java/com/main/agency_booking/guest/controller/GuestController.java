package com.main.agency_booking.guest.controller;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.main.agency_booking.guest.data.dto.GuestDto;
import com.main.agency_booking.guest.data.model.Guest;
import com.main.agency_booking.guest.exceptions.DuplicateUserException;
import com.main.agency_booking.guest.manager.GuestManagerImp;
import com.main.agency_booking.utils.HttpResponse;
import jakarta.validation.ValidationException;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Component
@Path("/guest")
public class GuestController {
    
    private final GuestManagerImp guestManager;
    
    @Autowired
    public GuestController(GuestManagerImp guestManager) {
        this.guestManager = guestManager;
    }
    
    @POST
    @Path("/add")
    @Consumes(value = APPLICATION_JSON)
    @Produces(value = APPLICATION_JSON)
    public Response addGuest(GuestDto guestDto) {
        try {
            Guest guest = guestManager.addGuest(guestDto);
            HttpResponse<GuestDto> response = new HttpResponse<>(guest.getDto());
            return Response.ok(response).build();
        } catch (DuplicateUserException | ValidationException e) {
            logException(e);
            return Response.status(Response.Status.BAD_REQUEST).entity(new HttpResponse<>(e.getMessage())).build();
        } catch (Exception e) {
            logException(e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new HttpResponse<>("Internal Server Error")).build();
        }
    }
    
    private static void logException(Exception e) {
        log.error("An Error Occurred while adding a new guest {}", e.getMessage());
    }
}
