package mundo.org.apilibrary.services;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;

import mundo.org.apilibrary.DTO.publications.PublicationCreationDTO;
import mundo.org.apilibrary.DTO.publications.PublicationDTO;
import mundo.org.apilibrary.classes.Publication;
import mundo.org.apilibrary.enums.PublicationType;
import mundo.org.apilibrary.mapper.PublicationMapper;
import mundo.org.apilibrary.repository.PublicationRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PublicationServiceTest {
    @Mock
    private PublicationRepository publicationRepository;
    @Mock
    private PublicationMapper publicationMapper;

    @InjectMocks
    private PublicationService publicationService;

    private UUID publicationId;
    private String issn;
    private Publication publicationMock;
    private PublicationDTO publicationDTO;
    private PublicationCreationDTO publicationCreationDTO;


    @BeforeEach
    void setup() {
        publicationId = UUID.randomUUID();
        publicationMock = mock(Publication.class);
        issn = "issn_test";

        publicationDTO = new PublicationDTO(
                publicationId,
                null,
                null,
                null,
                null,
                null,
                issn,
                null,
                PublicationType.MAGAZINE,
                null,
                null,
                null
        );

        publicationCreationDTO = new PublicationCreationDTO(
                null,
                null,
                null,
                null,
                null,
                issn,
                null,
                PublicationType.MAGAZINE,
                null,
                null
        );
    }

    @Test
    void findAllPublications_shouldReturnListOfDto_whenSuccess() {
        List<Publication> publications = List.of(publicationMock);
        List<PublicationDTO> publicationDTOs = List.of(publicationDTO);

        when(publicationRepository.findAll()).thenReturn(publications);
        when(publicationMapper.toDTOList(List.of(publicationMock))).thenReturn(publicationDTOs);

        List<PublicationDTO> result = publicationService.findAllPublications();

        assertNotNull(result);
        assertEquals(publicationDTOs, result);
        verify(publicationRepository, times(1)).findAll();
    }

    @Test
    void findAllPublications_shouldReturnException_whenListIsEmpty() {
        List<Publication> publications = List.of();
        when(publicationRepository.findAll()).thenReturn(publications);

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> publicationService.findAllPublications());

        assertEquals("Publications list were not found", exception.getMessage());
        verify(publicationRepository, times(1)).findAll();
    }

    //TODO: await for the refactor of findByPublisher to implement the respective test

    @Test
    void getPublicationByIssn_shouldReturnPublicationDTO_whenSuccess() {
        when(publicationMapper.toDTO(publicationMock)).thenReturn(publicationDTO);
        when(publicationRepository.findPublicationByIssn(issn)).thenReturn(Optional.of(publicationMock));

        PublicationDTO result = publicationService.getPublicationByIssn(issn);

        assertNotNull(result);
        assertEquals(publicationDTO, result);
        verify(publicationRepository, times(1)).findPublicationByIssn(issn);
    }

    @Test
    void getPublicationByIssn_shouldReturnException_whenPublicationNotFound() {
        when(publicationRepository.findPublicationByIssn(issn)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> publicationService.getPublicationByIssn(issn));

        assertEquals("Publication with ISSN " + issn + " not found", exception.getMessage());
        verify(publicationRepository, times(1)).findPublicationByIssn(issn);
    }

    @Test
    void createPublication_shouldReturnPublicationDTO_whenSuccess() {
        when(publicationRepository.existsByIssn(issn)).thenReturn(false);
        when(publicationMapper.toEntity(publicationCreationDTO)).thenReturn(publicationMock);
        when(publicationRepository.save(publicationMock)).thenReturn(publicationMock);
        when(publicationMapper.toDTO(publicationMock)).thenReturn(publicationDTO);

        PublicationDTO result = publicationService.createPublication(publicationCreationDTO);

        assertNotNull(result);
        assertEquals(publicationDTO, result);
        verify(publicationRepository, times(1)).save(publicationMock);
    }

    @Test
    void createPublication_shouldReturnException_whenPublicationIsNull() {
        when(publicationRepository.existsByIssn(issn)).thenReturn(true);

        EntityExistsException exception = assertThrows(EntityExistsException.class,
                () -> publicationService.createPublication(publicationCreationDTO));

        assertEquals("Publication with ISSN: " + issn + " already exists", exception.getMessage());
        verify(publicationRepository, never()).save(publicationMock);
    }

    @Test
    void updatePublication_shouldReturnPublicationDTO_whenSuccess() {
        when(publicationRepository.findById(publicationId)).thenReturn(Optional.of(publicationMock));
        when(publicationRepository.existsByIssnAndIdNot(issn, publicationId)).thenReturn(false);
        when(publicationMapper.toDTO(publicationMock)).thenReturn(publicationDTO);
        publicationMapper.updateEntityFromDto(publicationCreationDTO, publicationMock);
        when(publicationRepository.saveAndFlush(publicationMock)).thenReturn(publicationMock);

        PublicationDTO result = publicationService.updatePublication(publicationCreationDTO, publicationId);

        assertNotNull(result);
        assertEquals(publicationDTO, result);
        verify(publicationRepository, times(1)).saveAndFlush(publicationMock);
    }

    @Test
    void updatePublication_shouldReturnException_whenPublicationNotExists() {
        when(publicationRepository.findById(publicationId)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> publicationService.updatePublication(publicationCreationDTO, publicationId));

        assertEquals("Publication doesn't exists", exception.getMessage());
        verify(publicationRepository, times(1)).findById(publicationId);
        verify(publicationRepository, never()).saveAndFlush(publicationMock);
    }

    @Test
    void updatePublication_shouldReturnException_whenIssnExists() {
        when(publicationRepository.findById(publicationId)).thenReturn(Optional.of(publicationMock));
        when(publicationRepository.existsByIssnAndIdNot(issn, publicationId)).thenReturn(true);

        EntityExistsException exception = assertThrows(EntityExistsException.class,
                () -> publicationService.updatePublication(publicationCreationDTO, publicationId));

        assertEquals("Publication with ISSN: " + issn + " already exists", exception.getMessage());
        verify(publicationRepository, times(1)).findById(publicationId);
        verify(publicationRepository, never()).saveAndFlush(publicationMock);
    }

    @Test
    void deletePublication_shouldDelete_whenSuccess() {
        when(publicationRepository.existsById(publicationId)).thenReturn(true);
        publicationService.deletePublication(publicationId);

        verify(publicationRepository, times(1)).deleteById(publicationId);
    }

    @Test
    void deletePublication_shouldThrowException_whenPublicationNotFound() {
        when(publicationRepository.existsById(publicationId)).thenReturn(false);

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> publicationService.deletePublication(publicationId));

        assertEquals("Publication with ID: " + publicationId + " not found", exception.getMessage());
        verify(publicationRepository, times(1)).existsById(publicationId);
        verify(publicationRepository, never()).deleteById(publicationId);
    }
}
