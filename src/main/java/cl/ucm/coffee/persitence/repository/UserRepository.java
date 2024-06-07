package cl.ucm.coffee.persitence.repository;

import cl.ucm.coffee.persitence.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserEntity, String> {
}
