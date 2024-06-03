package roomescape.time.db;


import java.util.List;

public interface TimeRepositoryImpl {

    List<Time> findAll();

    Time findById(Long id);

    Time save(Time time);

    void deleteById(Long id);
}
