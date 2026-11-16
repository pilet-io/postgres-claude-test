package com.example.person;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class PersonControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    void createReadUpdateDelete() throws Exception {
        mvc.perform(post("/api/persons").contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":\"p1\",\"name\":\"Alice\",\"age\":30}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Alice"));

        mvc.perform(post("/api/persons").contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":\"p1\",\"name\":\"Dup\",\"age\":1}"))
                .andExpect(status().isConflict());

        mvc.perform(get("/api/persons/p1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.age").value(30));

        mvc.perform(put("/api/persons/p1").contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Alice B\",\"age\":31}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Alice B"));

        mvc.perform(get("/api/persons")).andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));

        mvc.perform(delete("/api/persons/p1")).andExpect(status().isNoContent());
        mvc.perform(get("/api/persons/p1")).andExpect(status().isNotFound());
    }
}
