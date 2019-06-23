package com.returnp.pointback.service.interfaces;

import java.util.HashMap;

import javax.servlet.http.HttpSession;

import org.springframework.transaction.annotation.Transactional;

import com.returnp.pointback.dto.command.InnerPointBackTarget;
import com.returnp.pointback.dto.command.OuterPointBackTarget;
import com.returnp.pointback.dto.command.PointBackTarget;
import com.returnp.pointback.dto.command.api.ApiRequest;
import com.returnp.pointback.dto.response.ReturnpBaseResponse;
import com.returnp.pointback.model.GreenPoint;

@Transactional
public interface PointbackTargetService {

	public PointBackTarget findInnerPointBackFindTarget(PointBackTarget target);
	public OuterPointBackTarget findOuterPointBackTarget(OuterPointBackTarget target);
	public InnerPointBackTarget findInnerPointBackTarget(String affiliateCode);
}
