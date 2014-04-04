package com.dosolves.gym.app.ads;

import java.util.ArrayList;
import java.util.List;

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
		Account[] accounts = getAllGoogleAccounts();
		if(accounts.length > 0){
			return generatePayloadForAccount(accounts[0]);
		}
		return null;
	}

	private String generatePayloadForAccount(Account account) {
		return account.name+"¤"+StringUtils.reverse("noitacilppamygsevlosod");
	}

	private Account[] getAllGoogleAccounts() {
		return accountManager.getAccountsByType(GOOGLE_ACCOUNT_TYPE_STRING);
	}

	@Override
	public String[] generateAllPossiblePayloads() {
		List<String> payloads = new ArrayList<String>();
		Account[] accounts = getAllGoogleAccounts();
		for(Account currentAccount:accounts){
			payloads.add(generatePayloadForAccount(currentAccount));
		}
		return toStringArray(payloads);
	}

	private String[] toStringArray(List<String> payloads) {
		return payloads.toArray(new String[0]);
	}

}
