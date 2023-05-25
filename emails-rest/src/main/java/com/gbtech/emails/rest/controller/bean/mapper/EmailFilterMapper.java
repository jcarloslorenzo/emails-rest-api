package com.gbtech.emails.rest.controller.bean.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.gbtech.emails.model.service.vo.EmailFilterVO;
import com.gbtech.emails.rest.controller.bean.EmailSearchFilter;

/**
 * The {@link EmailSearchFilter} Mapper
 */
@Mapper
public interface EmailFilterMapper {

	EmailFilterMapper MAPPER = Mappers.getMapper(EmailFilterMapper.class);

	EmailFilterVO toVO(EmailSearchFilter source);
}
