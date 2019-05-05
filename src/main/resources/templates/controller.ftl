
import ${EntityPackageName}.${EntityName};
import ${EntityServicePackageName}.${EntityName}Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ${EntityName}Controller {
    @Autowired
    ${EntityName}Service ${EntityNameLower}Service;

    /*find all*/
    @RequestMapping(value = "/findAll${EntityName}s", method = RequestMethod.GET)
    public List<${EntityName}> findAll${EntityName}s() {
        List<${EntityName}> ${EntityNameLower}s = ${EntityNameLower}Service.findAllT();
        return ${EntityNameLower}s;
    }

    /*find by pageble*/
    @RequestMapping(value = "/displayAll${EntityName}s", method = RequestMethod.GET)
    public Map<String, Object> displayAll${EntityName}s(@RequestParam(value = "page") Integer page,
                                                        @RequestParam(value = "rows") Integer size)throws Exception {
        Page<${EntityName}> list = ${EntityNameLower}Service.findAllPagebleT(new PageRequest(page - 1, size));
        Map<String, Object> map = new HashMap<String, Object>();
        int total = ${EntityNameLower}Service.findAllT().size();
        map.put("total", total);
        map.put("rows", list.getContent());
        return map;
    }

    /*add*/
    @RequestMapping(value = "/add${EntityName}", method = RequestMethod.POST)
    public Map<String, Object> add${EntityName}(@RequestBody ${EntityName} ${EntityNameLower}) throws Exception{
        ${EntityNameLower}Service.add(${EntityNameLower});
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("${EntityName}", ${EntityNameLower});
        return map;
    }

    /*update*/
    @RequestMapping(value = "/update${EntityName}", method = RequestMethod.PUT)
    public Map<String, Object> update${EntityName}(@RequestBody ${EntityName} ${EntityNameLower}) throws Exception{
        ${EntityNameLower}Service.update(awardsRank);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("${EntityName}", ${EntityNameLower});
        return map;
    }

    /*delete one*/
    @RequestMapping(value = "/delete${EntityName}", method = RequestMethod.DELETE)
    public void delete${EntityName}(@RequestParam("id") Long id)throws Exception {
        ${EntityNameLower}Service.deleteById(id);
    }

    /*delete many*/
    @RequestMapping(value = "/delete${EntityName}s", method = RequestMethod.DELETE)
    public void delete${EntityName}s(@RequestParam("ids") String ids)throws Exception {
        String deleteIds[] = ids.split(",");
        for (int i = 0; i < deleteIds.length; i++) {
            ${EntityNameLower}Service.deleteById(Long.parseLong(deleteIds[i]));
        }
    }
}
