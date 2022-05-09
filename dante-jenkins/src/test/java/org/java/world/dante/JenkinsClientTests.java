package org.java.world.dante;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

import org.junit.Test;

import com.offbytwo.jenkins.JenkinsServer;
import com.offbytwo.jenkins.client.JenkinsHttpClient;
import com.offbytwo.jenkins.model.Job;

/**
 * 参考：https://github.com/jenkinsci/java-client-api
 * 
 * @author dante
 *
 */
public class JenkinsClientTests {

	@Test
	public void getJob() {
		try {
			JenkinsServer server = new JenkinsServer(new URI("http://localhost:8000"), "dante", "iamdante");
			Map<String, Job> jobs = server.getJobs();
			jobs.forEach((k, v) -> {
				System.out.println(k + " - " + v.getUrl());
			});
			server.close();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	@Test
	public void buildJob() {
		JenkinsHttpClient client = null;
		try {
			client = new JenkinsHttpClient(new URI("http://localhost:8000"), "dante", "iamdante");
			Job job = new Job("x-credentials", "http://localhost:8000/view/CaaS/job/x-credentials/");
			job.setClient(client);
			job.build(true);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			client.close();
		}
	}
}
