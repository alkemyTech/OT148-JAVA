package com.alkemy.ong.dto;

import com.alkemy.ong.domain.Member;
import com.alkemy.ong.mapper.MemberMapper;
import com.alkemy.ong.service.MemberService;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MemberListDTO {
    private List<MemberDTO> members;
    private String nextPageUrl;
    private String previousPageUrl;

    @Autowired
    MemberService memberService;

    public MemberListDTO(Integer page, Page<Member> members, String currentContextPath) {
        if (members.hasPrevious()) {
            this.previousPageUrl = currentContextPath.concat(String.format("/members?page=%d", page - 1));
        }
        if (members.hasNext()) {
            this.nextPageUrl = currentContextPath.concat(String.format("/members?page=%d", page + 1));
        }
        this.members = members.getContent().stream().map(MemberMapper::mapDomainToDTO).collect(Collectors.toList());
    }
}
