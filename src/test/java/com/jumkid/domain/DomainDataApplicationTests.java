package com.jumkid.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jumkid.domain.controller.dto.DomainData;
import com.jumkid.domain.service.DomainDataService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@AutoConfigureMockMvc
public class DomainDataApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private DomainDataService domainDataService;

	private static boolean isSetup = false;

	@Before
	public void setup() {
		if (!isSetup) {
			domainDataService.saveDomainData("dummy industry", "dummy name", "dummy 1");
			domainDataService.saveDomainData("dummy industry", "dummy name", "dummy 2");
			isSetup = true;
		}
	}

	@Test
	@WithMockUser(username="test", password="test", authorities="USER_ROLE")
	public void givenId_shouldGetDomainData() throws Exception{
		mockMvc.perform(get("/domaindata/1")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.industry").value("dummy industry"));
	}

	@Test
	@WithMockUser(username="test", password="test", authorities="USER_ROLE")
	public void shouldGetListOfDomainData() throws Exception {
		mockMvc.perform(get("/domaindata")
				.param("industry", "dummy industry")
				.param("name", "dummy name")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(2)));
	}

	@Test
	@WithMockUser(username="admin", password="admin", authorities="ADMIN_ROLE")
	public void whenGivenIndustryNameValue_ShouldSaveNewDomainData() throws Exception {
		mockMvc.perform(post("/domaindata")
				.param("industry", "dummy industry")
				.param("name", "dummy name")
				.param("value", "dummy 3")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.value").value("dummy 3"));
	}

	@Test
	@WithMockUser(username="admin", password="admin", authorities="ADMIN_ROLE")
	public void whenGivenId_ShouldDeleteDomainData() throws Exception {
		mockMvc.perform(delete("/domaindata/1")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent());
	}

	@Test
	@WithMockUser(username="admin", password="admin", authorities="ADMIN_ROLE")
	public void whenGivenDomainData_ShouldUpdateDomainData() throws Exception {
		DomainData domainData = DomainData.builder()
				.industry("dummy industry")
				.name("dummy name")
				.value("dummy 4")
				.build();
		mockMvc.perform(put("/domaindata/2")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsBytes(domainData)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").isNumber());
	}
}
