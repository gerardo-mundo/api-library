package mundo.org.apilibrary.filters;

import jakarta.persistence.criteria.Predicate;

import mundo.org.apilibrary.interfaces.SpecificationFilter;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class SpecificationFilters<T> implements SpecificationFilter<T> {

    @Override
    public Specification<T> buildSpecification(Map<String, String> filters, List<String> allowedTerms) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            for (Map.Entry<String, String> entry : filters.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();

                if (allowedTerms.contains(key) && value != null && !value.isEmpty()) {
                    predicates.add(cb.like(cb.lower(root.get(key)), "%" + value.toLowerCase() + "%"));
                }
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
