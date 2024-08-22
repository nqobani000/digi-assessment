package com.main.agency_booking.hotel.controller;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.main.agency_booking.hotel.data.dto.HotelDto;
import com.main.agency_booking.hotel.data.model.Hotel;
import com.main.agency_booking.hotel.manager.HotelManagerImp;
import com.main.agency_booking.utils.HttpResponse;
import jakarta.validation.ValidationException;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Component
@Path("/hotel")
public class HotelController {
    
    private final HotelManagerImp hotelManager;
    
    @Autowired
    public HotelController(HotelManagerImp hotelManager) {
        this.hotelManager = hotelManager;
    }
    
    @GET
    @Path("/all")
    @Produces(value = APPLICATION_JSON)
    public Response getHotelsPaginated(@QueryParam("page") @DefaultValue("0") int page, @QueryParam("size") @DefaultValue("15")  Integer size) {
        try {
            List<Hotel> hotels = hotelManager.getHotelsPaginated(page, size);
            List<HotelDto> hotelDtoList = hotels.stream().map(Hotel::getDto).toList();
            return Response.ok(hotelDtoList).build();
        } catch (ValidationException e) {
            log.error("Error while getting hotels", e);
            return Response.status(Response.Status.BAD_REQUEST).entity(new HttpResponse<>(e.getMessage())).build();
        }
        catch (Exception e) {
            log.error("Error while getting hotels", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new HttpResponse<>(e.getMessage())).build();
        }
    }
    
    @POST
    @Path("/add")
    @Produces(value = APPLICATION_JSON)
    @Consumes(value = APPLICATION_JSON)
    public Response addHotel(HotelDto hotelDto) {
        try {
            Hotel hotel = hotelManager.addHotel(hotelDto);
            return Response.ok(hotel.getDto()).build();
        } catch (ValidationException e) {
            log.error("Error while adding hotel", e);
            return Response.status(Response.Status.BAD_REQUEST).entity(new HttpResponse<>(e.getMessage())).build();
        } catch (Exception e) {
            log.error("Error while adding hotel", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new HttpResponse<>(e.getMessage())).build();
        }
    }
}
