package com.rento.service.rentoservice;

import com.rento.service.rentoservice.repository.RoleRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RentoserviceApplicationTests {

	@Autowired
	private RoleRepository repository;

	@Test
	void contextLoads() {
		this.repository.findAll();
	}
}
