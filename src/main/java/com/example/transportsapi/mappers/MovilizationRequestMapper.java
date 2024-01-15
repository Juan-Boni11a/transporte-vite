package com.example.transportsapi.mappers;

import com.example.transportsapi.models.MovilizationRequestModel;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface MovilizationRequestMapper {
    void updateMrPartial(@MappingTarget MovilizationRequestModel movilizationRequest,
                            MovilizationRequestModel movilizationRequestR);
}