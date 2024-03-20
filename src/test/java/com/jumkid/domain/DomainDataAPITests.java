package com.jumkid.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jumkid.domain.controller.dto.DomainData;
import com.jumkid.domain.service.DomainDataService;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.security.test.context.support.WithMockUser;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@PropertySource("classpath:application.share.properties")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@EnableTestContainers
class DomainDataAPITests {
	@LocalServerPort
	private int port;

	@Value("${com.jumkid.jwt.test.user-token}")
	private String testUserToken;
	@Value("${com.jumkid.jwt.test.admin-token}")
	private String testAdminToken;

	@Autowired
	private DomainDataService domainDataService;

	@BeforeAll
	void setup() {
		try {
			RestAssured.defaultParser = Parser.JSON;
		} catch (Exception e) {
			fail();
		}

	}

	@Test
	void givenId_shouldGetDomainData() throws Exception{
		RestAssured
				.given()
					.baseUri("http://localhost").port(port)
					.contentType(ContentType.JSON)
				.when()
					.get("/domaindata/1")
				.then()
					.log()
					.all()
					.statusCode(HttpStatus.OK.value())
					.body("industry", equalTo("dummy industry"));
	}

	@Test
	void shouldGetListOfDomainData() throws Exception {
		RestAssured
				.given()
					.baseUri("http://localhost").port(port)
					.headers("Authorization", "Bearer " + testUserToken)
					.contentType(ContentType.JSON)
					.queryParam("industry", "dummy industry")
					.queryParam("name", "dummy name")
				.when()
					.get("/domaindata")
				.then()
					.log()
					.all()
					.statusCode(HttpStatus.OK.value())
					.body("size()", equalTo(2));
	}

	@Test
	void whenGivenIndustryNameValue_ShouldSaveNewDomainData() throws Exception {
		RestAssured
				.given()
					.baseUri("http://localhost").port(port)
					.headers("Authorization", "Bearer " + testAdminToken)
					.contentType(ContentType.JSON)
					.queryParam("industry", "dummy industry")
					.queryParam("name", "dummy name")
					.queryParam("value", "dummy 3")
				.when()
					.post("/domaindata")
				.then()
					.log()
					.all()
					.statusCode(HttpStatus.OK.value())
					.body("value", equalTo("dummy 3"));
	}

	@Test
	@WithMockUser(username="admin", password="admin", authorities="ADMIN_ROLE")
	void whenGivenId_ShouldDeleteDomainData() throws Exception {
		RestAssured
				.given()
					.baseUri("http://localhost").port(port)
					.headers("Authorization", "Bearer " + testAdminToken)
					.contentType(ContentType.JSON)
				.when()
					.delete("/domaindata/1")
				.then()
					.log()
					.all()
					.statusCode(HttpStatus.NO_CONTENT.value());
	}

	@Test
	@WithMockUser(username="admin", password="admin", authorities="ADMIN_ROLE")
	void whenGivenDomainData_ShouldUpdateDomainData() throws Exception {
		DomainData domainData = DomainData.builder()
				.industry("dummy industry")
				.name("dummy name")
				.value("dummy 4")
				.build();

		RestAssured
				.given()
					.baseUri("http://localhost").port(port)
					.headers("Authorization", "Bearer " + testAdminToken)
					.contentType(ContentType.JSON)
					.body(new ObjectMapper().writeValueAsBytes(domainData))
				.when()
					.put("/domaindata/2")
				.then()
					.log()
					.all()
					.statusCode(HttpStatus.ACCEPTED.value())
					.body("value", equalTo(domainData.getValue()));
	}

	@AfterAll
	void cleanUp() {
		//void
	}
}
