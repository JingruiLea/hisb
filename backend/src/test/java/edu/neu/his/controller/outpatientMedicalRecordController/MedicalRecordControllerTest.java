package edu.neu.his.controller.outpatientMedicalRecordController;

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

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class MedicalRecordControllerTest {
    private Logger logger = LoggerFactory.getLogger(MedicalRecordControllerTest.class);

    @Autowired
    protected MockMvc mockMvc;

    @Test
    public void getPatientList()throws  Exception{
        logger.info("MockMvcResultMatchers.status().isOk()", MockMvcResultMatchers.status().isOk());
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.
            post("/medicalRecord/getPatientList")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(" {\n" +
                            "      \"_uid\":1" +
                            "\n" +
                            "    }")

                    .accept(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andDo(MockMvcResultHandlers.print())
            .andReturn();
    }

    @Test
    public void registrationInfo() throws Exception{
        logger.info("MockMvcResultMatchers.status().isOk()", MockMvcResultMatchers.status().isOk());
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.
                post("/medicalRecord/registrationInfo")
                .contentType(MediaType.APPLICATION_JSON)
                .content(" {\n" +
                        "      \"medical_certificate_number\" : \"371625223284621134\",\n" +
                        "      \"type\":\"id\"\n" +
                        "    }")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void createMedicalRecord() throws Exception{
        logger.info("MockMvcResultMatchers.status().isOk()", MockMvcResultMatchers.status().isOk());
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.
                post("/medicalRecord/getMedicalRecord")
                .contentType(MediaType.APPLICATION_JSON)
                .content(" {\n" +
                        "      \"medical_record_id\" : 1\n" +
                        "    }")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void recordHistory() throws Exception{
        logger.info("MockMvcResultMatchers.status().isOk()", MockMvcResultMatchers.status().isOk());
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.
                post("/medicalRecord/allHistoryMedicalRecord")
                .contentType(MediaType.APPLICATION_JSON)
                .content(" {\n" +
                        "      \"type\":\"id\",\n" +
                        "      \"medical_certificate_number\":\"371625223284621134\"\n" +
                        "    }")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void updateMedicalRecord() throws Exception{
        logger.info("MockMvcResultMatchers.status().isOk()", MockMvcResultMatchers.status().isOk());
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.
                post("/medicalRecord/updateMedicalRecord")
                .contentType(MediaType.APPLICATION_JSON)
                .content(" {\n" +
                        "      \"medical_record_id\" : 1,\n" +
                        "      \"chief_complaint\": \"gaiguo\",\n" +
                        "      \"current_medical_history\": \"ssssssss\",\n" +
                        "      \"current_treatment_situation\": \"cy\",\n" +
                        "      \"past_history\": \"wu\",\n" +
                        "      \"allergy_history\": \"wu\",\n" +
                        "      \"physical_examination\": \"yyyy\",\n" +
                        "      \"create_time\": \"2019-06-13 14:26:07\"\n" +
                        "    }")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void saveMedicalRecord() throws Exception{
        logger.info("MockMvcResultMatchers.status().isOk()", MockMvcResultMatchers.status().isOk());
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.
                post("/medicalRecord/saveMedicalRecord")
                .contentType(MediaType.APPLICATION_JSON)
                .content(" {\n" +
                        "      \"medical_record_id\" : 1,\n" +
                        "      \"chief_complaint\": \"gaiguo\",\n" +
                        "      \"current_medical_history\": \"ssssssss\",\n" +
                        "      \"current_treatment_situation\": \"cy\",\n" +
                        "      \"past_history\": \"wu\",\n" +
                        "      \"allergy_history\": \"wu\",\n" +
                        "      \"physical_examination\": \"yyyy\",\n" +
                        "      \"create_time\": \"2019-06-13 14:26:07\"\n" +
                        "    }")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }


}
