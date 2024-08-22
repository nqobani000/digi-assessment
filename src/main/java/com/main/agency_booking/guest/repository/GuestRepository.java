package com.main.agency_booking.guest.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.main.agency_booking.guest.data.model.Guest;
import jakarta.transaction.Transactional;


@Repository
@Transactional
public interface GuestRepository extends JpaRepository<Guest, Long> {
    Optional<Guest> findByEmail(String email);
}
