package mundo.org.apilibrary.DTO.thesis;

import java.util.UUID;

public record ThesisDTO(
        UUID id,
        String title,
        String author,
        String authorTwo,
        String authorThree,
        String university,
        String thesisAdvisor,
        String bachelorDegree
) {
}
