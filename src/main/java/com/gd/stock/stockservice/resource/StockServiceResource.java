package com.gd.stock.stockservice.resource;

import com.gd.stock.stockservice.model.Quote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import yahoofinance.YahooFinance;
import yahoofinance.quotes.stock.StockQuote;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/rest/stock/")
public class StockServiceResource {

    @Autowired
    private RestTemplate template;

    @GetMapping("{username}")
    List<StockQuote> getStockQuote(@PathVariable("username") final String username){

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
        ResponseEntity<List<String>> response
                = template.exchange("http://db-service/rest/db/" + username, HttpMethod.GET, entity, new ParameterizedTypeReference<List<String>>() {});
        return response.getBody()
                .stream()
                .map(this::getStock)
                .collect(Collectors.toList());
    }

    private StockQuote getStock(String quote) {
        try {
            return YahooFinance.get(quote).getQuote();
        } catch (IOException e) {
            e.printStackTrace();
            return new StockQuote(quote);
        }
    }
}
