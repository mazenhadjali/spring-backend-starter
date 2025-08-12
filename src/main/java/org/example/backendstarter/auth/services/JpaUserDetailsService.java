package org.example.backendstarter.auth.services;

import lombok.RequiredArgsConstructor;
import org.example.backendstarter.ums.dao.AUserDao;
import org.example.backendstarter.ums.entity.AUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class JpaUserDetailsService implements UserDetailsService {

    private final AUserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AUser u = userDao.findByUsername(username);
        if (u == null) {
            throw new UsernameNotFoundException(username);
        }
        Set<GrantedAuthority> auths = new HashSet<>();
        u.getRoles().forEach(r -> {
            auths.add(new SimpleGrantedAuthority("ROLE_" + r.getName().toUpperCase().trim().replace(" ", "_")));
            r.getFeatures().forEach(f ->
                    auths.add(new SimpleGrantedAuthority("FEAT_" + f)));
        });
        return new org.springframework.security.core.userdetails.User(
                u.getUsername(), u.getPassword(), true, true, true, true, auths);
    }


}
