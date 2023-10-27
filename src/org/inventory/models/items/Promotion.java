package org.inventory.models.items;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Promotion(@JsonProperty("promotion") String promotion, @JsonProperty("discount") float discount) {
}
