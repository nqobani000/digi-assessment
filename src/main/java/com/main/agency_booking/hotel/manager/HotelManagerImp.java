package com.main.agency_booking.hotel.manager;

import static com.main.agency_booking.utils.StringUtil.isNullOrEmpty;
import static com.main.agency_booking.utils.ValidatorUtils.isValidEmailAddress;
import static com.main.agency_booking.utils.ValidatorUtils.isValidSouthAfricanCellNumber;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.main.agency_booking.hotel.data.dto.HotelDto;
import com.main.agency_booking.hotel.data.model.Hotel;
import com.main.agency_booking.hotel.repository.HotelRepository;
import com.main.agency_booking.utils.ValidatorUtils;
import jakarta.validation.ValidationException;


@Service
public class HotelManagerImp implements HotelManager {
    
    HotelRepository hotelRepository;
    
    @Autowired
    public HotelManagerImp(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }
    
    @Override
    public List<Hotel> getHotelsPaginated(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return hotelRepository.findAll(pageRequest).getContent();
    }
    
    @Override
    public Hotel addHotel(HotelDto dto) {
        Hotel hotel = new Hotel(dto);
        validate(hotel);
        
        if (hotelRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new ValidationException(ValidatorUtils.HotelDtoValidationMessages.EMAIL_ALREADY_EXISTS_VALIDATION_MESSAGE);
        }
        
        return hotelRepository.save(hotel);
    }
    
    @Override
    public Hotel findHotelById(Long id) {
        Optional<Hotel> hotelOptional = this.hotelRepository.findById(id);
        return hotelOptional.orElse(null);
    }
    
    public void validate(Hotel hotel) throws ValidationException {
        if (isNullOrEmpty(hotel.getName())) {
            throw new ValidationException(ValidatorUtils.HotelDtoValidationMessages.NAME_VALIDATION_MESSAGE);
        }
        
        if (isNullOrEmpty(hotel.getAddress())) {
            throw new ValidationException(ValidatorUtils.HotelDtoValidationMessages.ADDRESS_VALIDATION_MESSAGE);
        }
        
        String email = hotel.getEmail();
        if (isNullOrEmpty(email) || !isValidEmailAddress(email)) {
            throw new ValidationException(ValidatorUtils.HotelDtoValidationMessages.EMAIL_VALIDATION_MESSAGE);
        }
        
        String phone = hotel.getPhone();
        if (isNullOrEmpty(phone) || !isValidSouthAfricanCellNumber(phone)) {
            throw new ValidationException(ValidatorUtils.HotelDtoValidationMessages.CELL_NO_VALIDATION_MESSAGE);
        }
    }
}
