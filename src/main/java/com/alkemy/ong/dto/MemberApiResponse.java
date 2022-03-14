package com.alkemy.ong.dto;

import lombok.Data;

import java.util.List;

@Data
public class MemberApiResponse {
    private List<MemberDTO> members;
    private String nextPageUrl;
    private String previousPageUrl;

}
