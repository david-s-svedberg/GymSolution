package com.dosolves.gym.ads.test;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import android.app.Activity;

import com.dosolves.gym.ads.AdsRemovalBuyer;
import com.dosolves.gym.ads.AdsRemovalPurchasedListener;
import com.dosolves.gym.app.ads.AdsRemovalBuyerAdapter;
import com.dosolves.gym.app.ads.RouterActivityCreatedListener;
import com.dosolves.gym.app.ads.RouterActivityStarter;
import com.dosolves.gym.app.ads.UserSpecificPayloadGenerator;
import com.dosolves.gym.inappbilling.IabHelper;
import com.dosolves.gym.inappbilling.IabHelper.OnIabPurchaseFinishedListener;
import com.dosolves.gym.inappbilling.IabHelper.OnIabSetupFinishedListener;
import com.dosolves.gym.inappbilling.IabResult;

public class AdsRemovalBuyerAdapterTest {

	private static final String USER_SPECIFIC_PAYLOAD = "userSpecificPayload";
	@Mock
	IabHelper iabHelperMock;
	@Mock
	RouterActivityStarter routerActivityStarterMock;
	@Mock
	Activity activityMock;
	@Mock
	UserSpecificPayloadGenerator userSpecificPayloadGeneratorMock;
	@Mock
	AdsRemovalPurchasedListener adsRemovalPurchasedListenerMock;
	
	IabResult currentResult;
	
	AdsRemovalBuyer sut;
	RouterActivityCreatedListener sutAsRouterActivityCreatedListener;
	OnIabPurchaseFinishedListener sutAsIabPurchaseFinishedListener;
	
		
	@Before
	public void setUp(){
		MockitoAnnotations.initMocks(this);		
	}

	private void setupSut() {
		AdsRemovalBuyerAdapter sutImpl = new AdsRemovalBuyerAdapter(iabHelperMock, 
																	routerActivityStarterMock, 
																	userSpecificPayloadGeneratorMock,
																	adsRemovalPurchasedListenerMock);
		sut = sutImpl;
		sutAsRouterActivityCreatedListener = sutImpl;
		sutAsIabPurchaseFinishedListener = sutImpl;
	}
	
	@Test
	public void calls_startSetup_on_construction(){
		setupSut();
		verify(iabHelperMock).startSetup(Mockito.any(OnIabSetupFinishedListener.class));
	}
	
	@Test
	public void reruns_startSetup_on_buyAdsRemoval_if_original_setup_failed(){
		setCurrentResultFail();
		stupSetupService();
		
		setupSut();
		
		setCurrentResultSuccess();
		
		sut.buyAdsRemoval();
		
		verify(iabHelperMock, times(2)).startSetup(Mockito.any(OnIabSetupFinishedListener.class));
	}
	
	@Test
	public void doesnt_rerun_startSetup_on_buyAdsRemoval_if_original_setup_succeded(){
		setCurrentResultSuccess();
		stupSetupService();
		setupSut();
		
		sut.buyAdsRemoval();
		
		verify(iabHelperMock, times(1)).startSetup(Mockito.any(OnIabSetupFinishedListener.class));
	}
	
	@Test
	public void continues_with_purchase_upon_successful_initialization_after_rerun_of_startSetup_on_buyAdsRemoval_after_original_setup_failed(){
		setCurrentResultFail();
		stupSetupService();
		setupSut();
		
		setCurrentResultSuccess();
		sut.buyAdsRemoval();
		sutAsRouterActivityCreatedListener.onRouterActivityCreated(activityMock);
		
		verify(iabHelperMock, times(2)).startSetup(Mockito.any(OnIabSetupFinishedListener.class));
		verify(iabHelperMock, times(1)).launchPurchaseFlow(Mockito.any(Activity.class), anyString(), anyInt(), Mockito.any(OnIabPurchaseFinishedListener.class), anyString());
	}
	
	@Test
	public void stops_after_failed_rerun_of_startSetup_on_buyAdsRemoval_after_original_setup_failed(){
		setCurrentResultFail();
		stupSetupService();
		setupSut();
		
		sut.buyAdsRemoval();
		
		verify(iabHelperMock, times(2)).startSetup(Mockito.any(OnIabSetupFinishedListener.class));
	}
	
	@Test
	public void starts_routing_activity_when_purchase_is_initiated(){
		setCurrentResultSuccess();
		stupSetupService();
		setupSut();
		
		sut.buyAdsRemoval();
		verify(routerActivityStarterMock).startRouterActivity();
	}
	
	@Test
	public void calls_helper_to_launch_purchase_flow_when_router_activity_is_created(){
		setCurrentResultSuccess();
		stupSetupService();
		setupSut();
		
		sutAsRouterActivityCreatedListener.onRouterActivityCreated(activityMock);
		
		verify(iabHelperMock).launchPurchaseFlow(eq(activityMock), anyString(), anyInt(), Mockito.any(OnIabPurchaseFinishedListener.class), anyString());
	}
	
	@Test
	public void generates_payload_with_UserSpecificPayloadCreator(){
		setCurrentResultSuccess();
		stupSetupService();
		setupSut();
		
		when(userSpecificPayloadGeneratorMock.generateUserSpecificPayload()).thenReturn(USER_SPECIFIC_PAYLOAD);
		
		sutAsRouterActivityCreatedListener.onRouterActivityCreated(activityMock);
		
		verify(iabHelperMock).launchPurchaseFlow(eq(activityMock), anyString(), anyInt(), Mockito.any(OnIabPurchaseFinishedListener.class), eq(USER_SPECIFIC_PAYLOAD));
	}
	
	@Test
	public void notifies_listener_of_successful_app_removal_purchase_if_everything_checks_out(){
		setCurrentResultSuccess();
		stupSetupService();
		setupSut();
		
		sutAsIabPurchaseFinishedListener.onIabPurchaseFinished(currentResult, null);
		verify(adsRemovalPurchasedListenerMock).onAdsRemovalPurchased();
	}
	
	private void stupSetupService() {
		doAnswer(new Answer<IabHelper>(){
			@Override
			public IabHelper answer(InvocationOnMock invocation) throws Throwable {
				OnIabSetupFinishedListener callback = (OnIabSetupFinishedListener) invocation.getArguments()[0];
				callback.onIabSetupFinished(currentResult);
				return null;
			}
			
		}).when(iabHelperMock).startSetup(Mockito.any(OnIabSetupFinishedListener.class));
	}

	private void setCurrentResultFail() {
		currentResult = new IabResult(Integer.MAX_VALUE, null);
	}
	
	private void setCurrentResultSuccess() {
		currentResult = new IabResult(IabHelper.BILLING_RESPONSE_RESULT_OK, null);
	}
	
}
