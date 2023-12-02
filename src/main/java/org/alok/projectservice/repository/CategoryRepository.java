package org.alok.projectservice.repository;

import org.alok.projectservice.entity.Category;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository  extends CrudRepository<Category, String> {
}
