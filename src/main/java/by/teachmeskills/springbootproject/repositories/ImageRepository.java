package by.teachmeskills.springbootproject.repositories;

import by.teachmeskills.springbootproject.entities.Image;

import java.util.List;

public interface ImageRepository extends BaseRepository<Image> {
    Image findById(int id);

    Image findByCategoryId(int id);

    List<Image> findByProductId(int id);
}