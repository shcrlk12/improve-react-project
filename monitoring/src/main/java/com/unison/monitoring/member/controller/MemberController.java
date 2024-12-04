package com.unison.monitoring.member.controller;

import com.unison.common.dto.MemberDto;
import com.unison.common.dto.UserDto;
import com.unison.common.jsonapi.JsonApi;
import com.unison.common.jsonapi.JsonApiOrgHttpHeaders;
import com.unison.common.jsonapi.Links;
import com.unison.common.jsonapi.Resource;
import com.unison.common.jsonapi.request.ApiRequest;
import com.unison.common.jsonapi.response.ApiResponse;
import com.unison.common.jsonapi.response.ApiResponses;
import com.unison.monitoring.common.security.UserDetailImpl;
import com.unison.monitoring.member.dto.Member;
import com.unison.monitoring.member.entity.MemberEntity;
import com.unison.monitoring.member.mapper.MemberMapper;
import com.unison.monitoring.member.service.MemberServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class MemberController {
    private final MemberServiceImpl memberServiceImpl;

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<MemberDto.Response>> readMember(@PathVariable("id") String id, HttpServletRequest httpServletRequest) throws Exception {

        //make response
        MemberDto.Response response = memberServiceImpl.getMemberById(id);

        Resource<MemberDto.Response> resource = Resource.<MemberDto.Response>builder()
                .type(MemberDto.TYPE)
                .id(response.getId())
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

    @GetMapping("/auth")
    public ResponseEntity<ApiResponse<UserDto.Response>> auth(HttpServletRequest httpServletRequest){
        MemberEntity memberEntity = ((UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getMember();

        JsonApiOrgHttpHeaders headers = new JsonApiOrgHttpHeaders();

        Resource<UserDto.Response> resource = Resource.<UserDto.Response>builder()
                .id(memberEntity.getId())
                .type(UserDto.TYPE)
                .attributes(new UserDto.Response(memberEntity.getRole(), memberEntity.getName(), "인증 성공"))
                .build();

        ApiResponse<UserDto.Response> apiResponse = ApiResponse.<UserDto.Response>builder()
                .data(resource)
                .links(Links.create(httpServletRequest))
                .jsonapi(new JsonApi())
                .build();

        return ResponseEntity.status(HttpStatus.OK)
                .headers(headers)
                .body(apiResponse);

    }

    @GetMapping()
    public ResponseEntity<ApiResponses<MemberDto.Response>> getMembers(HttpServletRequest httpServletRequest) throws Exception {

        //make response
        List<MemberDto.Response> responses = memberServiceImpl.getMembers();


        List<Resource<MemberDto.Response>> resources = responses.stream().map(resopnse ->
                                            Resource.<MemberDto.Response>builder()
                                                    .type(MemberDto.TYPE)
                                                    .id(resopnse.getId())
                                                    .attributes(resopnse)
                                                    .build()
                                            ).toList();

        ApiResponses<MemberDto.Response> apiResponse = ApiResponses.<MemberDto.Response>builder()
                .data(resources)
                .links(Links.create(httpServletRequest))
                .jsonapi(new JsonApi())
                .build();

        return ResponseEntity.ok()
                .headers(new JsonApiOrgHttpHeaders())
                .body(apiResponse);
    }
    @PostMapping
    public ResponseEntity<ApiResponse<MemberDto.Response>> createMember(@RequestBody ApiRequest<MemberDto.Request> request, HttpServletRequest httpServletRequest){

        Member member = MemberMapper.toMemberDto(request);

        MemberDto.Response response = MemberMapper.toResponseDto(member);

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

        try{
            memberServiceImpl.createMember(request);
        }catch(RuntimeException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .headers(headers)
                    .body(null);
        }

        //make response
        ApiResponse<MemberDto.Response> apiResponse = ApiResponse.<MemberDto.Response>builder()
                                                                        .data(resource)
                                                                        .links(links)
                                                                        .jsonapi(new JsonApi())
                                                                        .build();

        return ResponseEntity.status(HttpStatus.CREATED)
                .headers(headers)
                .body(apiResponse);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<MemberDto.Response>> updateMember(@PathVariable("id") String id, @RequestBody ApiRequest<MemberDto.Request> request, HttpServletRequest httpServletRequest) throws Exception {

        if(!id.equals(request.getData().getId())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .headers(new JsonApiOrgHttpHeaders())
                    .body(null);
        }

        Member member = MemberMapper.toMemberDto(request);

        memberServiceImpl.updateMember(request);

        //make response
        MemberDto.Response response = MemberMapper.toResponseDto(member);

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
    public ResponseEntity<ApiResponse<MemberDto.Response>> deleteMember(@PathVariable("id") String id) throws Exception {

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
