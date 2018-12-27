package io.pivotal.pal.tracker;


import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class TimeEntryHealthIndicator implements HealthIndicator {

    private static final int MAX_TIME_ENTRIES = 5;
    private TimeEntryRepository timeEntryRepository;

    public TimeEntryHealthIndicator( TimeEntryRepository timeEntryRepository){
        this.timeEntryRepository = timeEntryRepository;
    }
    @Override
    public Health health() {

        Health.Builder builder = new Health.Builder();
        long size = timeEntryRepository.list().size();
        System.out.println("------- size: "+size);

        if( size < MAX_TIME_ENTRIES )
            builder.down();
        else
            builder.up();

        return builder.build();
    }
}
