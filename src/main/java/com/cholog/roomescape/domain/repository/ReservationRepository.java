package com.cholog.roomescape.domain.repository;

import com.cholog.roomescape.domain.entity.Reservation;
import com.cholog.roomescape.domain.exception.badrequest.TimeNotValidException;
import com.cholog.roomescape.domain.exception.conflict.ReservationConflictException;

import java.util.List;
import java.util.Optional;

public interface ReservationRepository {

    /**
     * @throws TimeNotValidException 예약이 참조하는 시간 객체가 존재하지 않는 경우 이 예외를 던집니다.
     * @throws ReservationConflictException 이미 예약된 시각에 예약을 시도하는 경우 이 예외를 던집니다.
     */
    Reservation save(Reservation reservation);

    List<Reservation> findAll();

    void delete(Reservation reservation);

    Optional<Reservation> findById(Long reservationId);
}
