package com.dissofly.musicplayer.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dissofly.musicplayer.entity.ClickLike;
import com.dissofly.musicplayer.repository.IClickLikeRepository;

@Component
@Service
@Transactional
public class DefaultClickLikeService implements IClickLikeService {

	@Autowired
	IClickLikeRepository likeRepo;
	@Autowired
	ILogsService logsService;

	@Override
	public ClickLike save(ClickLike clickLike) {
		clickLike=likeRepo.save(clickLike);
		return clickLike;
	}

	@Override
	public void delectById(Integer id) {
		likeRepo.delete(id);

	}

	@Override
	public Page<ClickLike> findByUserId(Integer userId, Integer page) {
		Sort sort = new Sort(Direction.DESC, "createDate");
		PageRequest pageReqeust = new PageRequest(page, 10, sort);
		return likeRepo.findByUserId(userId, pageReqeust);
	}

	@Override
	public ClickLike findById(Integer id) {
		return likeRepo.findOne(id);
	}

	@Override
	public ClickLike findCommentByUserById(Integer userId, Integer commentId) {
		// TODO Auto-generated method stub
		return likeRepo.findCommentByUserById(userId, commentId);
	}

	@Override
	public ClickLike findMusicByUserById(Integer userId, Integer musicId) {
		// TODO Auto-generated method stub
		return likeRepo.findMusicByUserById(userId,musicId);
	}

	@Override
	public List<ClickLike> findCommentById(Integer commentId) {
		// TODO Auto-generated method stub
		return likeRepo.findCommentById(commentId);
	}

	@Override
	public List<ClickLike> findMusicById(Integer musicId) {
		// TODO Auto-generated method stub
		return likeRepo.findMusicById(musicId);
	}

	@Override
	public List<ClickLike> findByUserId(Integer userId) {
		// TODO Auto-generated method stub
		return likeRepo.findByUserId(userId);
	}

	@Override
	public List<ClickLike> findMusicLikeByUserId(Integer userId) {
		// TODO Auto-generated method stub
		return likeRepo.findMusicLikeByUserId(userId);
	}

}
