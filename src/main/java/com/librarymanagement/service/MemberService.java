package com.librarymanagement.service;

import com.librarymanagement.dto.MemberDTO;
import com.librarymanagement.exception.DuplicateResourceException;
import com.librarymanagement.exception.ResourceNotFoundException;
import com.librarymanagement.mapper.MemberMapper;
import com.librarymanagement.model.Member;
import com.librarymanagement.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final MemberMapper     memberMapper;

    @Transactional
    public MemberDTO.Response create(MemberDTO.Request request) {
        if (memberRepository.existsByEmail(request.email())) {
            throw new DuplicateResourceException(
                    "Email already registered: " + request.email());
        }
        Member member = memberMapper.toEntity(request);
        return memberMapper.toResponse(memberRepository.save(member));
    }

    public List<MemberDTO.Response> findAll() {
        return memberRepository.findAll()
                .stream().map(memberMapper::toResponse).toList();
    }

    public MemberDTO.Response findById(Long id) {
        return memberRepository.findById(id)
                .map(memberMapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Member", id));
    }

    @Transactional
    public MemberDTO.Response update(Long id, MemberDTO.Request request) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Member", id));

        // Allow same email on update (only block if another member has it)
        memberRepository.findByEmail(request.email())
                .filter(existing -> !existing.getId().equals(id))
                .ifPresent(existing -> {
                    throw new DuplicateResourceException(
                            "Email already in use: " + request.email());
                });

        memberMapper.updateEntity(request, member);
        return memberMapper.toResponse(memberRepository.save(member));
    }

    @Transactional
    public void delete(Long id) {
        if (!memberRepository.existsById(id)) {
            throw new ResourceNotFoundException("Member", id);
        }
        memberRepository.deleteById(id);
    }
}