package pl.klgsolutions.klgtask.objectforrent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ObjectForRentService {

    @Autowired
    private ObjectForRentRepository objectForRentRepository;

    public boolean checkIfObjectExistsByName(String name) {
        return objectForRentRepository.existsByNameIgnoreCase(name);
    }

    public Optional<ObjectForRent> getObjectByName(String name) {
        return objectForRentRepository.findByNameIgnoreCase(name);
    }

    public List<ObjectForRent> getAllObjects() {
        return objectForRentRepository.findAll();
    }
}
