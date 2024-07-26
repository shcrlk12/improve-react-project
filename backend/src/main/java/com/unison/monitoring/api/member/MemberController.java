package com.unison.monitoring.api.member;

import com.unison.monitoring.api.jsonapiorg.JsonApi;
import com.unison.monitoring.api.jsonapiorg.JsonApiOrgHttpHeaders;
import com.unison.monitoring.api.member.model.Member;
import com.unison.monitoring.api.member.model.MemberDto;
import com.unison.monitoring.api.member.service.MemberService;
import com.unison.monitoring.api.jsonapiorg.request.ApiRequest;
import com.unison.monitoring.api.jsonapiorg.response.ApiResponse;
import com.unison.monitoring.api.jsonapiorg.Links;
import com.unison.monitoring.api.jsonapiorg.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<MemberDto.Response>> readMember(@PathVariable("id") String id, HttpServletRequest httpServletRequest){

        Member member = memberService.getMemberById(id);

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

        return ResponseEntity.status(HttpStatus.OK)
                .headers(headers)
                .body(apiResponse);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<MemberDto.Response>> createMember(@RequestBody ApiRequest<MemberDto.Request> request, HttpServletRequest httpServletRequest){

        Member member = MemberMapper.memberDtoToMember(request.getData().getAttributes());

        memberService.createMember(member);


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

        Member member = MemberMapper.memberDtoToMember(request.getData().getAttributes());

        memberService.updateMember(member);


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

        memberService.deleteMemberById(id);


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
