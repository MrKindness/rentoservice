package com.rento.service.rentoservice;

import com.rento.service.rentoservice.repository.RoleRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
class RentoserviceApplicationTests {

	@Autowired
	private RoleRepository repository;
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Test
	void contextLoads() {
		this.passwordEncoder.encode("admin");
	}
}
