package org.asidorkin.discoveryserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class DiscoveryServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(DiscoveryServerApplication.class, args);
    }

}

//Use Netflix Eureka for registering applications and discovering them for inter-component REST calls.

//Use Netflix Hystrix for protecting inter-component REST calls from the product application.
// The fallback behavior is supposed to result in ‘503 service unavailable’ in case of unavailability of any dependant services.
// Use synthetic delays (sleep time) in the inventory and catalog applications to increase response latency.
// Play with ‘execution.isolation.thread.timeoutInMilliseconds’, ‘coreSize’, ‘circuitBreaker.requestVolumeThreshold’
//   and ‘circuitBreaker.sleepWindowInMilliseconds’ to simulate circuit breaker behavior.

//Use request tracing through the inter-component REST calls and the Zipkin server for monitoring request flow and latency.
