package pl.klgsolutions.klgtask.person;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PersonRepository extends JpaRepository<Person, Long> {
    boolean existsByNameIgnoreCase(String name);

    Optional<Person> findByNameIgnoreCase(String name);
}
