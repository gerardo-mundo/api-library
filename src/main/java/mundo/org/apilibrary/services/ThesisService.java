package mundo.org.apilibrary.services;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

import mundo.org.apilibrary.DTO.thesis.ThesisCreationDTO;
import mundo.org.apilibrary.DTO.thesis.ThesisDTO;
import mundo.org.apilibrary.entities.Thesis;
import mundo.org.apilibrary.mapper.ThesisMapper;
import mundo.org.apilibrary.repository.ThesisRepository;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Limit;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ThesisService {
    private final ThesisRepository thesisRepository;
    private final ThesisMapper thesisMapper;

    public ThesisService(ThesisRepository thesisRepository, ThesisMapper mapper) {
        this.thesisRepository = thesisRepository;
        this.thesisMapper = mapper;
    }

    @Cacheable(value = "thesis", key = "'allThesis'")
    public List<ThesisDTO> findAll() {
        List<Thesis> thesisList = thesisRepository.findAll();

        if (thesisList.isEmpty()) {
            throw new EntityNotFoundException("Thesis list were not found");
        }

        return thesisMapper.toListDto(thesisList);
    }

    @Cacheable(value = "thesis", key = "#id")
    public ThesisDTO findById(UUID id) {
        Thesis thesis = thesisRepository.findById(id).orElse(null);

        return thesisMapper.toDto(thesis);
    }

    public ThesisDTO findByBachelorDegree(String bachelorDegree, Limit limit) {
        Thesis thesis = thesisRepository.findByBachelorDegree(bachelorDegree, limit).orElse(null);

        return thesisMapper.toDto(thesis);
    }

    public List<ThesisDTO> findByThesisAdvisor(String bachelorName, Limit limit) {
        List<Thesis> thesis = thesisRepository.findThesisByThesisAdvisor(bachelorName, limit);

        if (thesis.isEmpty()) {
            throw new EntityNotFoundException("Thesis list were not found");
        }

        return thesisMapper.toListDto(thesis);
    }

    @Transactional
    @CacheEvict(value = "thesis", allEntries = true)
    public ThesisDTO createThesis(ThesisCreationDTO thesisDto) {
        Thesis entity = thesisMapper.toEntity(thesisDto);
        Thesis thesis = thesisRepository.save(entity);

        return thesisMapper.toDto(thesis);
    }

    @Transactional
    @CacheEvict(value = "thesis", allEntries = true)
    public ThesisDTO updateThesis(UUID id, ThesisCreationDTO thesisDto) {
        Thesis thesis = thesisRepository.findById(id).orElse(null);

        if (thesis == null) {
            throw new EntityNotFoundException("Thesis with ID: " + id + " not found");
        }

        Thesis updatedThesis = thesisRepository.save(thesisMapper.toEntity(thesisDto));
        return thesisMapper.toDto(updatedThesis);
    }

    @Transactional
    @CacheEvict(value = "thesis", allEntries = true)
    public void deleteThesis(UUID id) {
        Thesis thesis = thesisRepository.findById(id).orElse(null);

        if (thesis == null) {
            throw new EntityNotFoundException("Thesis with ID: " + id + " not found");
        }

        thesisRepository.deleteById(id);
    }
}
