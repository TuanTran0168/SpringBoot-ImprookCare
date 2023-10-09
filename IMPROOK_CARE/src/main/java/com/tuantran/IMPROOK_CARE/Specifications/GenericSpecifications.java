/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuantran.IMPROOK_CARE.Specifications;

import java.util.List;
import java.util.Map;
import org.springframework.data.jpa.domain.Specification;

/**
 *
 * @author Administrator
 */
public class GenericSpecifications {

    public static <T> Specification<T> fieldEquals(String fieldName, Object value) {
        return (root, query, builder) -> builder.equal(root.get(fieldName), value);
    }

    public static <T> Specification<T> fieldContains(String fieldName, String value) {
        return (root, query, builder) -> builder.like(root.get(fieldName), "%" + value + "%");
    }

    public static <T> Specification<T> createSpecification(List<Specification<T>> Specifications) {
        Specification<T> combinedSpec = null;

        if (!Specifications.isEmpty()) {
            for (Specification<T> spec : Specifications) {
                if (combinedSpec == null) {
                    combinedSpec = Specification.where(spec);
                } else {
                    combinedSpec = combinedSpec.and(spec);
                }
            }
        }
        return combinedSpec;
    }
}
