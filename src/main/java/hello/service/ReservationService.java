package hello.service;

import hello.exceptions.NotFoundReservationException;
import hello.exceptions.NotSelectTimeException;
import hello.repository.ReservationRepository;
import hello.controller.dto.CreateReservationDto;
import hello.service.dto.ReservationDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ReservationService {

    private final ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @Transactional(readOnly = true)
    public List<ReservationDto> getReservationList() {
        return reservationRepository.findAllReservations()
                .stream()
                .map(ReservationDto::from)
                .toList();
    }

    public ReservationDto save(CreateReservationDto dto) {
        try {
            long timeId = Long.parseLong(dto.getTime_id());
            return ReservationDto.from(reservationRepository.save(dto.getName(), dto.getDate(), timeId));
        } catch (NumberFormatException e) {
            throw new NotSelectTimeException();
        }
    }

    public void deleteById(Long id) {
        int count = reservationRepository.delete(id);
        if (count==0) throw new NotFoundReservationException();
    }
}
