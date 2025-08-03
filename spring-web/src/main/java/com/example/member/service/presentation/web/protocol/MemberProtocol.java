package com.example.member.service.presentation.web.protocol;

import com.example.common.data.Pagination;
import com.example.member.service.presentation.web.request.CreateMemberRequest;
import com.example.member.service.presentation.web.request.ModifyMemberEmailRequest;
import com.example.member.service.presentation.web.request.QueryMembersRequest;
import com.example.member.service.presentation.web.response.QueryMemberResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.Positive;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

// We have 3 type annotations in this protocol
// OpenAPI
// Spring MVC
// Jakarta Validation or Spring Validation
public interface MemberProtocol {
    @Operation(
            summary = "Create a new member",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Member created successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid input"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            })
    @PostMapping("/api/v1/members")
    ResponseEntity<Void> createMember(@RequestBody @Validated final CreateMemberRequest request, final HttpServletRequest httpServletRequest);

    @Operation(
            summary = "Get a member by ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Member found"),
                    @ApiResponse(responseCode = "400", description = "Invalid member ID"),
                    @ApiResponse(responseCode = "404", description = "Member not found"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            }
    )
    @GetMapping("/api/v1/members/{id}")
    ResponseEntity<QueryMemberResponse> getMemberById(@Parameter(description = "member ID", required = true) @PathVariable @Positive final Long id);

    @Operation(
            summary = "Get members by various criteria",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Members found"),
                    @ApiResponse(responseCode = "400", description = "Invalid input"),
                    @ApiResponse(responseCode = "404", description = "Members not found"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            }
    )
    @GetMapping("/api/v1/members")
    ResponseEntity<Pagination<QueryMemberResponse>> getMembers(@ModelAttribute @Validated final QueryMembersRequest request);

    @Operation(
            summary = "Modify a member's email",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Member email modified successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid email format"),
                    @ApiResponse(responseCode = "404", description = "Member not found"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            }
    )
    @PatchMapping("/api/v1/members:modifyEmail")
    ResponseEntity<Void> modifyMemberEmail(@RequestBody @Validated final ModifyMemberEmailRequest request);

    @Operation(
            summary = "Delete a member by ID",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Member deleted successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid member ID"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            }
    )
    @DeleteMapping("/api/v1/members/{id}")
    ResponseEntity<Void> removeMemberById(@Parameter(description = "member ID", required = true) @PathVariable @Positive final Long id);
}
