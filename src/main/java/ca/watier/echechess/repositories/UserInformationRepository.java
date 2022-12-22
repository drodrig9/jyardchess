package ca.watier.echechess.repositories;

import ca.watier.echechess.models.UserInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

@Repository("userInformationRepository")
public interface UserInformationRepository extends JpaRepository<UserInformation, Serializable> {

    public List<UserInformation> findByEmail(String email);

    public UserInformation findByName (String name);
}
