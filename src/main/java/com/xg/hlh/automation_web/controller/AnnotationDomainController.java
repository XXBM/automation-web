package com.xg.hlh.automation_web.controller;

import com.xg.hlh.automation_web.entity.AnnotationDomain;
import com.xg.hlh.automation_web.service.AnnotationDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class AnnotationDomainController {
    @Autowired
    AnnotationDomainService annotationDomainService;


    /*find all*/
    @RequestMapping(value = "/findAllAnnotationDomainNames", method = RequestMethod.GET)
    public List<String> findAllAnnotationDomainNames() {
        List<AnnotationDomain> annotationDomains = annotationDomainService.findAllT();
        List<String> stringList = new ArrayList<>();
        for(int i=0;i<annotationDomains.size();i++){
            stringList.add(annotationDomains.get(i).getSimpleAnnotation());
        }
        return stringList;
    }


    /*find all*/
    @RequestMapping(value = "/findAllAnnotationDomains", method = RequestMethod.GET)
    public List<AnnotationDomain> findAllAnnotationDomains() {
        List<AnnotationDomain> annotationDomains = annotationDomainService.findAllT();
        return annotationDomains;
    }
    /*find all class annotation*/
    @RequestMapping(value = "/findAllClassAnnotation", method = RequestMethod.GET)
    public List<AnnotationDomain> findAllClassAnnotation() {
        List<AnnotationDomain> annotationDomains = annotationDomainService.findByType(0);
        return annotationDomains;
    }

    /*find all variable annotation*/
    @RequestMapping(value = "/findAllVariableAnnotation", method = RequestMethod.GET)
    public List<AnnotationDomain> findAllVariableAnnotation() {
        List<AnnotationDomain> annotationDomains = annotationDomainService.findByType(1);
        return annotationDomains;
    }


    /*find by pageble*/
    @RequestMapping(value = "/displayAllAnnotationDomains", method = RequestMethod.GET)
    public Map<String, Object> displayAllAnnotationDomains(@RequestParam(value = "page") Integer page,
                                                     @RequestParam(value = "rows") Integer size)throws Exception {
        Page<AnnotationDomain> list = annotationDomainService.findAllPagebleT(new PageRequest(page - 1, size));
        Map<String, Object> map = new HashMap<String, Object>();
        int total = annotationDomainService.findAllT().size();
        map.put("total", total);
        map.put("rows", list.getContent());
        return map;
    }

    /*add*/
    @RequestMapping(value = "/addAnnotationDomain", method = RequestMethod.POST)
    public Map<String, Object> addAnnotationDomain(@RequestBody AnnotationDomain annotationDomain) throws Exception{
        annotationDomainService.add(annotationDomain);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("AnnotationDomain", annotationDomain);
        return map;
    }

    /*update*/
    @RequestMapping(value = "/updateAnnotationDomain", method = RequestMethod.PUT)
    public Map<String, Object> updateAnnotationDomain(@RequestBody AnnotationDomain annotationDomain) throws Exception{
        annotationDomainService.update(annotationDomain);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("AnnotationDomain", annotationDomain);
        return map;
    }

    /*delete one*/
    @RequestMapping(value = "/deleteAnnotationDomain", method = RequestMethod.DELETE)
    public void deleteAnnotationDomain(@RequestParam("id") Integer id)throws Exception {
        annotationDomainService.deleteById(id);
    }

    /*delete many*/
    @RequestMapping(value = "/deleteAnnotationDomains", method = RequestMethod.DELETE)
    public void deleteAnnotationDomains(@RequestParam("ids") String ids)throws Exception {
        String deleteIds[] = ids.split(",");
        for (int i = 0; i < deleteIds.length; i++) {
            annotationDomainService.deleteById(Integer.parseInt(deleteIds[i]));
        }
    }
}
