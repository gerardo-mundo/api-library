package mundo.org.apilibrary.mapper;

import mundo.org.apilibrary.DTO.loans.LoanCreationDTO;
import mundo.org.apilibrary.DTO.loans.LoanDTO;
import mundo.org.apilibrary.DTO.loans.LoanUpdateDTO;
import mundo.org.apilibrary.entities.Loan;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LoanMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "approver", ignore = true)
    @Mapping(target = "borrower", ignore = true)
    @Mapping(target = "borrowDate", ignore = true)
    @Mapping(target = "returnDate", ignore = true)
    @Mapping(target = "borrowedBooks", ignore = true)
    Loan toEntity(LoanCreationDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "borrowDate", ignore = true)
    @Mapping(target = "approver", ignore = true)
    @Mapping(target = "borrower", ignore = true)
    void updateEntity(LoanUpdateDTO dto, @MappingTarget Loan loan);

    LoanDTO toDTO(Loan loan);

    List<LoanDTO> toListDTO(List<Loan> loans);
}
