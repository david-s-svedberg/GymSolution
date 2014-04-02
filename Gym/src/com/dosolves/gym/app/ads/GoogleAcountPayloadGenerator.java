package com.dosolves.gym.app.ads;

import com.dosolves.gym.utils.StringUtils;

import android.accounts.Account;
import android.accounts.AccountManager;

public class GoogleAcountPayloadGenerator implements
		UserSpecificPayloadGenerator {

	private static final String GOOGLE_ACCOUNT_TYPE_STRING = "com.google";
	private AccountManager accountManager;

	public GoogleAcountPayloadGenerator(AccountManager accountManager) {
		this.accountManager = accountManager;		
	}

	@Override
	public String generateUserSpecificPayload() {
		Account[] accounts = accountManager.getAccountsByType(GOOGLE_ACCOUNT_TYPE_STRING);
		if(accounts.length > 0){
			return accounts[0].name+"¤"+StringUtils.reverse("noitacilppamygsevlosod");
		}
		return null;
	}

}
