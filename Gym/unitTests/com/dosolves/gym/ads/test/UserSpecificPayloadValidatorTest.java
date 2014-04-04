package com.dosolves.gym.ads.test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.dosolves.gym.ads.UserSpecificPayloadValidator;
import com.dosolves.gym.app.ads.GoogleAccountUserSpecificPayloadValidator;
import com.dosolves.gym.app.ads.UserSpecificPayloadGenerator;

public class UserSpecificPayloadValidatorTest {
	
	private static final String PAYLOAD = "payload";
	
	@Mock
	UserSpecificPayloadGenerator userSpecificPayloadGeneratorMock;
	
	UserSpecificPayloadValidator sut;
	
		
	@Before
	public void setUp(){
		MockitoAnnotations.initMocks(this);
		
		sut = new GoogleAccountUserSpecificPayloadValidator(userSpecificPayloadGeneratorMock);
	}
	
	@Test	
	public void returns_false_if_none_of_the_payloads_matches(){
		String[] possiblePayloads = new String[]{"asdf","hgsdh","hsdgf"};
		when(userSpecificPayloadGeneratorMock.generateAllPossiblePayloads()).thenReturn(possiblePayloads);
		
		assertFalse(sut.payloadChecksOut(PAYLOAD));
		
	}
	
	@Test	
	public void returns_true_if_any_of_the_payloads_matches(){
		String[] possiblePayloads = new String[]{"asdf","hgsdh","hsdgf", PAYLOAD};
		when(userSpecificPayloadGeneratorMock.generateAllPossiblePayloads()).thenReturn(possiblePayloads);
		
		assertTrue(sut.payloadChecksOut(PAYLOAD));
		
	}

}
