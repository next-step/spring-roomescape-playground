package roomescape;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import roomescape.global.DatabaseInitialization;

@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
@SpringBootApplication
public class RoomescapeApplication implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(RoomescapeApplication.class, args);
    }


    @Autowired(required = false)
    private DatabaseInitialization databaseInitialization;

    @Override
    public void run(String... args) {
        if (databaseInitialization != null) {
            databaseInitialization.initializeTables();
        }
    }
}
