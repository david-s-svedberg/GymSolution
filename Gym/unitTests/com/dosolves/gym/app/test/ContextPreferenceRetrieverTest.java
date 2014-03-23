package com.dosolves.gym.app.test;

import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import android.content.Context;

import com.dosolves.gym.app.ContextPreferenceRetriever;

public class ContextPreferenceRetrieverTest {

	@Mock
	Context contextMock;
	
	ContextPreferenceRetriever sut;
	
	
	@Before
	public void setUp(){
		MockitoAnnotations.initMocks(this);
		
		sut = new ContextPreferenceRetriever(contextMock);
	}
	
	@Test
	public void gets_preference_from_context() {
		sut.getPreferences();
		verify(contextMock).getSharedPreferences("com.dosolves.gym",Context.MODE_PRIVATE);
	}

}
