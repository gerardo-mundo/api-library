package mundo.org.apilibrary.services;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;

import mundo.org.apilibrary.DTO.publications.PublicationCreationDTO;
import mundo.org.apilibrary.DTO.publications.PublicationDTO;
import mundo.org.apilibrary.classes.Publication;
import mundo.org.apilibrary.mapper.PublicationMapper;
import mundo.org.apilibrary.repository.PublicationRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class PublicationService {
    private final PublicationRepository publicationRepository;
    private final PublicationMapper publicationMapper;

    public PublicationService(PublicationRepository publicationRepository, PublicationMapper publicationMapper) {
        this.publicationRepository = publicationRepository;
        this.publicationMapper = publicationMapper;
    }

    public List<PublicationDTO> findAllPublications() {
        List<Publication> publicationList = publicationRepository.findAll();

        if (publicationList.isEmpty()) throw new EntityNotFoundException("Publications list were not found");

        return publicationMapper.toDTOList(publicationList);
    }

    public PublicationDTO getPublicationByIssn(String issn) {
        return publicationRepository.findPublicationByIssn(issn)
                .map(publicationMapper::toDTO)
                .orElseThrow(() -> new EntityNotFoundException("Publication with ISSN " + issn + " not found"));
    }

    @Transactional
    public PublicationDTO createPublication(PublicationCreationDTO dto) {
        if (publicationRepository.existsByIssn(dto.issn()))
            throw new EntityExistsException("Publication with ISSN: " + dto.issn() + " already exists");

        return publicationMapper.toDTO(publicationRepository.save(publicationMapper.toEntity(dto)));
    }

    @Transactional
    public PublicationDTO updatePublication(PublicationCreationDTO publicationDTO, UUID id) {
        Publication entity = publicationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Publication doesn't exists"));

        if (publicationRepository.existsByIssnAndIdNot(publicationDTO.issn(), id))
            throw new EntityExistsException("Publication with ISSN: " + publicationDTO.issn() + " already exists");

        publicationMapper.updateEntityFromDto(publicationDTO, entity);
        return publicationMapper.toDTO(publicationRepository.saveAndFlush(entity));
    }

    @Transactional
    public void deletePublication(UUID id) {
        if (publicationRepository.existsById(id))
            throw new EntityNotFoundException("Publication with ID: " + id + " not found");

        publicationRepository.deleteById(id);
    }
}
