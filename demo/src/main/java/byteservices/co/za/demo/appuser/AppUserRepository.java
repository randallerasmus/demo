package byteservices.co.za.demo.appuser;

// this is a way to have a query against our database

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface AppUserRepository {
    Optional<AppUser> findByEmail(String email);
}
