package com.librarymanagement.mapper;

import com.librarymanagement.dto.MemberDTO;
import com.librarymanagement.model.Member;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-06-03T09:03:46-0500",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.11 (Oracle Corporation)"
)
@Component
public class MemberMapperImpl implements MemberMapper {

    @Override
    public MemberDTO.Response toResponse(Member member) {
        if ( member == null ) {
            return null;
        }

        Long id = null;
        String name = null;
        String email = null;
        String phone = null;

        id = member.getId();
        name = member.getName();
        email = member.getEmail();
        phone = member.getPhone();

        MemberDTO.Response response = new MemberDTO.Response( id, name, email, phone );

        return response;
    }

    @Override
    public Member toEntity(MemberDTO.Request request) {
        if ( request == null ) {
            return null;
        }

        Member.MemberBuilder member = Member.builder();

        member.name( request.name() );
        member.email( request.email() );
        member.phone( request.phone() );

        return member.build();
    }

    @Override
    public void updateEntity(MemberDTO.Request request, Member member) {
        if ( request == null ) {
            return;
        }

        member.setName( request.name() );
        member.setEmail( request.email() );
        member.setPhone( request.phone() );
    }
}
