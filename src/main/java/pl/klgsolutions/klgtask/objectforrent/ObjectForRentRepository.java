package pl.klgsolutions.klgtask.objectforrent;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ObjectForRentRepository extends JpaRepository<ObjectForRent, Long> {
    boolean existsByNameIgnoreCase(String name);

    Optional<ObjectForRent> findByNameIgnoreCase(String name);
}
