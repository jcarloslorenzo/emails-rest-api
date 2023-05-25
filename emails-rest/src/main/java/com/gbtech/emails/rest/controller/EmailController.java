/*
 *
 */
package com.gbtech.emails.rest.controller;

import java.math.BigInteger;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.gbtech.emails.model.service.EmailService;
import com.gbtech.emails.model.service.exceptions.ActionNotAllowedException;
import com.gbtech.emails.model.service.exceptions.DataNotFoundException;
import com.gbtech.emails.model.service.exceptions.StateErrorException;
import com.gbtech.emails.rest.controller.bean.Email;
import com.gbtech.emails.rest.controller.bean.EmailSearchFilter;
import com.gbtech.emails.rest.controller.bean.NotSuccessfulActionResponse;
import com.gbtech.emails.rest.controller.bean.mapper.EmailFilterMapper;
import com.gbtech.emails.rest.controller.bean.mapper.EmailMapper;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

/**
 * The Class EmailController.
 */
@RestController
@RequestMapping("v1/emails")
@OpenAPIDefinition(info = @Info(title = "Emails API", version = "v${app.version}"))
@CrossOrigin(origins = { "*" },
	methods = { RequestMethod.GET, RequestMethod.PUT, RequestMethod.POST, RequestMethod.PATCH, RequestMethod.OPTIONS })
public class EmailController {

	/** The email service. */
	@Autowired
	private EmailService emailService;

	/**
	 * Search for emails that meet the requirements defined by the filter.
	 *
	 * @param filter The search filter
	 * @return The emails found that meet the search criteria.
	 */
	@Operation(operationId = "searchEmails", summary = "Search emails",
		description = "Search for emails that meet the requirements defined by the filter.")

	@io.swagger.v3.oas.annotations.parameters.RequestBody(
		description = "Search filters.<br>Supported values for the 'state' attribute:<br>1 = Sent<br>2 = Draft<br>3 = Deleted<br>4 = Spam",
		content = @Content(schema = @Schema(implementation = EmailSearchFilter.class)))

	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Search successful.",
				content = @Content(mediaType = "application/json",
					array = @ArraySchema(minItems = 1, schema = @Schema(implementation = Email.class)))),

			@ApiResponse(responseCode = "204", description = "No results found.",
				content = @Content(mediaType = "application/json",
					array = @ArraySchema(maxItems = 0, minItems = 0, schema = @Schema(implementation = Void.class)))),

			@ApiResponse(responseCode = "400", description = "Wrong or illegal filter content.",
				content = @Content(mediaType = "application/json",
					array = @ArraySchema(maxItems = 1, minItems = 0,
						schema = @Schema(implementation = NotSuccessfulActionResponse.class))))
	})
