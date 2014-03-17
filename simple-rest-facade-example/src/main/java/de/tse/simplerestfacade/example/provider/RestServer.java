package de.tse.simplerestfacade.example.provider;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.sun.jersey.api.container.httpserver.HttpServerFactory;
import com.sun.jersey.api.core.PackagesResourceConfig;
import com.sun.jersey.api.core.ResourceConfig;
import com.sun.net.httpserver.HttpServer;

public class RestServer {

	private final HttpServer server;
	private final ExecutorService executorService;
	
	public RestServer(final String url) throws IllegalArgumentException, IOException {
		final ResourceConfig rc = new PackagesResourceConfig("de.tse.simplerestfacade.example.provider.services");
		server = HttpServerFactory.create(url, rc);
		
		executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
		server.setExecutor(executorService);
	}
	
	public void start() {
		server.start();
	}
	public void stop() {
		executorService.shutdown();
		server.stop(0);
	}
}
