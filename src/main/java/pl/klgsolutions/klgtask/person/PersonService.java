package pl.klgsolutions.klgtask.person;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PersonService {

    @Autowired
    PersonRepository personRepository;

    public boolean checkIfPersonExistsByName(String name) {
        return personRepository.existsByNameIgnoreCase(name);
    }

    public Optional<Person> getPersonByName(String name) {
        return personRepository.findByNameIgnoreCase(name);
    }
}
