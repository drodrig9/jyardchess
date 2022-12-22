package ca.watier.echechess.repositories;

import ca.watier.echechess.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

@Repository("jpaUserRepository")
public interface JPAUserRepository extends JpaRepository<User, Serializable> {
}
