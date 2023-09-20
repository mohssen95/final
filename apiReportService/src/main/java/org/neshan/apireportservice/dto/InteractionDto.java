package org.neshan.apireportservice.dto;

import lombok.Getter;
import lombok.Setter;
import org.neshan.apireportservice.entity.model.enums.InteractionType;

@Getter
@Setter

public class InteractionDto {
    private Long userId;
    private Long reportId;
    private InteractionType interactionType;
}
