package org.inventory.models.items;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

public record Promotion(@JsonProperty("promotion") String promotion, @JsonProperty("startDate")LocalDate startDate, @JsonProperty("endDate") LocalDate endDate, @JsonProperty("discount") float discount) {
}
