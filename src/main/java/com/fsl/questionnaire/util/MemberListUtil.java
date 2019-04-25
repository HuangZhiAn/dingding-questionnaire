package com.fsl.questionnaire.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiDepartmentListRequest;
import com.dingtalk.api.request.OapiUserSimplelistRequest;
import com.dingtalk.api.response.OapiDepartmentListResponse;
import com.dingtalk.api.response.OapiUserSimplelistResponse;
import com.fsl.questionnaire.dto.User;
import com.taobao.api.ApiException;
import org.springframework.boot.json.GsonJsonParser;

public class MemberListUtil {
    //获取部门列表下所有用户
    public static List<User> getDepartmentUserIdWithName(List<Long> departmentIds) throws Exception {
        List<User> userList = new ArrayList<>();
        for (int i=0; i<departmentIds.size();i++){
            userList.addAll(getUserIdWithName(departmentIds.get(i)));
        }
        return userList;
    }

    //获取部门列表
    public static List<Long> getDepartment(String id) throws Exception {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/department/list");
        OapiDepartmentListRequest request = new OapiDepartmentListRequest();
        request.setId(id);
        request.setHttpMethod("GET");
        request.setFetchChild(true);
        OapiDepartmentListResponse response = null;
        try {
            response = client.execute(request, AccessTokenUtil.getToken());
        } catch (ApiException e) {
            e.printStackTrace();
        }
        System.out.println(response.getBody());
        GsonJsonParser gsonJsonParser = new GsonJsonParser();
        Map<String, Object> stringObjectMap = gsonJsonParser.parseMap(response.getBody());
        if("ok".equals(stringObjectMap.get("errmsg").toString())){
            List department = (List)stringObjectMap.get("department");
            List<Long> longList = new ArrayList<>();
            for (int i=0;i<department.size();i++){
                Map m = (Map) department.get(i);
                Double id1 = (Double)m.get("id");
                longList.add(id1.longValue());
            }
            return longList;
        }else{
            throw new Exception(response.getBody());
        }
    }

    //获取某部门下的所有用户的id和name
    public static List<User> getUserIdWithName(Long departmentId) throws Exception {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/user/simplelist");
        OapiUserSimplelistRequest request = new OapiUserSimplelistRequest();
        request.setDepartmentId(departmentId);
        request.setOffset(0L);
        request.setSize(100L);
        request.setHttpMethod("GET");
        OapiUserSimplelistResponse response = null;
        try {
            response = client.execute(request, AccessTokenUtil.getToken());
        } catch (ApiException e) {
            e.printStackTrace();
        }
        System.out.println(response.getBody());

        GsonJsonParser gsonJsonParser = new GsonJsonParser();
        Map<String, Object> stringObjectMap = gsonJsonParser.parseMap(response.getBody());
        if("ok".equals(stringObjectMap.get("errmsg").toString())){
            List department = (List)stringObjectMap.get("userlist");
            List<User> longList = new ArrayList<>();
            for (int i=0;i<department.size();i++){
                Map m = (Map) department.get(i);
                String userid = (String)m.get("userid");
                String name = (String)m.get("name");
                User user = new User();
                user.setUserid(userid);
                user.setName(name);
                longList.add(user);
            }
            return longList;
        }else{
            throw new Exception(response.getBody());
        }
    }
}
