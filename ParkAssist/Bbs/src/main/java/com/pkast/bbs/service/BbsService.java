package com.pkast.bbs.service;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.pkast.bbs.itf.BbsBusiness;
import com.pkast.bbs.module.BbsItem;
import com.pkast.bbs.module.PublishBbsRaw;
import com.pkast.version.Version;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="/pkast/bbs/" + Version.V_0_0_1 + "/")
@JsonSerialize
public class BbsService {
    @Autowired
    private BbsBusiness bbsBusiness;

    @RequestMapping(method = RequestMethod.GET, path =  "get-bbs", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<List<BbsItem>> getBbsItems(@RequestParam("pageIdx") int pageIdx, @RequestParam("pageSize") int pageSize, @RequestParam("xiaoquId") String xiaoquId, @RequestParam("type") @Nullable String bbsType){
        return bbsBusiness.getBbs(pageIdx, pageSize, xiaoquId, bbsType);
    }

    @RequestMapping(method = RequestMethod.POST, path =  "add-bbs", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public boolean addBbs(@RequestParam("xiaoquId") String xiaoquId, @RequestBody PublishBbsRaw bbsRaw){
        bbsBusiness.addBbs(bbsRaw);
        return true;
    }
}
