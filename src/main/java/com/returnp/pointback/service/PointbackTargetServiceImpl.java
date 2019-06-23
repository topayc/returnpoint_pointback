package com.returnp.pointback.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.returnp.pointback.dao.mapper.PointBackMapper;
import com.returnp.pointback.dto.command.InnerPointBackTarget;
import com.returnp.pointback.dto.command.OuterPointBackTarget;
import com.returnp.pointback.dto.command.PointBackTarget;
import com.returnp.pointback.service.interfaces.PointbackTargetService;

@Service
public class PointbackTargetServiceImpl implements PointbackTargetService {

	@Autowired PointBackMapper pointBackMapper;;
	
	@Override
	public PointBackTarget findInnerPointBackFindTarget(PointBackTarget target) {
		return this.pointBackMapper.findInnerPointBackFindTarget(target);
	}
	
	@Override
	public OuterPointBackTarget findOuterPointBackTarget(OuterPointBackTarget target) {
		return this.pointBackMapper.findOuterPointBackTarget(target);
	}
	
	@Override
	public InnerPointBackTarget findInnerPointBackTarget(String affiliateSerial) {
		return this.pointBackMapper.findInnerPointBackTarget(affiliateSerial);
	}
}
