package mundo.org.apilibrary.interfaces;

import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Map;

public interface SpecificationFilter<T> {
    Specification<T> buildSpecification(Map<String, String> filters, List<String> allowedTerms);
}
