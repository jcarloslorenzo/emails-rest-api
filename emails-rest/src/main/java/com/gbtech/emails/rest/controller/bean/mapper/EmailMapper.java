package com.gbtech.emails.rest.controller.bean.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.gbtech.emails.model.service.vo.EmailVO;
import com.gbtech.emails.rest.controller.bean.Email;

/**
 * The {@link Email} Mapper.
 */
@Mapper(uses = EmailRecipientMapper.class)
public interface EmailMapper {

	EmailMapper MAPPER = Mappers.getMapper(EmailMapper.class);

	Email fromVO(EmailVO source);

	EmailVO toVO(Email source);

	List<Email> fromVOList(List<EmailVO> sourceList);

	List<EmailVO> toVOList(List<Email> sourceList);

}
