package com.alacritysys.developer.gcp;

import static com.alacritysys.developer.gcp.Autheticator.getCredentials;
import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import com.google.api.services.iam.v1.model.ServiceAccount;
import com.google.api.services.iam.v1.model.ServiceAccountKey;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.resourcemanager.v3.Project;

import lombok.extern.slf4j.Slf4j;

@Slf4j
class ProjectListerTest {

	private ProjectLister lister = new ProjectLister();

	@Test
	@Disabled("For Debugging purpose")
	void testProjectListing() throws IOException, GeneralSecurityException {
		GoogleCredentials credentials = getCredentials();
		Iterable<Project> projects = lister.listProjects(credentials, "959854286594");
		assertThat(projects).isNotNull().isNotEmpty();

		log.info("{},{} ", "Project", "ServiceAccount");
		System.out.println("Project,ServiceAccount,ManagedBy");
		for (Project project : projects) {
			List<ServiceAccount> listServiceAccounts = lister.listServiceAccounts(credentials, project.getName());
			for (ServiceAccount serviceAccount : listServiceAccounts) {
				System.out.println(project.getProjectId() + "," + serviceAccount.getEmail() + ","
						+ serviceAccount.get("displayName"));
			}
		}

	}

	@Test
	@Disabled("For Debugging purpose")
	void testServiceKeysListing() throws IOException, GeneralSecurityException {
		GoogleCredentials credentials = getCredentials();
		Iterable<Project> projects = lister.listProjects(credentials, "959854286594");
		assertThat(projects).isNotNull().isNotEmpty();

		System.out.println("Project,ServiceAccountKey,Type,ValidTill");
		for (Project project : projects) {
			List<ServiceAccountKey> listServiceKeys = lister.listServiceAccountsKeys(credentials, project.getName());
			for (ServiceAccountKey serviceAccountKey : listServiceKeys) {
				System.out.println(project.getProjectId() + "," + serviceAccountKey.getName() + ","
						+ serviceAccountKey.getKeyType() + "," + serviceAccountKey.getValidBeforeTime());
			}
		}

	}

}
