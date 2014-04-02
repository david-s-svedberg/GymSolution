package com.dosolves.gym.ads.test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;

import android.accounts.Account;
import android.accounts.AccountManager;

import com.dosolves.gym.app.ads.GoogleAcountPayloadGenerator;
import com.dosolves.gym.app.ads.UserSpecificPayloadGenerator;

@RunWith(RobolectricTestRunner.class)
public class UserSpecificPayloadGeneratorTest {

	@Mock
	AccountManager accountManagerMock;
	
	Account accountMock;
	
	
	UserSpecificPayloadGenerator sut;
	
	@Before
	public void setUp() throws Exception{
		MockitoAnnotations.initMocks(this);
		
		accountMock = new Account("asdf@asdf.com", "com.google");
		
		sut = new GoogleAcountPayloadGenerator(accountManagerMock);
	}
	
	@Test
	public void querries_accountmanager_for_google_accounts(){
		when(accountManagerMock.getAccountsByType("com.google")).thenReturn(new Account[]{accountMock});
		
		sut.generateUserSpecificPayload();
		
		verify(accountManagerMock).getAccountsByType("com.google");
	}
	
	@Test
	public void creates_payload_with_account_email_and_static_password(){
		when(accountManagerMock.getAccountsByType("com.google")).thenReturn(new Account[]{accountMock});
		
		String payload = sut.generateUserSpecificPayload();
		assertEquals("asdf@asdf.com¤dosolvesgymapplication", payload);
	}
	
}
