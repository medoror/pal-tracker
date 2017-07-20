package io.pivotal.pal.tracker;

import java.util.ArrayList;
import java.util.List;

public class InMemoryTimeEntryRepository implements TimeEntryRepository {

    private List<TimeEntry> entries = new ArrayList<>();
    public TimeEntry find(Long id) {
        return entries.stream().filter(entry -> entry.getId().equals(id))
                .findAny()
                .orElse(null);
    }

    public TimeEntry create(TimeEntry timeEntry) {
        int currentId = entries.size() +1;

        TimeEntry returnEntry = new TimeEntry(currentId, timeEntry.getProjectId(), timeEntry.getUserId(),
                timeEntry.getDate(), timeEntry.getHours());

        entries.add(returnEntry);

        return returnEntry;
    }


    public TimeEntry update(Long id, TimeEntry timeEntry) {
        TimeEntry oldEntry = find(id);

        if( oldEntry != null ){

            //delete the old entry
            delete(id);

            //reinsert with new info
            TimeEntry newEntry = new TimeEntry(oldEntry.getId(), timeEntry.getProjectId(),
                    timeEntry.getUserId(), timeEntry.getDate(), timeEntry.getHours());

            entries.add(newEntry);

            return newEntry;
        }

        //couldnt find the entry to update...return null
        return null;
    }

    public void delete(Long id) {
        entries.removeIf(entry -> entry.getId().equals(id));
    }

    public List<TimeEntry> list() {
        return entries;
    }
}
