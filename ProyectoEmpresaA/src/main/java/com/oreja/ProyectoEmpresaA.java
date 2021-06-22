package com.oreja;

import javax.net.ssl.SSLException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import reactor.netty.http.client.HttpClient;

@SpringBootApplication
public class ProyectoEmpresaA {

	public static void main(String[] args) {
		SpringApplication.run(ProyectoEmpresaA.class, args);
	}
	
	@Bean
	public WebClient webClient(@Value("${Api.path}")String urlBase) throws SSLException { //Para pruebas con certificados autofirmados -> Con certificados reales, se le pasa al builder la direccion https y ya
	    var sslContext = SslContextBuilder
		    .forClient()
		    	.trustManager(InsecureTrustManagerFactory.INSTANCE)
		    .build();
	    HttpClient client = HttpClient.create().secure(spec -> spec.sslContext(sslContext));
	    var httpConnector = new ReactorClientHttpConnector(client);
	    return WebClient.builder()
		    .clientConnector(httpConnector)
		    .baseUrl(urlBase)
		    .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
		    .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
		    .build();
	}

}
