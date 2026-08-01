package mundo.org.apilibrary.services;

import jakarta.persistence.EntityNotFoundException;

import mundo.org.apilibrary.DTO.thesis.ThesisCreationDTO;
import mundo.org.apilibrary.DTO.thesis.ThesisDTO;
import mundo.org.apilibrary.entities.Thesis;
import mundo.org.apilibrary.mapper.ThesisMapper;
import mundo.org.apilibrary.repository.ThesisRepository;

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
public class ThesisServiceTest {
    @Mock
    private ThesisRepository thesisRepository;
    @Mock
    private ThesisMapper thesisMapper;

    @InjectMocks
    private ThesisService thesisService;

    private UUID thesisId;
    private Thesis thesis;
    private ThesisCreationDTO thesisCreationDTO;
    private ThesisDTO thesisDTO;

    @BeforeEach
    public void setUp() {
        thesis = new Thesis();
        thesisId = UUID.randomUUID();

        thesisDTO = new ThesisDTO(
                thesisId,
                null,
                null,
                null,
                null,
                null,
                null,
                null
        );

        thesisCreationDTO = new ThesisCreationDTO(
                "Title",
                "Author",
                null,
                null,
                "University",
                "Advisor",
                "Degree"
        );
    }

    @Test
    void findAll_shouldReturnListDto_whenSuccess() {
        when(thesisRepository.findAll()).thenReturn(List.of(thesis));
        when(thesisMapper.toListDto(List.of(thesis))).thenReturn(List.of(thesisDTO));
        List<ThesisDTO> result = thesisService.findAll();

        assertNotNull(result);
        assertEquals(List.of(thesisDTO), result);
        verify(thesisRepository, times(1)).findAll();
    }

    @Test
    void findAll_shouldReturnException_whenListIsEmpty() {
        when(thesisRepository.findAll()).thenReturn(List.of());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> thesisService.findAll());

        assertEquals("Thesis list were not found", exception.getMessage());
        verify(thesisRepository, times(1)).findAll();
    }

    @Test
    void findById_shouldReturnDto_whenSuccess() {
        when(thesisRepository.findById(thesisId)).thenReturn(Optional.of(thesis));
        when(thesisMapper.toDto(thesis)).thenReturn(thesisDTO);

        ThesisDTO result = thesisService.findById(thesisId);

        assertNotNull(result);
        assertEquals(thesisDTO, result);
        verify(thesisRepository, times(1)).findById(thesisId);
    }

    @Test
    void create_shouldReturnDto_whenSuccess() {
        when(thesisMapper.toEntity(thesisCreationDTO)).thenReturn(thesis);
        when(thesisRepository.save(thesis)).thenReturn(thesis);
        when(thesisMapper.toDto(thesis)).thenReturn(thesisDTO);

        ThesisDTO result = thesisService.createThesis(thesisCreationDTO);

        assertNotNull(result);
        assertEquals(thesisDTO, result);
        verify(thesisRepository, times(1)).save(thesis);
    }

    @Test
    void updateThesis_shouldReturnDto_whenSuccess() {
        when(thesisRepository.findById(thesisId)).thenReturn(Optional.of(thesis));
        when(thesisMapper.toEntity(thesisCreationDTO)).thenReturn(thesis);
        when(thesisRepository.save(thesis)).thenReturn(thesis);
        when(thesisMapper.toDto(thesis)).thenReturn(thesisDTO);

        ThesisDTO result = thesisService.updateThesis(thesisId, thesisCreationDTO);

        assertNotNull(result);
        assertEquals(thesisDTO, result);
        verify(thesisRepository, times(1)).findById(thesisId);
    }

    @Test
    void updateThesis_shouldReturnException_whenEntityNotFound() {
        when(thesisRepository.findById(thesisId)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> thesisService.updateThesis(thesisId, thesisCreationDTO));

        assertEquals("Thesis with ID: " + thesisId + " not found", exception.getMessage());
        verify(thesisRepository, times(1)).findById(thesisId);
    }

    @Test
    void deleteThesis_shouldDelete_whenSuccess() {
        when(thesisRepository.findById(thesisId)).thenReturn(Optional.of(thesis));
        thesisService.deleteThesis(thesisId);

        verify(thesisRepository, times(1)).deleteById(thesisId);
    }

    @Test
    void deleteThesis_shouldReturnException_whenEntityNotFound() {
        when(thesisRepository.findById(thesisId)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> thesisService.deleteThesis(thesisId));

        assertEquals("Thesis with ID: " + thesisId + " not found", exception.getMessage());
        verify(thesisRepository, times(1)).findById(thesisId);
        verify(thesisRepository, never()).deleteById(thesisId);
    }
}
