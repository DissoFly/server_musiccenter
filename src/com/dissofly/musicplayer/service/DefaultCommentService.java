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
import com.dissofly.musicplayer.entity.Comment;
import com.dissofly.musicplayer.repository.ICommentRepository;
import com.dissofly.musicplayer.repository.IMusicListRepository;

@Component
@Service
@Transactional
public class DefaultCommentService implements IComnentService{

	@Autowired
	ICommentRepository commentRepo;

	@Override
	public Comment save(Comment comment) {
		// TODO Auto-generated method stub
		return commentRepo.save(comment);
	}

	@Override
	public Page<Comment> findBySongId(Integer songId, Integer page) {
		Sort sort = new Sort(Direction.DESC, "floor");
		PageRequest pageReqeust = new PageRequest(page, 10, sort);
		return commentRepo.findBySongId(songId, pageReqeust);
	}

	@Override
	public void delectById(Integer id) {
		commentRepo.delete(id);
		
	}

	@Override
	public Page<Comment> findByUserId(Integer userId, Integer page) {
		Sort sort = new Sort(Direction.DESC, "createDate");
		PageRequest pageReqeust = new PageRequest(page, 10, sort);
		return commentRepo.findByUserId(userId, pageReqeust);
	}

	@Override
	public List<Comment> getAllBySongId(Integer songId) {
		// TODO Auto-generated method stub
		return commentRepo.findAllBySongId(songId);
	}

	@Override
	public Comment findById(Integer id) {
		// TODO Auto-generated method stub
		return commentRepo.findOne(id);
	}
	


}
