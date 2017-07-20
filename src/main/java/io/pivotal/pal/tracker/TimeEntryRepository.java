package io.pivotal.pal.tracker;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TimeEntryRepository {

    public TimeEntry find(Long id);

    public TimeEntry create(TimeEntry timeEntry);


    public TimeEntry update(Long id, TimeEntry timeEntry);

    public void delete(Long id);

    List<TimeEntry> list();
}
