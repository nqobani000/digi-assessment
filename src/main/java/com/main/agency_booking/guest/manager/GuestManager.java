package com.main.agency_booking.guest.manager;

import com.main.agency_booking.guest.data.dto.GuestDto;
import com.main.agency_booking.guest.data.model.Guest;


public interface GuestManager {
    Guest addGuest(GuestDto guest);
    Guest findGuestByEmail(String email);
    Guest findGuestById(long guestId);
}
