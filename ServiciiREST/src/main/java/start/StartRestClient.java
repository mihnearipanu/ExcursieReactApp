package start;

import autogara.domain.Excursie;
import autogara.services.rest.ServiceException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import rest.client.ExcursiiClient;

import java.time.LocalTime;

public class StartRestClient {
    public final static ExcursiiClient excursiiClient = new ExcursiiClient();

    public static void main(String[] args) {
        RestTemplate restTemplate = new RestTemplate();
        Excursie excursie = new Excursie("10",
                "Marionex",
                "Dedeman",
                LocalTime.of(12, 40),
                123.0,
                21);
        try{
            show(() -> {
                Excursie[] result = excursiiClient.getAll();
                for (Excursie e: result){
                    System.out.println(e.getNumeCompTrans() + " -> " + e.getObiectiv());
                }
            });

            show(() -> {
                Excursie result = excursiiClient.findById("1");
                System.out.println("Am  gasit excursia cu id-ul: " + result.getId());
            });

            show(() -> {
                System.out.println("Adauga excursie: " + excursiiClient.create(excursie));
            });

            show(() -> {
                excursiiClient.update(new Excursie("10",
                        "Stelaria",
                        "Dolinex",
                        LocalTime.of(10, 0),
                        2.5,
                        18));
                System.out.println("Excursie update facut");
            });

            show(() -> {
                excursiiClient.delete("10");
                System.out.println("Excursie stearsa");
            });

        }catch (RestClientException e){
            System.out.println("Exceptie: " + e.getMessage());
        }
    }

    private static void show(Runnable task){
        try{
            task.run();
        } catch (ServiceException e){
            System.out.println("Service exception: " + e.getMessage());
        }
    }
}
