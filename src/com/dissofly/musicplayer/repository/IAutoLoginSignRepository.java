package com.dissofly.musicplayer.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.dissofly.musicplayer.entity.AutoLoginSign;

public interface IAutoLoginSignRepository extends
		PagingAndSortingRepository<AutoLoginSign, Integer> {
	
	@Query("from AutoLoginSign where userId= ?1 ")
	AutoLoginSign findByUserId(int userId);

}
