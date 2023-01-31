package com.dz.util;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BoardParam {
    private Object pageIndex;
    private long l_pageIndex;
    private long pageSize;

    private String searchType;
    private String search;
    private String type;

    /*
    limit 0, 10 --> pageIndex : 1
    limit 10, 10 --> pageIndex : 2
    limit 20, 30    --> pageIndex : 3
     */
    public long getPageStart(){
        init(); // 초기화
        return (l_pageIndex - 1) * pageSize;
    }

    public long getPageEnd(){
        init();
        return pageSize;
    }

    public void init(){
        if(pageIndex == null || pageIndex.toString().length() < 1){
            l_pageIndex = 1;
        }else{
            l_pageIndex = Long.parseLong((String) pageIndex);
        }

        if(search == null){
            search = "";
        }
        if(l_pageIndex < 1){
            l_pageIndex = 1;
        }
        if(pageSize < 10){
            pageSize = 9;
        }
    }

    public String getQueryString() {
        init();
        StringBuilder sb = new StringBuilder();

        if(search != null && search.length() > 0){
            if(sb.length() > 0){ // searchType이 있을 때 &로 묶어줌
                sb.append("&");
            }
            sb.append(String.format("search=%s", search));
        }
        if(type != null){
            if(sb.length() > 0){ // searchType이 있을 때 &로 묶어줌
                sb.append("&");
            }
            sb.append(String.format("type=%s", type));
        }

        return sb.toString();
    }
}
