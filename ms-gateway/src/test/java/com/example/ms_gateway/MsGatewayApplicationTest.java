package com.example.ms_gateway;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;

@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    properties = {
        "eureka.client.enabled=false",
        "spring.cloud.discovery.enabled=false",
        "server.port=0"
    }
)
class MsGatewayApplicationTest {

    @Autowired
    private ApplicationContext context;

    @Autowired
    private Environment environment;

    @Test
    void contextLoads() {
        assertThat(context).isNotNull();
    }

    @Test
    void gatewayRoutesAreConfigured() {
        assertThat(environment.getProperty("spring.cloud.gateway.server.webmvc.routes[0].id")).isEqualTo("ms-auth");
        assertThat(environment.getProperty("spring.cloud.gateway.server.webmvc.routes[9].id")).isEqualTo("ms-devolucion");
    }
}
