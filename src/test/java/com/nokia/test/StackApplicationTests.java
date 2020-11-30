package com.nokia.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nokia.test.model.StackApiRequest;
import com.nokia.test.repo.StackRepo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
class StackApplicationTests {

    Logger log = LoggerFactory.getLogger(StackApplicationTests.class);

    @Value("${stack.max.capacity}")
    private long stackCapacity;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private StackRepo stackRepo;

    @AfterEach
    public void after() {
        log.info("Deleting all data from database");
        stackRepo.deleteAll();
    }

    @BeforeEach
    public void before() throws Exception {
        StackApiRequest stackApiRequest = new StackApiRequest();
        stackApiRequest.setData("1");
        this.mockMvc.perform(post("/push").content(asJsonString(stackApiRequest)).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void shouldReturnStatusOk() throws Exception {
        StackApiRequest stackApiRequest = new StackApiRequest();
        stackApiRequest.setData("Hello World");
        this.mockMvc.perform(post("/push").content(asJsonString(stackApiRequest)).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void shouldReturnDataFromDb() throws Exception {
        this.mockMvc.perform(get("/pop"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("1")));
    }


    @Test
    public void shouldThrowStackFullException() throws Exception {
        StackApiRequest stackApiRequest = new StackApiRequest();
        for (int i = 0; i < stackCapacity; i++) {
            stackApiRequest.setData(String.valueOf(i));
            this.mockMvc.perform(post("/push").content(asJsonString(stackApiRequest)).contentType(MediaType.APPLICATION_JSON_VALUE));
        }
        this.mockMvc.perform(post("/push").content(asJsonString(stackApiRequest)).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().is5xxServerError())
                .andExpect(content().string(containsString("Stack Is Full")));
    }

    @Test
    public void shouldThrowStackEmptyException() throws Exception {
        for (int i = 0; i < stackCapacity; i++) {
            this.mockMvc.perform(get("/pop"));
        }
        this.mockMvc.perform(get("/pop"))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(content().string(containsString("Stack Is Empty")));
    }

    public String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
