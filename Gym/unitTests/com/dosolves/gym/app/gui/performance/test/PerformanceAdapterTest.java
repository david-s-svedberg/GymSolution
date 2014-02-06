package com.dosolves.gym.app.gui.performance.test;

import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.Date;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;

import android.content.Context;

import com.dosolves.gym.app.performance.gui.PerformanceAdapter;
import com.dosolves.gym.domain.performance.Performance;
import com.dosolves.gym.domain.performance.Set;

@RunWith(RobolectricTestRunner.class)
public class PerformanceAdapterTest {

	@Mock
	Context contextMock;
	
	PerformanceAdapter sut;
	
	@Before
	public void setUp() throws Exception{
		MockitoAnnotations.initMocks(this);
		
		sut = new PerformanceAdapter(contextMock);
		
		ArrayList<Performance> performances = createPerformances();
		sut.setPerformances(performances);
	}

	private ArrayList<Performance> createPerformances() {
		ArrayList<Performance> performances = new ArrayList<Performance>();
		ArrayList<Set> sets = new ArrayList<Set>();
		Set set = new Set(12,312,12,55.5,new Date());
		sets.add(set);
		performances.add(new Performance(sets));
		return performances;
	}
	
	@Ignore("Roboelectric can't handle call for inflater")
	@Test
	public void getView_returns_layout(){
		assertNotNull(sut.getView(0, null, null));		
	}
	
}

