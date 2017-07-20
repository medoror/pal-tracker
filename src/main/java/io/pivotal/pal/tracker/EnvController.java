package io.pivotal.pal.tracker;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class EnvController {


//    @Value("${PORT:NOT SET}")
//    String port;
//    @Value("${MEMORY_LIMIT:NOT SET}")
//    String memoryLimit;
//    @Value("${CF_INSTANCE_INDEX:NOT SET}")
//    String cfInstanceIndex;
//    @Value("${CF_INSTANCE_ADDR:NOT SET}")
//    String cfInstanceAddr;
//
//    // The method signature has errors in intellij
//    // https://stackoverflow.com/questions/26889970/intellij-incorrectly-saying-no-beans-of-type-found-for-autowired-repository
//    public EnvController(String port, String memLimit, String cfInstanceIndex, String cfInstanceAddr) {
//        this.port = port;
//        this.memoryLimit = memLimit;
//        this.cfInstanceIndex = cfInstanceIndex;
//        this.cfInstanceAddr = cfInstanceAddr;
//    }

    private final String port;
    private final String memoryLimit;
    private final String cfInstanceIndex;
    private final String cfInstanceAddress;

    public EnvController(
            @Value("${PORT:NOT SET}") String port,
            @Value("${MEMORY_LIMIT:NOT SET}") String memoryLimit,
            @Value("${CF_INSTANCE_INDEX:NOT SET}") String cfInstanceIndex,
            @Value("${CF_INSTANCE_ADDR:NOT SET}") String cfInstanceAddress
    ) {
        this.port = port;
        this.memoryLimit = memoryLimit;
        this.cfInstanceIndex = cfInstanceIndex;
        this.cfInstanceAddress = cfInstanceAddress;
    }

    @GetMapping("/env")
    public Map<String, String> getEnv() {
        Map<String, String> envMap = new HashMap<>();

        envMap.put("PORT", port);
        envMap.put("MEMORY_LIMIT", memoryLimit);
        envMap.put("CF_INSTANCE_INDEX", cfInstanceIndex);
        envMap.put("CF_INSTANCE_ADDR", cfInstanceAddress );

        return envMap;

    }
}
