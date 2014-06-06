package com.dosolves.gym.ads.test;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

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
import com.dosolves.gym.ads.UserSpecificPayloadValidator;
import com.dosolves.gym.app.ads.AdsRemovalBuyerAdapter;
import com.dosolves.gym.app.ads.RouterActivity.RouteReason;
import com.dosolves.gym.app.ads.RouterActivityCreatedListener;
import com.dosolves.gym.app.ads.RouterActivityStarter;
import com.dosolves.gym.app.ads.UserSpecificPayloadGenerator;
import com.dosolves.gym.inappbilling.IabException;
import com.dosolves.gym.inappbilling.IabHelper;
import com.dosolves.gym.inappbilling.IabHelper.OnIabPurchaseFinishedListener;
import com.dosolves.gym.inappbilling.IabHelper.OnIabSetupFinishedListener;
import com.dosolves.gym.inappbilling.IabResult;
import com.dosolves.gym.inappbilling.Inventory;
import com.dosolves.gym.inappbilling.Purchase;

public class AdsRemovalBuyerAdapterTest {

	private static final String USER_SPECIFIC_PAYLOAD = "userSpecificPayload";

	IabResult currentResult;
	
	AdsRemovalBuyer sut;
	RouterActivityCreatedListener sutAsRouterActivityCreatedListener;
	OnIabPurchaseFinishedListener sutAsIabPurchaseFinishedListener;

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
	@Mock
	UserSpecificPayloadValidator userSpecificPayloadValidatorMock;
	@Mock
	Purchase purchaseMock;
	@Mock
	Inventory inventoryMock;
		
	@Before
	public void setUp(){
		MockitoAnnotations.initMocks(this);		
	}

	private void setupSut() {
		AdsRemovalBuyerAdapter sutImpl = new AdsRemovalBuyerAdapter(iabHelperMock, 
																	routerActivityStarterMock, 
																	userSpecificPayloadGeneratorMock,
																	adsRemovalPurchasedListenerMock,
																	userSpecificPayloadValidatorMock);
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
		verify(routerActivityStarterMock).startRouterActivity(RouteReason.FOR_IN_APP_BILLING);
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
	public void ignores_concurrent_calls_fromRouterCreated(){
		setCurrentResultSuccess();
		stupSetupService();
		setupSut();
		
		sutAsRouterActivityCreatedListener.onRouterActivityCreated(activityMock);
		sutAsRouterActivityCreatedListener.onRouterActivityCreated(activityMock);
		
		verify(iabHelperMock, times(1)).launchPurchaseFlow(eq(activityMock), anyString(), anyInt(), Mockito.any(OnIabPurchaseFinishedListener.class), anyString());
	}
	
	@Test
	public void can_start_purchaseflow_after_finished_previous_one(){
		setCurrentResultSuccess();
		stupSetupService();
		setupSut();
		
		sutAsRouterActivityCreatedListener.onRouterActivityCreated(activityMock);
		sutAsIabPurchaseFinishedListener.onIabPurchaseFinished(currentResult, purchaseMock);
		sutAsRouterActivityCreatedListener.onRouterActivityCreated(activityMock);
		
		verify(iabHelperMock, times(2)).launchPurchaseFlow(eq(activityMock), anyString(), anyInt(), Mockito.any(OnIabPurchaseFinishedListener.class), anyString());
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
		
		when(purchaseMock.getDeveloperPayload()).thenReturn(USER_SPECIFIC_PAYLOAD);
		when(userSpecificPayloadValidatorMock.payloadChecksOut(anyString())).thenReturn(true);
		sutAsRouterActivityCreatedListener.onRouterActivityCreated(activityMock);
		sutAsIabPurchaseFinishedListener.onIabPurchaseFinished(currentResult, purchaseMock);
		verify(adsRemovalPurchasedListenerMock).onAdsRemovalPurchased();
	}
	
	@Test
	public void doesnt_notifie_listener_if_payload_doesnt_check_out(){
		setCurrentResultSuccess();
		stupSetupService();
		setupSut();
		
		when(purchaseMock.getDeveloperPayload()).thenReturn(USER_SPECIFIC_PAYLOAD);
		when(userSpecificPayloadValidatorMock.payloadChecksOut(anyString())).thenReturn(false);
		sutAsRouterActivityCreatedListener.onRouterActivityCreated(activityMock);
		sutAsIabPurchaseFinishedListener.onIabPurchaseFinished(currentResult, purchaseMock);
		
		Mockito.verifyNoMoreInteractions(adsRemovalPurchasedListenerMock);
	}
	
	@Test
	public void checks_user_inventory_if_response_is_item_already_owned() throws IabException{
		setCurrentResultAlreadyOwned();
		stupSetupService();
		setupSut();
		
		when(iabHelperMock.queryInventory(false, null)).thenReturn(inventoryMock);
		when(inventoryMock.hasPurchase(anyString())).thenReturn(false);
		when(purchaseMock.getDeveloperPayload()).thenReturn(USER_SPECIFIC_PAYLOAD);
		when(userSpecificPayloadValidatorMock.payloadChecksOut(anyString())).thenReturn(true);
		
		sutAsRouterActivityCreatedListener.onRouterActivityCreated(activityMock);
		sutAsIabPurchaseFinishedListener.onIabPurchaseFinished(currentResult, purchaseMock);
		
		verify(iabHelperMock).queryInventory(false, null);
	}
	
	@Test
	public void verifies_inventory_purchase_if_response_is_item_already_owned() throws IabException{
		setCurrentResultAlreadyOwned();
		stupSetupService();
		setupSut();
		
		when(iabHelperMock.queryInventory(false, null)).thenReturn(inventoryMock);
		when(inventoryMock.hasPurchase(anyString())).thenReturn(true);
		when(inventoryMock.getPurchase(anyString())).thenReturn(purchaseMock);
		when(purchaseMock.getDeveloperPayload()).thenReturn(USER_SPECIFIC_PAYLOAD);
		
		sutAsRouterActivityCreatedListener.onRouterActivityCreated(activityMock);
		sutAsIabPurchaseFinishedListener.onIabPurchaseFinished(currentResult, purchaseMock);
		
		verify(userSpecificPayloadValidatorMock).payloadChecksOut(USER_SPECIFIC_PAYLOAD);
	}
	
	@Test
	public void notifies_listener_if_everything_checks_outon_item_already_owned() throws IabException{
		setCurrentResultAlreadyOwned();
		stupSetupService();
		setupSut();
		
		when(iabHelperMock.queryInventory(false, null)).thenReturn(inventoryMock);
		when(inventoryMock.hasPurchase(anyString())).thenReturn(true);
		when(inventoryMock.getPurchase(anyString())).thenReturn(purchaseMock);
		when(purchaseMock.getDeveloperPayload()).thenReturn(USER_SPECIFIC_PAYLOAD);
		when(userSpecificPayloadValidatorMock.payloadChecksOut(USER_SPECIFIC_PAYLOAD)).thenReturn(true);
		
		sutAsRouterActivityCreatedListener.onRouterActivityCreated(activityMock);
		sutAsIabPurchaseFinishedListener.onIabPurchaseFinished(currentResult, purchaseMock);
		
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
	
	private void setCurrentResultAlreadyOwned() {
		currentResult = new IabResult(IabHelper.BILLING_RESPONSE_RESULT_ITEM_ALREADY_OWNED, null);
	}
	
	private void setCurrentResultSuccess() {
		currentResult = new IabResult(IabHelper.BILLING_RESPONSE_RESULT_OK, null);
	}
	
}
