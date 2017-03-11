package com.learnasyncrest.controller;

import com.learnasyncrest.AsyncrestApplication;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.client.AsyncRestTemplate;

import java.util.concurrent.ExecutionException;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by z001qgd on 3/11/17.
 */
@RunWith(SpringRunner.class)
//@ContextConfiguration(classes = AsyncrestApplication.class)
public class HelloControllerTest {


    @Test
    public void hello(){
        TestRestTemplate restTemplate = new TestRestTemplate();
        String url = "http://localhost:8081/hello";
        System.out.println("URL:  "+url);
        // restTemplate.getForEntity(url , String.class);
        String response = restTemplate.getForObject(url,String.class);
        assertEquals("Welcome",response);

    }

    @Test
    public void hello_asynchronous_req(){
        AsyncRestTemplate asyncRestTemplate = new AsyncRestTemplate();
        String url = "http://localhost:8081/hello";

        System.out.println("URL:  "+url);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);
        HttpEntity<String> requestEntity = new HttpEntity<String>("params", headers);
        Class<String> responseType = String.class;
        ListenableFuture<ResponseEntity<String>> future = asyncRestTemplate.exchange(url, HttpMethod.GET, requestEntity, responseType);
        System.out.println("First call done");
        ListenableFuture<ResponseEntity<String>> future1 = asyncRestTemplate.exchange(url, HttpMethod.GET, requestEntity, responseType);
        System.out.println("Second call done");
        ListenableFuture<ResponseEntity<String>> future2 = asyncRestTemplate.exchange(url, HttpMethod.GET, requestEntity, responseType);
        System.out.println("Third call done");

        try {
            //waits for the result
            ResponseEntity<String> entity = future.get();
            ResponseEntity<String> entity1 = future1.get();
            ResponseEntity<String> entity2 = future2.get();
            //prints body source code for the given URL
            System.out.println("First Response  : " +  entity.getBody());
            System.out.println("Second Response  : " +  entity1.getBody());
            System.out.println("Third Response  : " +  entity2.getBody());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


    }

}
