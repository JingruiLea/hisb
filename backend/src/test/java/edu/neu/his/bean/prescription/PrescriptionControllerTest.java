package edu.neu.his.bean.prescription;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PrescriptionControllerTest {
    private Logger logger = LoggerFactory.getLogger(PrescriptionControllerTest.class);

    @Autowired
    protected MockMvc mockMvc;


    @Test
    public void create() throws  Exception{
        logger.info("MockMvcResultMatchers.status().isOk()", MockMvcResultMatchers.status().isOk());
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.
                post("/prescription/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "      \"type\":0,\n" +
                        "      \"medical_record_id\": 1,\n" +
                        "      \"drug_list\":[\n" +
                        "        {\"id\":9, \"amount\":2,\"note\":\"多吃\"},\n" +
                        "        {\"id\":3,\"amount\":3}\n" +
                        "      ],\n" +
                        "      \"_uid\": 10002\n" +
                        "    }")

                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void addItem() throws  Exception{
        logger.info("MockMvcResultMatchers.status().isOk()", MockMvcResultMatchers.status().isOk());
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.
                post("/prescription/addItem")
                .contentType(MediaType.APPLICATION_JSON)
                .content(" {\n" +
                        "      \"prescription_id\" : 2,\n" +
                        "      \"drug_list\": [\n" +
                        "        {\"id\":3, \"amount\":2,\"note\":\"多吃\"}\n" +
                        "      ],\n" +
                        "      \"_uid\":10004\n" +
                        "    }")

                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void deleteItem() throws  Exception{
        logger.info("MockMvcResultMatchers.status().isOk()", MockMvcResultMatchers.status().isOk());
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.
                post("/prescription/deleteItem")
                .contentType(MediaType.APPLICATION_JSON)
                .content(" {\n" +
                        "      \"prescription_id\" : 1,\n" +
                        "      \"drug_list\" :[\n" +
                        "        {\"id\":1}\n" +
                        "      ],\n" +
                        "      \"_uid\": 10004\n" +
                        "    }")

                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void updateItem()throws  Exception{
        logger.info("MockMvcResultMatchers.status().isOk()", MockMvcResultMatchers.status().isOk());
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.
                post("/prescription/updateItem")
                .contentType(MediaType.APPLICATION_JSON)
                .content("  {\n" +
                        "      \"prescription_id\" : 1,\n" +
                        "      \"drug_list\" :[\n" +
                        "        {\"id\":5, \"amount\":2,\"note\":\"多吃\"}\n" +
                        "      ],\n" +
                        "      \"_uid\": 10004\n" +
                        "    }")

                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void submit() throws  Exception{
        logger.info("MockMvcResultMatchers.status().isOk()", MockMvcResultMatchers.status().isOk());
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.
                post("/prescription/submit")
                .contentType(MediaType.APPLICATION_JSON)
                .content(" {\n" +
                        "      \"type\" : 1,\n" +
                        "      \"_uid\":10004\n" +
                        "    }")

                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }
}