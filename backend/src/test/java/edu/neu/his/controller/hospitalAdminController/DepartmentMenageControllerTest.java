package edu.neu.his.controller.hospitalAdminController;

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
import org.springframework.util.MultiValueMap;

import java.util.HashMap;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class DepartmentMenageControllerTest {

    private Logger logger = LoggerFactory.getLogger(DepartmentMenageControllerTest.class);

    @Autowired
    protected MockMvc mockMvc;

    @Test
    public void departmentFindByName() {

    }

    @Test
    public void listAllDepartment() throws Exception {
        logger.info("MockMvcResultMatchers.status().isOk()", MockMvcResultMatchers.status().isOk());
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.
                post("/departmentManage/findByName")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"神经内科\"}")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("{}"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void updateDepartment() {

    }

    @Test
    public void insertDepartment() {

    }

    @Test
    public void deleteDepartment() {

    }
}