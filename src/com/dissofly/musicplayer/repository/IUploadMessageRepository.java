package com.dissofly.musicplayer.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.dissofly.musicplayer.entity.PublicSong;
import com.dissofly.musicplayer.entity.UploadMessage;

public interface IUploadMessageRepository extends
PagingAndSortingRepository<UploadMessage, Integer>{

}
