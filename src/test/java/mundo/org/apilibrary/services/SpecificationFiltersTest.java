package mundo.org.apilibrary.services;

import jakarta.persistence.criteria.*;
import mundo.org.apilibrary.filters.SpecificationFilters;
import mundo.org.apilibrary.interfaces.SpecificationFilter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SpecificationFilterTest {
    private SpecificationFilter<Object> specificationFilter;
    @Mock
    private Root<Object> root;
    @Mock
    private CriteriaQuery<?> query;
    @Mock
    private CriteriaBuilder cb;
    @Mock
    private Path<String> path;
    @Mock
    private Predicate predicate;

    @BeforeEach
    void setUp() {
        specificationFilter = new SpecificationFilters<>();
    }

    @Test
    void buildSpecification_ShouldAddLikePredicates_WhenFiltersMatchAllowedTerms() {
        Map<String, String> filters = Map.of("title", "Clean Code");
        List<String> allowedTerms = List.of("title", "author");

        when(root.<String>get(anyString())).thenReturn(path);
        when(cb.lower(any())).thenReturn(path);

        when(cb.like(any(), eq("%clean code%"))).thenReturn(predicate);
        when(cb.and(any(Predicate[].class))).thenReturn(predicate);

        Specification<Object> spec = specificationFilter.buildSpecification(filters, allowedTerms);
        Predicate resultPredicate = spec.toPredicate(root, query, cb);

        assertNotNull(resultPredicate);
        verify(root, times(1)).get("title");
        verify(cb, times(1)).like(any(), eq("%clean code%"));
    }

    @Test
    void buildSpecification_ShouldIgnoreFilters_WhenNotInAllowedTerms() {
        Map<String, String> filters = Map.of("email", "test@test.com");
        List<String> allowedTerms = List.of("title", "author");

        when(cb.and(any(Predicate[].class))).thenReturn(predicate);

        Specification<Object> spec = specificationFilter.buildSpecification(filters, allowedTerms);
        Predicate resultPredicate = spec.toPredicate(root, query, cb);

        assertNotNull(resultPredicate);

        verify(root, never()).get(anyString());
        verify(cb, never()).like(any(), anyString());
    }
}
