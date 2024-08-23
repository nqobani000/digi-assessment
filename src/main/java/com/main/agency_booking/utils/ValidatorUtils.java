package com.main.agency_booking.utils;

import static com.main.agency_booking.utils.StringUtil.containsSymbol;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;


public class ValidatorUtils {
    
    public static final String SA_CELLPHONE_PATTERN = "^(0|\\+27)[6-8][0-9]{8}$";
    
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class HotelDtoValidationMessages {
        
        public static final String EMAIL_ALREADY_EXISTS_VALIDATION_MESSAGE = "Provide a valid hotel email address.";
        public static final String EMAIL_VALIDATION_MESSAGE = "Provide a valid hotel email address.";
        public static final String NAME_VALIDATION_MESSAGE = "Provide a valid hotel name.";
        public static final String HOTEL_NOT_FOUND_VALIDATION_MESSAGE = "Provide a valid hotel id.";
        public static final String ADDRESS_VALIDATION_MESSAGE = "Provide a valid hotel address.";
        public static final String CELL_NO_VALIDATION_MESSAGE = "Provide a valid S.A cell number for the Hotel.";
    }
    
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class GuestDtoValidationMessages {
        
        public static final String GUEST_NOT_FOUND_VALIDATION_MESSAGE = "Provide a valid guest email address.";
        public static final String GUEST_ALREADY_EXISTS_VALIDATION_MESSAGE = "A guest with the provided email address already exists.";
        public static final String FIRST_NAME_VALIDATION_MESSAGE = "Provide a valid first name for the guest.";
        public static final String LAST_NAME_VALIDATION_MESSAGE = "Provide a valid last name for the guest.";
        public static final String EMAIL_VALIDATION_MESSAGE = "Provide a valid guest email address.";
        public static final String CELL_NO_VALIDATION_MESSAGE = "Provide a valid S.A cell number for the guest.";
    }
    
    public static boolean isValidSouthAfricanCellNumber(String phoneNumber) {
        if (phoneNumber == null) {
            return false;
        }
        
        return phoneNumber.matches(SA_CELLPHONE_PATTERN);
    }
    
    public static boolean isValidEmailAddress(String email) {
        return containsSymbol(email, "@");
    }
}