//
	@PostMapping(path = "/search")
	public @ResponseBody List<Email> searchEmails(@RequestBody final EmailSearchFilter filter) {
		return EmailMapper.MAPPER.fromVOList(this.emailService.searchEmails(EmailFilterMapper.MAPPER.toVO(filter)));
	}

	/**
	 * Create and store new emails with the information received in the call to the endpoint.
	 *
	 * @param emails Emails to be created
	 * @return Emails after creation.
	 * @throws ActionNotAllowedException
	 */

	@Operation(operationId = "createEmails", summary = "Create new emails",
		description = "Create new emails with the information received.")

	@io.swagger.v3.oas.annotations.parameters.RequestBody(
		description = "Emails to create.",
		content = @Content(array = @ArraySchema(minItems = 1, schema = @Schema(implementation = Email.class))))

	@ApiResponses({
			@ApiResponse(responseCode = "201", description = "Emails created successfully.",
				content = @Content(mediaType = "application/json",
					array = @ArraySchema(minItems = 1, schema = @Schema(implementation = Email.class)))),

			@ApiResponse(responseCode = "400", description = "Wrong or illegal email content.",
				content = @Content(mediaType = "application/json",
					schema = @Schema(implementation = NotSuccessfulActionResponse.class)))
	})

	@PostMapping(path = "/")
	public @ResponseBody List<Email> addEmails(@RequestBody final List<Email> emails)
			throws ActionNotAllowedException {
		return EmailMapper.MAPPER.fromVOList(this.emailService.saveAll(EmailMapper.MAPPER.toVOList(emails)));
	}

	/**
	 * Delete emails.
	 *
	 * @param ids the email ids
	 * @throws DataNotFoundException
	 * @throws ActionNotAllowedException
	 */

	@Operation(operationId = "deleteEmails", summary = "Delete emails",
		description = "Delete the received list of emails.",
		parameters = {
				@Parameter(name = "ids", description = "The ids to be delete", allowEmptyValue = false)
		})
	@ApiResponses({
			@ApiResponse(responseCode = "201", description = "Emails successfully deleted.",
				content = @Content(schema = @Schema(implementation = Void.class))),

			@ApiResponse(responseCode = "400", description = "Email cannot be deleted.",
				content = @Content(mediaType = "application/json",
					schema = @Schema(implementation = NotSuccessfulActionResponse.class))),

			@ApiResponse(responseCode = "404", description = "Email not found.",
				content = @Content(mediaType = "application/json",
					schema = @Schema(implementation = NotSuccessfulActionResponse.class)))

	})
	@DeleteMapping(path = "/{ids}")
	public void deleteEmails(@PathVariable final List<BigInteger> ids)
			throws DataNotFoundException, ActionNotAllowedException {

		this.emailService.delete(ids);
	}

	/**
	 * Gets the email.
	 *
	 * @param id the email id
	 * @return the email
	 * @throws DataNotFoundException
	 */
	@Operation(operationId = "getEmail", summary = "Get email by id",
		description = "Retrieves an email by id.",
		parameters = {
				@Parameter(name = "id", description = "The email id", allowEmptyValue = false)
		})
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Email found.",
				content = @Content(mediaType = "application/json",
					schema = @Schema(implementation = Email.class))),

			@ApiResponse(responseCode = "404", description = "Email not found.",
				content = @Content(mediaType = "application/json",
					schema = @Schema(implementation = NotSuccessfulActionResponse.class)))
	})
	@GetMapping(path = "/{id}")
	public @ResponseBody Email getEmail(@PathVariable final BigInteger id) throws DataNotFoundException {

		final Email result = EmailMapper.MAPPER.fromVO(this.emailService.getById(id));

		if (result == null) {
			throw new DataNotFoundException(String.format("No email found with id %s", id));
		}
		return result;
	}

	/**
	 * Updates the received email.
	 *
	 * @param email the email to update
	 * @return the updated email
	 * @throws StateErrorException
	 * @throws DataNotFoundException
	 * @throws ActionNotAllowedException
	 */
	@Operation(operationId = "updateEmail", summary = "Updates an email.",
		description = "Updates the received email.<br>Only existing emails on state 2 (Draft) could be updated",
		parameters = {
				@Parameter(name = "id", description = "The email id", allowEmptyValue = false)
		})
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Email updated successfully.",
				content = @Content(mediaType = "application/json",
					schema = @Schema(implementation = Email.class))),

			@ApiResponse(responseCode = "400", description = "Wrong or illegal email content.",
				content = @Content(mediaType = "application/json",
					schema = @Schema(implementation = NotSuccessfulActionResponse.class))),

			@ApiResponse(responseCode = "404", description = "Email not found.",
				content = @Content(mediaType = "application/json",
					schema = @Schema(implementation = NotSuccessfulActionResponse.class))),

			@ApiResponse(responseCode = "409", description = "Incorrect email state.",
				content = @Content(mediaType = "application/json",
					schema = @Schema(implementation = NotSuccessfulActionResponse.class)))
	})
	@PatchMapping(path = "/{id}")
	public @ResponseBody Email updateEmail(@PathVariable final BigInteger id, @RequestBody final Email email)
			throws DataNotFoundException, StateErrorException, ActionNotAllowedException {
		return EmailMapper.MAPPER.fromVO(this.emailService.update(id, EmailMapper.MAPPER.toVO(email)));
	}

	/**
	 * Test.
	 *
	 * @return true, if successful
	 */
	@Operation(operationId = "test", summary = "Method for test purpouses",
		description = "For API availability testing purposes only.<br>The method always returns true.")
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "OK") })
	@GetMapping(path = "/test")
	public boolean test() {
		return true;
	}

}
