package rz.demo.jdbc.repo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import rz.demo.jdbc.repo.greet.GreetService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureMockMvc
class ApplicationTests {

    @Autowired
    private GreetService service;

    @Autowired
    private MockMvc mvc;

    @Test
    void greetAndResponseContainsNameFromDatabase() {
        String response = service.greet("Hello");

        assertThat(response)
                .doesNotContain("Default")
                .contains("Demo");
    }

    @Test
    void getConfigurationValueFromActuator() throws Exception {
        mvc.perform(
                get("/actuator/env/{key}", "app.greet.name")
        )
                .andExpect(jsonPath("$.property.source", containsString("demo-default")))
                .andExpect(jsonPath("$.property.value", is("Demo")));
    }
}
