package by.teachmeskills.springbootproject.repositories;

import by.teachmeskills.springbootproject.entities.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Transactional
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findById(int id);

    User findByEmailAndPassword(String email, String password);
}