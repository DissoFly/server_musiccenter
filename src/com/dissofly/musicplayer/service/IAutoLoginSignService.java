package com.dissofly.musicplayer.service;

import com.dissofly.musicplayer.entity.AutoLoginSign;

public interface IAutoLoginSignService {

	AutoLoginSign findByUserId(int userId);
	void save(AutoLoginSign autoLoginSign);
}
