package cl.ucm.coffee.persitence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.ucm.coffee.persitence.entity.UserRoleEntity;
import cl.ucm.coffee.persitence.entity.UserRoleId;

import org.springframework.stereotype.Repository;

@Repository
public interface RolRepository extends JpaRepository<UserRoleEntity,UserRoleId> {

}
