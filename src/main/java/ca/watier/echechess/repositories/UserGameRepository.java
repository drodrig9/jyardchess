package ca.watier.echechess.repositories;

import ca.watier.echechess.models.UserGame;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

@Repository("userGameRepository")
public interface UserGameRepository extends JpaRepository<UserGame, Serializable> {
}
