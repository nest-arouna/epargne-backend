package com.nest.epargne.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import com.nest.epargne.entities.Utilisateur;
import com.nest.epargne.repositories.IDaoUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{
	@Autowired
    private IDaoUser daoUser;
	@Override
	public UserDetails loadUserByUsername(String phone) throws UsernameNotFoundException {
		Optional<Utilisateur> user = daoUser.findByPhone(phone);

		if(!user.isPresent()) throw new UsernameNotFoundException(phone);
		Collection<GrantedAuthority> authorities = new ArrayList<>();

		User userNew = new User(
				user.get().getPhone(),
				user.get().getPassword(),
				authorities
				);
		return userNew;
	}

}
