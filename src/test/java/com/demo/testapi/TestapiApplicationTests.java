package com.demo.testapi;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.demo.testapi.bo.BOServiceClass;
import com.demo.testapi.bo.TestBO;
import com.demo.testapi.model.Account;
import com.demo.testapi.model.Password;
import com.demo.testapi.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;

import junit.framework.Assert;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestapiApplicationTests {
	
	private MockMvc mockMvc;
	@Autowired
	private WebApplicationContext context;
	
	ObjectMapper om = new ObjectMapper();
	
	@Mock
	private TestBO testBO;
	
	@Spy
	private TestBO testSpyBO;
	
	@InjectMocks
	private BOServiceClass BOService;
	
	@Before
	public void setUp() {
		mockMvc= MockMvcBuilders.webAppContextSetup(context).build();
	}
	
	//UseMockMVC for testing REST endpoints
	
	@Test
	public void createUserAccountTest() throws Exception {
		Account account = new Account();
		account.setUsername("kevin");
		account.setPassword("abc123");
		String jsonRequest = om.writeValueAsString(account);
		mockMvc.perform(post("/accounts/createAccount").accept(MediaType.APPLICATION_JSON_VALUE).content(jsonRequest).contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
		
		
	}

	@SuppressWarnings("deprecation")
	@Test
	public void getUserPasswordTest() throws Exception {
		User username= new User();
		username.setUsername("kevin");
		String jsonRequest = om.writeValueAsString(username);
		MvcResult result= mockMvc.perform(post("/accounts/getPassword").accept("application/json").content(jsonRequest).contentType("application/json")).andExpect(status().isOk()).andReturn();
		String resultContent= result.getResponse().getContentAsString();
		Password response = om.readValue(resultContent, Password.class);
		Assert.assertEquals(response.getPassword(), "abc123");
	}
	
	//Use Mockito for testing Java Code and Mocking external dependencies e.g. API calls
	
	@Test
	public void adderTest() {
		when(testBO.adder(Mockito.anyInt(), Mockito.anyInt())).thenReturn(5);
		assertEquals(testBO.adder(7,7), 5);

		
	}
	
	@Test
	public void injectedMockTestInitalized() {
		when(testBO.adder(Mockito.anyInt(), Mockito.anyInt())).thenReturn(7);
		assertEquals(BOService.executeInjectedMock(2, 1), 7);
		//mock returns assigned value from when/return statement
		
	}
	
	@Test
	public void injectedMockTestUninitialized() {
		//when(testBO.adder(Mockito.anyInt(), Mockito.anyInt())).thenReturn(7);
		assertEquals(BOService.executeInjectedMock(2, 1), 0);
		//mock needs when/thenReturn statement to return a value otherwise returns 0
		
	}
	
	@Test
	public void injectedSpyTestInitialized() {
		Mockito.doReturn(200).when(testSpyBO).adder(50, 50);
		assertEquals(200, BOService.executeInjectedSpy(50, 50));
		//spy returns assigned value from a doReturn/when statement
	}
	
	@Test
	public void injectedSpyTestUninitialized() {
		assertEquals(BOService.executeInjectedSpy(2, 1), 3);
		//spy will execute original implementation if no assigned value
	}

}
