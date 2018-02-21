package com.unitins.quadro.quadrodehorarios.models.modelos;

import com.unitins.quadro.quadrodehorarios.models.Predio;

import java.util.List;

/**
 * Created by savio on 09/02/2018.
 */

public class ModeloPredio {

    private boolean status;
    private String msg;
    private List<Predio> data;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<Predio> getData() {
        return data;
    }

    public void setData(List<Predio> data) {
        this.data = data;
    }
}
