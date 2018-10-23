package com.rigiresearch.examgen;

import com.beust.jcommander.JCommander;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

    @Bean
    public JCommander jCommander(Application application) {
        JCommander jc = JCommander.newBuilder()
                .addObject(application)
                .build();
        jc.setProgramName("java -jar examgen.jar");
        return jc;
    }

}
