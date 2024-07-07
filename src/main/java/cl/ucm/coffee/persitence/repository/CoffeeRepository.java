package cl.ucm.coffee.persitence.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.ucm.coffee.persitence.entity.CoffeeEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface CoffeeRepository extends JpaRepository<CoffeeEntity,Integer> {
    public Optional<CoffeeEntity> findByName(String name);

}
