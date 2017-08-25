package com.dissofly.musicplayer.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.dissofly.musicplayer.entity.Comment;
import com.dissofly.musicplayer.entity.Information;

public interface IInformationRepository extends PagingAndSortingRepository<Information, Integer>{

}
