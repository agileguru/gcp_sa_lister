package com.alacritysys.developer.gcp;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.auth.oauth2.GoogleCredentials;

public class Autheticator {

	private static final Logger LOGGER = LoggerFactory.getLogger(Autheticator.class);

	private Autheticator() {

	}

	public static GoogleCredentials getCredentials() throws IOException {

		GoogleCredentials credentials = GoogleCredentials.getApplicationDefault();
		LOGGER.debug("Getting Credentials success {}", credentials.getQuotaProjectId());

		return credentials;
	}
}
