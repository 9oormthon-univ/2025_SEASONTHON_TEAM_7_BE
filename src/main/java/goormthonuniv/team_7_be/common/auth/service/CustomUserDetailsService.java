package goormthonuniv.team_7_be.common.auth.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import goormthonuniv.team_7_be.api.repository.MemberRepository;
import goormthonuniv.team_7_be.common.auth.exception.AuthExceptionType;
import goormthonuniv.team_7_be.common.auth.service.dto.CustomUserDetails;
import goormthonuniv.team_7_be.common.exception.BaseException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = memberRepository.findByUsername(username)
                .orElseThrow(() -> new BaseException(AuthExceptionType.UNAUTHORIZED));

        return new CustomUserDetails(user);
    }
}
