package autogara.services.rest;

import autogara.domain.Excursie;
import autogara.persistence.RepoExcursie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/autogara/excursii")
public class ExcursiiController {

    private static final String template = "Destinatie: %s";

    @Autowired
    private RepoExcursie repository;

    @RequestMapping("/greeting")
    public String greeting(@RequestParam(value = "obiectiv", defaultValue = "Luna") String obiectiv){
        return String.format(template, obiectiv);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ArrayList<Excursie> getAll(){
        return  repository.findAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> findById(@PathVariable String id){
        Excursie excursie = repository.findOne(id);
        if(excursie == null){
            return new ResponseEntity<String>("User not found", HttpStatus.NOT_FOUND);
        }
        else{
            return new ResponseEntity<Excursie>(excursie, HttpStatus.OK);
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    public Excursie create(@RequestBody Excursie excursie) {
        repository.save(excursie);
        return excursie;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Excursie update(@RequestBody Excursie excursie) {
        System.out.println("Update excursie ...");
        repository.update(excursie.getId(), excursie);
        return excursie;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable String id){
        System.out.println("Delete excursie ... " + id);
        try{
            repository.delete(id);
            return new ResponseEntity<Excursie>(HttpStatus.OK);
        }catch (Exception ex){
            System.out.println("Delete Exception");
            return new ResponseEntity<String>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping("/{id}/name")
    public String name(@PathVariable String id){
        Excursie result = repository.findOne(id);
        System.out.println("Result ..." + result);
        return result.getObiectiv() + " " + result.getObiectiv();
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String excursieError(Exception e){
        return e.getMessage();
    }
}
