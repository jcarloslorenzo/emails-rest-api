package com.gbtech.emails.rest.controller.bean.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.gbtech.emails.model.service.vo.EmailRecipientVO;
import com.gbtech.emails.rest.controller.bean.Email.Recipient;

/**
 * The {@link Recipient} Mapper.
 */
@Mapper
public interface EmailRecipientMapper {

	EmailRecipientMapper MAPPER = Mappers.getMapper(EmailRecipientMapper.class);

	Recipient fromVO(EmailRecipientVO source);

	EmailRecipientVO toVO(Recipient source);

	List<Recipient> fromVOList(List<EmailRecipientVO> sourceList);

	List<EmailRecipientVO> toVOList(List<Recipient> sourceList);

}
