package by.teachmeskills.springbootproject.repositories;

import by.teachmeskills.springbootproject.entities.Category;
import by.teachmeskills.springbootproject.entities.Product;
import by.teachmeskills.springbootproject.entities.SearchParams;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class ProductSearchSpecification implements Specification<Product> {
    private final SearchParams searchParams;

    @Override
    public Predicate toPredicate(@NonNull Root<Product> root, @NonNull CriteriaQuery<?> query, @NonNull CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();

        if (Optional.ofNullable(searchParams).isPresent() && Optional.ofNullable(searchParams.getSearchKey()).isPresent() && !searchParams.getSearchKey().isBlank()) {
            predicates.add(criteriaBuilder
                    .or(criteriaBuilder.like(root.get("name"), "%" + searchParams.getSearchKey() + "%"),
                            criteriaBuilder.like(root.get("description"), "%" + searchParams.getSearchKey() + "%")));
        }

        if (Optional.ofNullable(searchParams).isPresent() && Optional.ofNullable(searchParams.getPriceFrom()).isPresent() && searchParams.getPriceFrom() > 0) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("price"), searchParams.getPriceFrom()));
        }

        if (Optional.ofNullable(searchParams).isPresent() && Optional.ofNullable(searchParams.getPriceTo()).isPresent() && searchParams.getPriceTo() > 0) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("price"), searchParams.getPriceTo()));
        }

        if (Optional.ofNullable(searchParams).isPresent() && Optional.ofNullable(searchParams.getCategoryName()).isPresent()
                && !searchParams.getCategoryName().isBlank()) {
            Join<Product, Category> productCategoryJoin = root.join("category");
            predicates.add(criteriaBuilder.and(criteriaBuilder.like(productCategoryJoin.get("name"), "%" + searchParams.getCategoryName() + "%")));
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}