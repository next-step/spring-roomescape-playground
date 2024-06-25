package roomescape.controller;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/reservations")
                .map(ReservationResponse::from)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

        return ResponseEntity.created(location).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {

        return ResponseEntity.noContent().build();
    }
}
