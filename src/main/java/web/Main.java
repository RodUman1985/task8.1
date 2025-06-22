package web;

import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class Main {

    private  static final String API_Url = "http://94.198.50.185:7081/api/users";
    private static String sessionId;
    private  static String part_one;
    private  static String part_two;
    private static String part_three;
   private static String foolCode;

    public static void main(String[] args) {


        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<List> responseEntity = restTemplate.getForEntity(API_Url, List.class);
        sessionId = responseEntity.getHeaders().getFirst("Set-Cookie");
        System.out.println("session ID = " + sessionId);


        HttpHeaders headers = new HttpHeaders();
        headers.set("Cookie", sessionId);
        headers.setContentType(MediaType.APPLICATION_JSON);

        User newUser = new User(3L, "James", "Brown", (byte) 30);
        HttpEntity<User> request = new HttpEntity<>(newUser, headers);
        ResponseEntity<String> createEntity = restTemplate.postForEntity(API_Url, request, String.class);

        if (createEntity.getStatusCode() == HttpStatus.OK) {
            part_one = createEntity.getBody();
            System.out.println("первая часть кода = " + part_one);
        } else {
            System.out.println("error");
        }


        User updateUser = new User(3L, "Thomas", "Shelby", (byte) 30);
        HttpEntity<User> request2 = new HttpEntity<>(updateUser, headers);
        ResponseEntity<String> updateEntity = restTemplate.exchange(API_Url, HttpMethod.PUT, request2, String.class);
        if (updateEntity.getStatusCode() == HttpStatus.OK) {
            part_two = updateEntity.getBody();
            System.out.println("вторая часть кода = " + part_two);
        } else {
            System.out.println("error");
        }

        HttpEntity<String> deleteRequest = new HttpEntity<>(headers);
        ResponseEntity<String> deleteEntity = restTemplate.exchange(API_Url+"/3", HttpMethod.DELETE, deleteRequest, String.class);
        if (deleteEntity.getStatusCode() == HttpStatus.OK) {
             part_three = deleteEntity.getBody();
            System.out.println("третья часть кода = " + part_three);
        } else {
            System.out.println("error");
        }

       foolCode = part_one +part_two +part_three;
        System.out.println("полный код = " + foolCode);
        System.out.println("длинна =  "+foolCode.length());


    }
}