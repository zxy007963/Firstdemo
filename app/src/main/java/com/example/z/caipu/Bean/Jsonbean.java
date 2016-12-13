package com.example.z.caipu.Bean;

import java.util.ArrayList;

import javax.xml.transform.Result;

/**
 * Created by Administrator on 2016/12/5 0005.
 */
public class Jsonbean {
    int resultcode;
    String reason;
    Result result;

    public int getResultcode() {
        return resultcode;
    }

    public void setResultcode(int resultcode) {
        this.resultcode = resultcode;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public static class Result {
        ArrayList<FoodData> data;

        public ArrayList<FoodData> getData() {
            return data;
        }

        public void setData(ArrayList<FoodData> data) {
            this.data = data;
        }
    }
}
