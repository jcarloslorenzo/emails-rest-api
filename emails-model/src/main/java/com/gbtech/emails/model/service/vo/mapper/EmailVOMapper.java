package com.gbtech.emails.model.service.vo.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

import com.gbtech.emails.model.repository.entity.EmailEntity;
import com.gbtech.emails.model.service.vo.EmailVO;
import com.gbtech.emails.queues.service.bean.EmailQueueBean;

/** Mapper for {@link EmailVO} */
@Mapper(uses = EmailRecipientVOMapper.class)
public interface EmailVOMapper {

	EmailVOMapper MAPPER = Mappers.getMapper(EmailVOMapper.class);

	EmailVO toVO(EmailEntity source);

	@Mapping(target = "creationDate", ignore = true)
	@Mapping(target = "modificationDate", ignore = true)
	@Mapping(source = "to", target = "to", qualifiedByName = "toRecipientEntityList")
	@Mapping(source = "cc", target = "cc", qualifiedByName = "ccRecipientEntityList")
	@Mapping(source = "bcc", target = "bcc", qualifiedByName = "bccRecipientEntityList")
	EmailEntity toEntity(EmailVO source);

	@Mapping(target = "creationDate", ignore = true)
	@Mapping(target = "modificationDate", ignore = true)
	@Mapping(target = "to", source = "to", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	@Mapping(target = "cc", source = "cc", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	@Mapping(target = "bcc", source = "bcc", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	void update(@MappingTarget EmailEntity target, EmailVO source);

	List<EmailVO> toVOList(List<EmailEntity> sourceList);

	List<EmailEntity> toEntityList(List<EmailVO> sourceList);

	EmailQueueBean toQueueBean(EmailVO email);

}
