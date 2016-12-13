package com.example.z.caipu.Bean;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/12/8 0008.
 */
public class FenleiData {
    String resultcode;
    String reason;
    ArrayList<Result> result;

    public String getResultcode() {
        return resultcode;
    }

    public void setResultcode(String resultcode) {
        this.resultcode = resultcode;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public ArrayList<Result> getResult() {
        return result;
    }

    public void setResult(ArrayList<Result> result) {
        this.result = result;
    }

    public static class Result {
        int parentId;
        String name;
        ArrayList<List> list;
        public int getParentId() {
            return parentId;
        }

        public void setParentId(int parentId) {
            this.parentId = parentId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public ArrayList<List> getList() {
            return list;
        }

        public void setList(ArrayList<List> list) {
            this.list = list;
        }
    }
}
