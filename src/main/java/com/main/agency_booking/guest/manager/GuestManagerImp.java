package com.main.agency_booking.guest.manager;

import static com.main.agency_booking.utils.StringUtil.containsSymbol;
import static com.main.agency_booking.utils.StringUtil.isNullOrEmpty;
import static com.main.agency_booking.utils.ValidatorUtils.GuestDtoValidationMessages.*;
import static com.main.agency_booking.utils.ValidatorUtils.isValidSouthAfricanCellNumber;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.main.agency_booking.guest.data.dto.GuestDto;
import com.main.agency_booking.guest.data.model.Guest;
import com.main.agency_booking.guest.exceptions.DuplicateUserException;
import com.main.agency_booking.guest.repository.GuestRepository;
import jakarta.validation.ValidationException;


@Service
public class GuestManagerImp implements GuestManager {
    
    private final GuestRepository guestRepository;
    
    @Autowired
    public GuestManagerImp(GuestRepository guestRepository) {
        this.guestRepository = guestRepository;
    }
    
    @Override
    public Guest addGuest(GuestDto dto) {
        Guest guestEntity = new Guest(dto);
        validate(guestEntity);
        if (findGuestByEmail(dto.getEmail()) != null) {
            throw new DuplicateUserException(GUEST_ALREADY_EXISTS_VALIDATION_MESSAGE);
        }
        return guestRepository.save(guestEntity);
    }
    
    @Override
    public Guest findGuestByEmail(String email) {
        if (!isNullOrEmpty(email)) {
            Optional<Guest> optionalGuest = guestRepository.findByEmail(email);
            if (optionalGuest.isPresent()) {
                return optionalGuest.get();
            }
        }
        return null;
    }
    
    @Override
    public Guest findGuestById(long guestId) {
        Optional<Guest> optionalGuest = guestRepository.findById(guestId);
        return optionalGuest.orElse(null);
    }
    
    public void validate(Guest guest) throws ValidationException {
        if (isNullOrEmpty(guest.getFirstName())) {
            throw new ValidationException(FIRST_NAME_VALIDATION_MESSAGE);
        }
        
        if (isNullOrEmpty(guest.getLastName())) {
            throw new ValidationException(LAST_NAME_VALIDATION_MESSAGE);
        }
        
        String email = guest.getEmail();
        if (isNullOrEmpty(email) || !containsSymbol(email, "@")) {
            throw new ValidationException(EMAIL_VALIDATION_MESSAGE);
        }
        
        String phone = guest.getPhone();
        if (isNullOrEmpty(phone) || !isValidSouthAfricanCellNumber(phone)) {
            throw new ValidationException(CELL_NO_VALIDATION_MESSAGE);
        }
    }
}
