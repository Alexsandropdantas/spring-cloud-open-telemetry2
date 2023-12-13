package com.baeldung.opentelemetry.controller;

import com.baeldung.opentelemetry.api.client.PriceClient;
import com.baeldung.opentelemetry.model.Product;
import com.baeldung.opentelemetry.repository.ProductRepository;

import io.opentelemetry.api.trace.Tracer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);

	private final PriceClient priceClient;

	private final ProductRepository productRepository;

	private Tracer tracer;

	@Autowired
	public ProductController(PriceClient priceClient, ProductRepository productRepository, Tracer tracer) {
		this.priceClient = priceClient;
		this.productRepository = productRepository;
		this.tracer = tracer;
	}

	@GetMapping(path = "/product/{id}")
	public Product getProductDetails(@PathVariable("id") long productId) {
		LOGGER.info("Getting Product and Price Details With Product Id {}", productId);
		Product product = productRepository.getProduct(productId);
		product.setPrice(priceClient.getPrice(productId));

		return product;
	}

	@GetMapping(path = "/produto")
	public String ola() {

		return "Ola";
	}

	@GetMapping("/span")
	public String exemploComSpan() {
		// Cria um novo span usando o Tracer do Sleuth
		io.opentelemetry.api.trace.Span span = tracer.spanBuilder("meu-span-no-controller").startSpan();

		System.out.println("-------------------------------------");
		System.out.println("---------- Funciona até aqui --------");
		System.out.println("-------------------------------------");

		try {
			// Lógica do seu controlador aqui

			// Adiciona atributos ao span, se necessário
			span.setAttribute("atributo-chave", "valor-do-atributo");

			// Adiciona eventos ao span, se necessário
			span.addEvent("Evento 1");
			span.addEvent("Evento 2");

			return "Exemplo com span";

		} finally {
			// Encerra o span ao final da execução do controlador
			span.end();
		}
	}

	@GetMapping("/spanx")
    public String exemploComSpanx() {

        // Cria o primeiro span
    	io.opentelemetry.api.trace.Span primeiroSpan = tracer.spanBuilder("primeiro-span").startSpan();

        try {
            // Lógica do seu aplicativo para o primeiro span aqui
        	
            System.out.println("--------------------------------------------------");
            System.out.println("---------- Funciona até aqui primeiro-span -------");
            System.out.println("--------------------------------------------------");
        	
            // Adiciona atributos ou eventos ao primeiro span, se necessário
            primeiroSpan.setAttribute("atributo-chave", "valor-primeiro-span");
            primeiroSpan.addEvent("Evento 1");

            // Cria o segundo span
            io.opentelemetry.api.trace.Span segundoSpan = tracer.spanBuilder("segundo-span").startSpan();

            try {
            	
                // Lógica do seu aplicativo para o segundo span aqui
            	
            	System.out.println("--------------------------------------------------");
            	System.out.println("---------- Funciona até aqui segundo-span --------");
            	System.out.println("--------------------------------------------------");

                // Adiciona atributos ou eventos ao segundo span, se necessário
                segundoSpan.setAttribute("outro-atributo", "valor-segundo-span");
                segundoSpan.addEvent("Evento 2");
            	
                
                io.opentelemetry.api.trace.Span terceiroSpan = tracer.spanBuilder("terceiro-span").startSpan();

                try {
                	// Cria o segundo span

                	// Lógica do seu aplicativo para o terceiro span aqui
                	
                    System.out.println("--------------------------------------------------");
                    System.out.println("---------- Funciona até aqui terceiro-span -------");
                    System.out.println("--------------------------------------------------");
                	
                    
                    // Adiciona atributos ou eventos ao terceiro span, se necessário
                    terceiroSpan.setAttribute("atributo-chave-1", "valor-terceiro-span-1");
                    terceiroSpan.addEvent("Evento 1");
                    
                    terceiroSpan.setAttribute("atributo-chave-2", "valor-terceiro-span-2");
                    terceiroSpan.addEvent("Evento 2");
                    
                    terceiroSpan.setAttribute("atributo-chave-3", "valor-terceiro-span-3");
                    terceiroSpan.addEvent("Evento 3");



	                } finally {
	                    // Encerra o segundo span ao final da execução
	                	terceiroSpan.end();
	                }
                
            } finally {
                // Encerra o segundo span ao final da execução
                segundoSpan.end();
            }

            
        } finally {
            // Encerra o primeiro span ao final da execução
            primeiroSpan.end();
        }

        
        return "Exemplo com spanx";
        
    }

}
