package fasttrackit.course9;

import fasttrackit.course9.model.entity.*;
import fasttrackit.course9.repository.cleanup.CleanupRepository;
import fasttrackit.course9.repository.review.ReviewRepository;
import fasttrackit.course9.repository.room.RoomRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.util.List;

@SpringBootApplication
public class Course9HotelApiOnMongoApplication {

    public static void main(String[] args) {
        SpringApplication.run(Course9HotelApiOnMongoApplication.class, args);
    }

    @Bean
    CommandLineRunner dataLoader(RoomRepository roomRepository,
                                 CleanupRepository cleanupRepository,
                                 ReviewRepository reviewRepository) {
        return args -> {
            roomRepository.saveAll(List.of(Room.builder()
                            .number("220A")
                            .etaj(1)
                            .hotelName("hotel2")
                            .roomFacilities(RoomFacilities.builder()
                                    .doubleBed(true)
                                    .tv(true).build())
                            .build(),
                    Room.builder()
                            .number("220A")
                            .etaj(2)
                            .hotelName("hotel1")
                            .roomFacilities(RoomFacilities.builder()
                                    .doubleBed(false)
                                    .tv(false).build())
                            .build(),
                    Room.builder()
                            .number("220B")
                            .etaj(1)
                            .hotelName("hotel1")
                            .roomFacilities(RoomFacilities.builder()
                                    .doubleBed(true)
                                    .tv(true).build())
                            .build(),
                    Room.builder()
                            .number("220B")
                            .etaj(2)
                            .hotelName("hotel2")
                            .roomFacilities(RoomFacilities.builder()
                                    .doubleBed(true)
                                    .tv(false).build())
                            .build()
            ));
            cleanupRepository.saveAll(List.of(
                    Cleanup.builder()
                            .roomId("6093b3a8a0a84e40cc6ad8ed")
                            .data(LocalDate.now())
                            .proceduri(List.of(CleaningProcedure.builder()
                                            .name("Procedura 1")
                                            .outcome(1).build(),
                                    CleaningProcedure.builder()
                                            .name("Procedura 2")
                                            .outcome(2).build()))
                            .build()

            ));
            reviewRepository.saveAll(List.of(
                    Review.builder()
                            .roomId("6093b3a8a0a84e40cc6ad8ed")
                            .mesaj("Best Room")
                            .rating(5)
                            .turist("Adrian").build(),
                    Review.builder()
                            .roomId("6093b3a8a0a84e40cc6ad8ed")
                            .mesaj("Lux Room")
                            .rating(4)
                            .turist("Gigi").build(),
                    Review.builder()
                            .roomId("6093b3a8a0a84e40cc6ad8ed")
                            .mesaj("Great Room")
                            .rating(3)
                            .turist("Titi").build()
            ));
        };
    }
}
