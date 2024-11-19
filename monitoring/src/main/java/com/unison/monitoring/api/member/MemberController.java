package com.unison.monitoring.api.member;

import com.unison.common.dto.MemberDto;
import com.unison.common.jsonapi.JsonApi;
import com.unison.common.jsonapi.JsonApiOrgHttpHeaders;
import com.unison.common.jsonapi.Links;
import com.unison.common.jsonapi.Resource;
import com.unison.common.jsonapi.request.ApiRequest;
import com.unison.common.jsonapi.response.ApiResponse;
import com.unison.monitoring.api.domain.Member;
import com.unison.monitoring.api.mapper.MemberMapper;
import com.unison.monitoring.api.member.service.MemberServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class MemberController {
    private final MemberServiceImpl memberServiceImpl;

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<MemberDto.Response>> readMember(@PathVariable("id") String id, HttpServletRequest httpServletRequest){

        Member member = memberServiceImpl.getMemberById(id);

        //make response
        MemberDto.Response response = MemberMapper.memberToMemberDto(member);

        Resource<MemberDto.Response> resource = Resource.<MemberDto.Response>builder()
                .type(MemberDto.TYPE)
                .id(member.getId())
                .attributes(response)
                .build();

        ApiResponse<MemberDto.Response> apiResponse = ApiResponse.<MemberDto.Response>builder()
                .data(resource)
                .links(Links.create(httpServletRequest, id))
                .jsonapi(new JsonApi())
                .build();

        return ResponseEntity.status(HttpStatus.OK)
                .headers(JsonApiOrgHttpHeaders.create(httpServletRequest, id))
                .body(apiResponse);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<MemberDto.Response>> createMember(@RequestBody ApiRequest<MemberDto.Request> request, HttpServletRequest httpServletRequest){

        Member member = MemberMapper.memberDtoToMember(request);

        memberServiceImpl.createMember(request);


        //make response
        MemberDto.Response response = MemberMapper.memberToMemberDto(member);

        Resource<MemberDto.Response> resource = Resource.<MemberDto.Response>builder()
                .type("users")
                .id(member.getId())
                .attributes(response)
                .build();

        String requestURL = httpServletRequest.getRequestURL().toString();
        
        //링크 설정
        Links links = new Links(String.format("%s/%s", requestURL, member.getId()));
        
        // 헤더 설정
        JsonApiOrgHttpHeaders headers = new JsonApiOrgHttpHeaders(String.format("%s/%s", requestURL, member.getId()));

        ApiResponse<MemberDto.Response> apiResponse = ApiResponse.<MemberDto.Response>builder()
                                                                        .data(resource)
                                                                        .links(links)
                                                                        .jsonapi(new JsonApi())
                                                                        .build();

        return ResponseEntity.status(HttpStatus.CREATED)
                .headers(headers)
                .body(apiResponse);
    }

    @PatchMapping
    public ResponseEntity<ApiResponse<MemberDto.Response>> updateMember(@RequestBody ApiRequest<MemberDto.Request> request, HttpServletRequest httpServletRequest){

        Member member = MemberMapper.memberDtoToMember(request);

        memberServiceImpl.updateMember(member);


        //make response
        MemberDto.Response response = MemberMapper.memberToMemberDto(member);

        Resource<MemberDto.Response> resource = Resource.<MemberDto.Response>builder()
                .type("users")
                .id(member.getId())
                .attributes(response)
                .build();

        String requestURL = httpServletRequest.getRequestURL().toString();

        //링크 설정
        Links links = new Links(String.format("%s/%s", requestURL, member.getId()));

        // 헤더 설정
        JsonApiOrgHttpHeaders headers = new JsonApiOrgHttpHeaders();

        ApiResponse<MemberDto.Response> apiResponse = ApiResponse.<MemberDto.Response>builder()
                .data(resource)
                .links(links)
                .jsonapi(new JsonApi())
                .build();

        return ResponseEntity.status(HttpStatus.OK)
                .headers(headers)
                .body(apiResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<MemberDto.Response>> deleteMember(@PathVariable("id") String id){

        memberServiceImpl.deleteMemberById(id);


        // 헤더 설정
        JsonApiOrgHttpHeaders headers = new JsonApiOrgHttpHeaders();

        ApiResponse<MemberDto.Response> apiResponse = ApiResponse.<MemberDto.Response>builder()
                .jsonapi(new JsonApi())
                .build();

        return ResponseEntity.status(HttpStatus.OK)
                .headers(headers)
                .body(apiResponse);
    }
}
