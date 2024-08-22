package com.main.agency_booking.reservation.controller;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;
import static jakarta.ws.rs.core.Response.ok;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.main.agency_booking.reservation.data.dto.ReservationDto;
import com.main.agency_booking.reservation.data.dto.ReservationRequest;
import com.main.agency_booking.reservation.data.model.Reservation;
import com.main.agency_booking.reservation.manager.ReservationManagerImp;
import com.main.agency_booking.utils.HttpResponse;
import jakarta.validation.ValidationException;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Component
@Path("/reservation")
 public class ReservationController {
    
    ReservationManagerImp reservationManager;
    
    @Autowired
    public ReservationController(ReservationManagerImp reservationManager) {
        this.reservationManager = reservationManager;
    }
    
    @GET
    @Path("/{hotelId}/hotel/all")
    @Produces(value = APPLICATION_JSON)
    public Response getAllHotelReservations(
            @PathParam("hotelId") long hotelId,
            @QueryParam("page") int page,
            @QueryParam("size") int size) {
        try {
            List<Reservation> guestReservations = reservationManager.getAllHotelReservations(hotelId, page, size);
            List<ReservationDto> response = guestReservations.stream().map(Reservation::getDto).toList();
            return Response.ok().entity(new HttpResponse<>(response)).build();
        } catch (ValidationException e) {
            log.error("Failed to retrieve hotel reservations {}", e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(new HttpResponse<>(e.getMessage())).build();
        } catch (Exception e) {
            String somethingWentWrong = "Something else went wrong";
            log.error("{} {}", somethingWentWrong, e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new HttpResponse<>(somethingWentWrong))
                    .build();
        }
    }
    
    @GET
    @Path("/{guestId}/user/all")
    @Produces(value = APPLICATION_JSON)
    public Response getAllReservations(@PathParam("guestId") long guestId) {
        try {
            List<Reservation> guestReservations = reservationManager.findAllReservations(guestId);
            List<ReservationDto> response = guestReservations.stream().map(Reservation::getDto).toList();
            return Response.ok().entity(new HttpResponse<>(response)).build();
        } catch (ValidationException e) {
            log.error("Failed to retrieve guest reservations {}", e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(new HttpResponse<>(e.getMessage())).build();
        } catch (Exception e) {
            String somethingWentWrong = "Something else went wrong";
            log.error("{} {}", somethingWentWrong, e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new HttpResponse<>(somethingWentWrong))
                    .build();
        }
    }
    
    @POST
    @Path("/add")
    @Consumes(value = APPLICATION_JSON)
    @Produces(value = APPLICATION_JSON)
    public Response makeReservation(ReservationRequest reservationDto) {
        try {
            Reservation reservation = reservationManager.createReservation(reservationDto);
            ReservationDto response = new ReservationDto(reservation);
            return Response.ok().entity(new HttpResponse<>(response)).build();
        } catch (ValidationException e) {
            log.error("Failed to make a reservation {}", e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(new HttpResponse<>(e.getMessage())).build();
        } catch (Exception e) {
            String somethingWentWrong = "Something else went wrong";
            log.error("{} {}", somethingWentWrong, e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new HttpResponse<>(somethingWentWrong))
                    .build();
        }
    }
    
    @DELETE
    @Path("/{reservationId}/delete")
    @Produces(value = APPLICATION_JSON)
    public Response deleteReservation(@PathParam("reservationId") Long reservationId) {
        try {
            reservationManager.deleteReservation(reservationId);
            return ok().build();
        } catch (ValidationException e) {
            log.error("Failed to delete a reservation {}", e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(new HttpResponse<>(e.getMessage())).build();
        } catch (Exception e) {
            String somethingWentWrong = "Something else went wrong";
            log.error("{} {}", somethingWentWrong, e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(somethingWentWrong).build();
        }
    }
    
    @PATCH
    @Path("/{reservationId}/update")
    @Produces(value = APPLICATION_JSON)
    public Response updateReservationDate(
            @PathParam("reservationId") Long reservationId,
            @QueryParam("checkInDate") String checkInDateStr,
            @QueryParam("checkOutDate") String checkOutDateStr
    ) {
        try {
            LocalDateTime checkInDate = LocalDateTime.parse(checkInDateStr);
            LocalDateTime checkOutDate = LocalDateTime.parse(checkOutDateStr);
            Reservation updated = reservationManager.updateReservationDate(reservationId, checkInDate, checkOutDate);
            return Response.ok().entity(new HttpResponse<>(updated.getDto())).build();
        } catch (ValidationException e) {
            log.error("Failed to update a reservation {}", e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(new HttpResponse<>(e.getMessage())).build();
        } catch (Exception e) {
            String somethingWentWrong = "Something else went wrong";
            log.error("{} {}", somethingWentWrong, e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new HttpResponse<>(somethingWentWrong))
                    .build();
        }
    }
}
