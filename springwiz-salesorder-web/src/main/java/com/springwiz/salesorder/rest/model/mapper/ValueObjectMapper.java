package com.springwiz.salesorder.rest.model.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

/**
 * The Class ObjectMapper.
 *
 * @author sumit
 */
@Component
public class ValueObjectMapper {
	
	/** The model mapper. */
	private ModelMapper modelMapper = new ModelMapper();

	/**
	 * Instantiates a new object mapper.
	 */
	public ValueObjectMapper() {
		
	}
	
	/**
	 * Map.
	 *
	 * @param <TargetType> the generic type
	 * @param source the source
	 * @param targetType the target type
	 * @return the TargetType Object
	 */
	public <TargetType> TargetType map(Object source, Class<TargetType> targetType) {
		return modelMapper.map(source, targetType);
	}
}
