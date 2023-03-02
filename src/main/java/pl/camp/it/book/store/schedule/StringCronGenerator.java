package pl.camp.it.book.store.schedule;

import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Log4j2
public class StringCronGenerator {

    //public static final Logger log = LogManager.getLogger(StringCronGenerator.class);

    @Scheduled(cron = "4,11,16,23,35,43,51,56 * * ? * *")
    public void generateString() throws InterruptedException {
        for (int i = 0; i < 100; i++) {
            log.info("Jakas informacja: " + i);
            log.debug("Jakis debug: " + i);
        }
    }
}
