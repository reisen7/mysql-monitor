package com.fc.v2.controller.mysql;

import com.fc.v2.common.domain.AjaxResult;
import com.fc.v2.dto.PagedDto;
import com.fc.v2.model.mysql.InnodbLockWaits;
import com.fc.v2.model.mysql.InnodbTrx;
import com.fc.v2.service.monitor.InnodbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/innodb")
public class InnodbOldController {
	@Autowired
	private InnodbService innodbService;

	
	@RequestMapping("/lockwaits/{serverId}")
    @CrossOrigin(origins = "*")
    public PagedDto<InnodbLockWaits> getLockWaits(@PathVariable Long serverId) {
        return innodbService.getInnodbLockWaits(serverId);
    }
}
