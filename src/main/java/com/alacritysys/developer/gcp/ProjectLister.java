package com.alacritysys.developer.gcp;

import static com.google.api.client.googleapis.javanet.GoogleNetHttpTransport.newTrustedTransport;
import static com.google.api.client.json.gson.GsonFactory.getDefaultInstance;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.google.api.services.iam.v1.Iam;
import com.google.api.services.iam.v1.model.ListServiceAccountsResponse;
import com.google.api.services.iam.v1.model.ServiceAccount;
import com.google.api.services.iam.v1.model.ServiceAccountKey;
import com.google.auth.Credentials;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.resourcemanager.v3.Folder;
import com.google.cloud.resourcemanager.v3.FoldersClient;
import com.google.cloud.resourcemanager.v3.Project;
import com.google.cloud.resourcemanager.v3.ProjectsClient;
import com.google.cloud.resourcemanager.v3.ProjectsSettings;

public class ProjectLister {

	public Iterable<Project> listProjects(GoogleCredentials credentials, String orgId) throws IOException {

		ProjectsSettings projectsSettings = ProjectsSettings.newBuilder().setCredentialsProvider(() -> credentials)
				.build();

		List<Project> projects = new ArrayList<>();	
		String orgParent = "organizations/" + orgId;
		try (ProjectsClient projectsClient = ProjectsClient.create(projectsSettings)) {
			projectsClient.listProjects(orgParent).iterateAll().forEach(projects::add);
		}
		
		try (FoldersClient foldersClient = FoldersClient.create()) {
			Iterable<Folder> allFolders = foldersClient.listFolders(orgParent).iterateAll();
			for (Folder folder : allFolders) {
				try (ProjectsClient projectsClient = ProjectsClient.create(projectsSettings)) {
					projectsClient.listProjects(folder.getName()).iterateAll().forEach(projects::add);
				}
			}
		}
		return projects;
	}

	public List<ServiceAccount> listServiceAccounts(Credentials credential, String projectId)
			throws IOException, GeneralSecurityException {

		Iam service = getIam(credential);
		ListServiceAccountsResponse response = service.projects().serviceAccounts().list(projectId).execute();
		List<ServiceAccount> serviceAccounts = response.getAccounts();

		if (serviceAccounts != null) {
			return serviceAccounts;
		}
		return Collections.emptyList();
	}

	public List<ServiceAccountKey> listServiceAccountsKeys(Credentials credential, String projectId)
			throws IOException, GeneralSecurityException {

		List<ServiceAccountKey> keys = new ArrayList<>();
		List<ServiceAccount> serviceAccounts = listServiceAccounts(credential, projectId);
		for (ServiceAccount serviceAccount : serviceAccounts) {
			String email = serviceAccount.getEmail();
			List<ServiceAccountKey> list = getIam(credential).projects().serviceAccounts().keys()
					.list("projects/-/serviceAccounts/" + email).execute().getKeys();
			if (list != null && !list.isEmpty()) {
				keys.addAll(list);
			}
		}
		return keys;
	}

	private Iam getIam(Credentials credential) throws GeneralSecurityException, IOException {
		return new Iam.Builder(newTrustedTransport(), getDefaultInstance(), new HttpCredentialsAdapter(credential))
				.setApplicationName("service-accounts").build();
	}
}
