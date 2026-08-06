package mundo.org.apilibrary.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Role {
    ROLE_ADMIN,
    ROLE_LIBRARIAN,
    ROLE_PROFESSOR,
    ROLE_STUDENT,
    ROLE_ADMINISTRATIVE;

    @JsonCreator
    public static Role fromString(String value) {
        if (value == null || value.isBlank()) return null;

        String normalized = value.trim().toUpperCase();
        if (!normalized.startsWith("ROLE_")) normalized = "ROLE_" + normalized;
        return Role.valueOf(normalized);
    }
}