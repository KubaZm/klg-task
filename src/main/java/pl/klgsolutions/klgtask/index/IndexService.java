package pl.klgsolutions.klgtask.index;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.klgsolutions.klgtask.index.data.IndexData;
import pl.klgsolutions.klgtask.objectforrent.ObjectForRent;
import pl.klgsolutions.klgtask.objectforrent.ObjectForRentService;
import pl.klgsolutions.klgtask.person.Person;
import pl.klgsolutions.klgtask.person.PersonService;

@Service
public class IndexService {

    @Autowired
    ObjectForRentService objectService;

    @Autowired
    PersonService personService;

    public IndexData getData() {
        return IndexData.builder()
                .objects(objectService.getAllObjects().stream().map(ObjectForRent::getName).toList())
                .people(personService.getAllPeople().stream().map(Person::getName).toList())
                .build();
    }
}
