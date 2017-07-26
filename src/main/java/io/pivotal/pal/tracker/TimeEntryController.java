package io.pivotal.pal.tracker;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.DistributionSummary;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/time-entries")
public class TimeEntryController {

    private TimeEntryRepository timeEntryRepository;
    private final DistributionSummary timeEntrySummary;
    private final Counter actionCounter;

    public TimeEntryController(TimeEntryRepository timeEntryRepository,
                               DistributionSummary timeEntrySummary,
                               Counter actionCounter){

        this.timeEntryRepository = timeEntryRepository;
        this.timeEntrySummary = timeEntrySummary;
        this.actionCounter = actionCounter;
    }

    @GetMapping(value ="/{id}", produces = "application/json")
    public ResponseEntity<TimeEntry> read(@PathVariable long id) {

        TimeEntry toFind = timeEntryRepository.find(id);

        if( toFind == null  )
        {
            return new ResponseEntity(null, HttpStatus.NOT_FOUND);
        }
        actionCounter.increment();
        return new ResponseEntity(toFind, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity create(@RequestBody TimeEntry timeEntryToCreate) {
        TimeEntry createdTimeEntry = timeEntryRepository.create(timeEntryToCreate);
        actionCounter.increment();
        timeEntrySummary.record(timeEntryRepository.list().size());

        return new ResponseEntity<>(createdTimeEntry, HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<TimeEntry> delete(@PathVariable long id) {

        timeEntryRepository.delete(id);
        actionCounter.increment();
        timeEntrySummary.record(timeEntryRepository.list().size());
        return new ResponseEntity<>(new TimeEntry(), HttpStatus.NO_CONTENT);
    }

    @GetMapping()
    public ResponseEntity<List<TimeEntry>> list() {
        actionCounter.increment();
        return new ResponseEntity<>(timeEntryRepository.list(), HttpStatus.OK);
    }

    @PutMapping(value = "/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity update(@PathVariable long id, @RequestBody TimeEntry expected) {

        TimeEntry entry = timeEntryRepository.update(id, expected);

        if( entry != null){
            actionCounter.increment();
            return new ResponseEntity(entry, HttpStatus.OK);
        }
        return new ResponseEntity(null, HttpStatus.NOT_FOUND);
    }
}
