package io.pivotal.pal.tracker;

import io.pivotal.pal.tracker.TimeEntry;
import io.pivotal.pal.tracker.TimeEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/time")
public class TimeEntryController {

    private TimeEntryRepository timeEntryRepository;

    public TimeEntryController(TimeEntryRepository timeEntryRepository){

        this.timeEntryRepository = timeEntryRepository;
    }

    @GetMapping(value ="/read", produces = "application/json")
    public ResponseEntity<TimeEntry> read(long l) {

        TimeEntry toFind = timeEntryRepository.find(l);

        if( toFind == null  )
        {
            return new ResponseEntity(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(toFind, HttpStatus.OK);
    }

    @PostMapping(value = "/create", consumes = "application/json", produces = "application/json")
    public ResponseEntity create(TimeEntry timeEntryToCreate) {
        return new ResponseEntity(timeEntryRepository.create(timeEntryToCreate), HttpStatus.CREATED);
    }

    @DeleteMapping(value = "delete", consumes = "application/json")
    public ResponseEntity<TimeEntry> delete(long l) {

        // TODO should probably returned the deleted item
        timeEntryRepository.delete(l);

        return new ResponseEntity<>(new TimeEntry(), HttpStatus.NO_CONTENT);
    }

    @GetMapping("/list")
    public ResponseEntity<List<TimeEntry>> list() {
        return new ResponseEntity<>(timeEntryRepository.list(), HttpStatus.OK);
    }

    @PutMapping(value = "/update", consumes = "application/json", produces = "application/json")
    public ResponseEntity update(long l, TimeEntry expected) {

        TimeEntry entry = timeEntryRepository.update(l, expected);

        if( entry != null){
            return new ResponseEntity(entry, HttpStatus.OK);
        }
        return new ResponseEntity(null, HttpStatus.NOT_FOUND);
    }
}
