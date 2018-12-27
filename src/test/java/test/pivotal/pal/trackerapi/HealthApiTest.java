package test.pivotal.pal.trackerapi;

import com.jayway.jsonpath.DocumentContext;
import io.pivotal.pal.tracker.PalTrackerApplication;
import io.pivotal.pal.tracker.TimeEntry;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;

import static com.jayway.jsonpath.JsonPath.parse;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = PalTrackerApplication.class, webEnvironment = RANDOM_PORT)
public class HealthApiTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void healthTest() {
        // test db is empty which will result in the health acutator to return DOWN...populating DB
        createEntries();

        ResponseEntity<String> response = this.restTemplate.getForEntity("/health", String.class);


        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        DocumentContext healthJson = parse(response.getBody());

        assertThat(healthJson.read("$.status", String.class)).isEqualTo("UP");
        assertThat(healthJson.read("$.db.status", String.class)).isEqualTo("UP");
        assertThat(healthJson.read("$.diskSpace.status", String.class)).isEqualTo("UP");
    }


    private void createEntries() {

        for (int i = 0; i < 6; i++) {

            TimeEntry timeEntry = new TimeEntry(i+1, i+100,
                    i+1000, LocalDate.parse("2017-01-08"), 8);

            HttpEntity<TimeEntry> entity = new HttpEntity<>(timeEntry);

            restTemplate.exchange("/time-entries", HttpMethod.POST, entity, TimeEntry.class);
        }

    }
}
