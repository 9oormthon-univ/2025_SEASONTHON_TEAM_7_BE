package goormthonuniv.team_7_be.common.auth.service;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import goormthonuniv.team_7_be.api.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.core.user.OAuth2User;

@RequiredArgsConstructor
public class CustomUserDetails implements OAuth2User {

    private final Member member;

    @Override
    public Map<String, Object> getAttributes() {
        return Map.of();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(member.getRole().getKey()));
    }


    @Override
    public String getName() {
        return "";
    }
}