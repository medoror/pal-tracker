package io.pivotal.pal.tracker;

import io.pivotal.pal.tracker.TimeEntry;
import io.pivotal.pal.tracker.TimeEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/time-entries")
public class TimeEntryController {

    private TimeEntryRepository timeEntryRepository;

    public TimeEntryController(TimeEntryRepository timeEntryRepository){

        this.timeEntryRepository = timeEntryRepository;
    }

    @GetMapping(value ="/{id}", produces = "application/json")
    public ResponseEntity<TimeEntry> read(@PathVariable long id) {

        TimeEntry toFind = timeEntryRepository.find(id);

        if( toFind == null  )
        {
            return new ResponseEntity(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(toFind, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity create(@RequestBody TimeEntry timeEntryToCreate) {
        return new ResponseEntity(timeEntryRepository.create(timeEntryToCreate), HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<TimeEntry> delete(@PathVariable long id) {

        // TODO should probably returned the deleted item
        timeEntryRepository.delete(id);

        return new ResponseEntity<>(new TimeEntry(), HttpStatus.NO_CONTENT);
    }

    @GetMapping()
    public ResponseEntity<List<TimeEntry>> list() {
        return new ResponseEntity<>(timeEntryRepository.list(), HttpStatus.OK);
    }

    @PutMapping(value = "/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity update(@PathVariable long id, @RequestBody TimeEntry expected) {

        TimeEntry entry = timeEntryRepository.update(id, expected);

        if( entry != null){
            return new ResponseEntity(entry, HttpStatus.OK);
        }
        return new ResponseEntity(null, HttpStatus.NOT_FOUND);
    }
}
