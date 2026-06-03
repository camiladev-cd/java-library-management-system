package com.librarymanagement.mapper;

import com.librarymanagement.dto.MemberDTO;
import com.librarymanagement.model.Member;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface MemberMapper {

    MemberDTO.Response toResponse(Member member);

    Member toEntity(MemberDTO.Request request);

    void updateEntity(MemberDTO.Request request, @MappingTarget Member member);
}