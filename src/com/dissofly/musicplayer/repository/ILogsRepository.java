package com.dissofly.musicplayer.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.dissofly.musicplayer.entity.Information;
import com.dissofly.musicplayer.entity.Logs;

public interface ILogsRepository extends PagingAndSortingRepository<Logs, Integer>{

}
