package com.xg.hlh.automation_web.test.controller;
import com.xg.hlh.automation_web.test.domain.AwardsRank;
import com.xg.hlh.automation_web.test.service.AwardsRankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class AwardsRankController {
    @Autowired
    AwardsRankService awardsRankService;

    /*find all*/
    @RequestMapping(value = "/findAllAwardsRanks", method = RequestMethod.GET)
    public List<AwardsRank> findAllAwardsRanks() {
        List<AwardsRank> awardsRanks = awardsRankService.findAllT();
        return awardsRanks;
    }

    /*find by pageble*/
    @RequestMapping(value = "/displayAllAwardsRanks", method = RequestMethod.GET)
    public Map<String, Object> displayAllAwardsRanks(@RequestParam(value = "page") Integer page,
                                                        @RequestParam(value = "rows") Integer size)throws Exception {
        Page<AwardsRank> list = awardsRankService.findAllPagebleT(new PageRequest(page - 1, size));
        Map<String, Object> map = new HashMap<String, Object>();
        int total = awardsRankService.findAllT().size();
        map.put("total", total);
        map.put("rows", list.getContent());
        return map;
    }

    /*add*/
    @RequestMapping(value = "/addAwardsRank", method = RequestMethod.POST)
    public Map<String, Object> addAwardsRank(@RequestBody AwardsRank awardsRank) throws Exception{
        awardsRankService.add(awardsRank);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("AwardsRank", awardsRank);
        return map;
    }

    /*update*/
    @RequestMapping(value = "/updateAwardsRank", method = RequestMethod.PUT)
    public Map<String, Object> updateAwardsRank(@RequestBody AwardsRank awardsRank) throws Exception{
        awardsRankService.update(awardsRank);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("AwardsRank", awardsRank);
        return map;
    }

    /*delete one*/
    @RequestMapping(value = "/deleteAwardsRank", method = RequestMethod.DELETE)
    public void deleteAwardsRank(@RequestParam("id") Long id)throws Exception {
        awardsRankService.deleteById(id);
    }

    /*delete many*/
    @RequestMapping(value = "/deleteAwardsRanks", method = RequestMethod.DELETE)
    public void deleteAwardsRanks(@RequestParam("ids") String ids)throws Exception {
        String deleteIds[] = ids.split(",");
        for (int i = 0; i < deleteIds.length; i++) {
            awardsRankService.deleteById(Long.parseLong(deleteIds[i]));
        }
    }
}
