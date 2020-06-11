package rest.client;

import autogara.domain.Excursie;
import autogara.services.rest.ServiceException;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.concurrent.Callable;

public class ExcursiiClient {
    public static final String URL = "http://localhost:8080/autogara/excursii";

    private RestTemplate restTemplate = new RestTemplate();

    private <T> T execute(Callable<T> callable){
        try{
            return callable.call();
        }catch(ResourceAccessException | HttpClientErrorException e){
            throw new ServiceException(e);
        }catch(Exception e){
            throw new ServiceException(e);
        }
    }

    public Excursie[] getAll(){
        return execute(() -> restTemplate.getForObject(URL, Excursie[].class));
    }

    public Excursie findById(String id){
        return execute(() -> restTemplate.getForObject(String.format("%s/%s", URL, id), Excursie.class));
    }

    public Excursie create(Excursie excursie) {
        System.out.println("Save");
        return execute(() -> restTemplate.postForObject(URL, excursie, Excursie.class));
    }

    public void update(Excursie excursie) {
        System.out.println("Update");
        execute(() -> {
            restTemplate.put(String.format("%s/%s", URL, excursie.getId()), excursie);
            return null;
        });
    }

    public void delete(String id) {
        System.out.println("Delete");
        execute(() -> {
            restTemplate.delete(String.format("%s/%s", URL, id));
            return null;
        });
    }
}
