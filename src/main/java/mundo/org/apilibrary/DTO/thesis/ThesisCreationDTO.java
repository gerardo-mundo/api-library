package mundo.org.apilibrary.DTO.thesis;

import jakarta.validation.constraints.NotBlank;

public record ThesisCreationDTO(
        @NotBlank String title,
        @NotBlank String author,
        String authorTwo,
        String authorThree,
        @NotBlank String university,
        @NotBlank String thesisAdvisor,
        @NotBlank String bachelorDegree
) {
}
