package by.teachmeskills.springbootproject.repositories;

import by.teachmeskills.springbootproject.entities.User;

public interface UserRepository extends BaseRepository<User> {
    User findById(int id);

    User findByEmailAndPassword(String email, String password);
}