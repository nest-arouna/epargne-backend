package com.nest.epargne.rest;

import com.nest.epargne.dto.request.IntouchCallBackDtoRequest;
import com.nest.epargne.services.IntouchService;
import com.nest.epargne.utils.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class IntouchRestControler {
	@Autowired
	private IntouchService intouch;

	@PostMapping(Utility.INTOUCH)
	public void getAddCredit(@RequestBody IntouchCallBackDtoRequest intouchCallBackDtoRequest){
		 intouch.intouchCallBack(intouchCallBackDtoRequest);
    }


	

}
