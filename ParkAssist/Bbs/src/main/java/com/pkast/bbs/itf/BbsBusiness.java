package com.pkast.bbs.itf;

import com.pkast.bbs.module.BbsItem;
import com.pkast.bbs.module.PublishBbsBase;
import com.pkast.bbs.module.PublishBbsRaw;

import java.util.List;

public interface BbsBusiness {
    List<List<BbsItem>> getBbs(int pageIdx, int pageSize, String xiaoquId, String bbsType);

    void addBbs(PublishBbsRaw bbsRaw);
}
