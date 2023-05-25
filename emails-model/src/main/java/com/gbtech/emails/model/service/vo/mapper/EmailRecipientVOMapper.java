package com.gbtech.emails.model.service.vo.mapper;

import java.util.List;

import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import com.gbtech.emails.model.repository.entity.EmailRecipientBccEntity;
import com.gbtech.emails.model.repository.entity.EmailRecipientCcEntity;
import com.gbtech.emails.model.repository.entity.EmailRecipientEntity;
import com.gbtech.emails.model.repository.entity.EmailRecipientToEntity;
import com.gbtech.emails.model.service.vo.EmailRecipientVO;

/**
 * Mapper for {@link EmailRecipientVO}
 */
@Mapper
public interface EmailRecipientVOMapper {

	EmailRecipientVOMapper MAPPER = Mappers.getMapper(EmailRecipientVOMapper.class);

	@Mapping(source = "address", target = "email")
	EmailRecipientVO toVO(EmailRecipientEntity source);

	List<EmailRecipientVO> toVOList(List<EmailRecipientEntity> sourceList);

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "email", ignore = true)
	@Mapping(source = "email", target = "address")
	EmailRecipientToEntity toRecipientToEntity(EmailRecipientVO source);

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "email", ignore = true)
	@Mapping(source = "email", target = "address")
	EmailRecipientCcEntity toRecipientCcEntity(EmailRecipientVO source);

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "email", ignore = true)
	@Mapping(source = "email", target = "address")
	EmailRecipientBccEntity toRecipientBccEntity(EmailRecipientVO source);

	@Named("toRecipientEntityList")
	@InheritConfiguration
	List<EmailRecipientToEntity> toRecipientToEntityList(List<EmailRecipientVO> sourceList);

	@Named("ccRecipientEntityList")
	@InheritConfiguration
	List<EmailRecipientCcEntity> toRecipientCcEntityList(List<EmailRecipientVO> sourceList);

	@Named("bccRecipientEntityList")
	@InheritConfiguration
	List<EmailRecipientBccEntity> toRecipientBccEntityList(List<EmailRecipientVO> sourceList);

}
