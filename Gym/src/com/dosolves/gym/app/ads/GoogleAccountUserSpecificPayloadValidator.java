package com.dosolves.gym.app.ads;

import com.dosolves.gym.ads.UserSpecificPayloadValidator;

public class GoogleAccountUserSpecificPayloadValidator implements UserSpecificPayloadValidator {

	private UserSpecificPayloadGenerator userSpecificPayloadGenerator;

	public GoogleAccountUserSpecificPayloadValidator(UserSpecificPayloadGenerator userSpecificPayloadGenerator) {
		this.userSpecificPayloadGenerator = userSpecificPayloadGenerator;
	}

	@Override
	public boolean payloadChecksOut(String payload) {
		String[] allPossiblePayloads = userSpecificPayloadGenerator.generateAllPossiblePayloads();
		
		for(String currentPayload: allPossiblePayloads){
			if(currentPayload.equals(payload))
				return true;
		}
		
		return false;
	}

}
