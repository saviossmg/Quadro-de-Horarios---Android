package com.unitins.quadro.quadrodehorarios.models.modelos;

import com.unitins.quadro.quadrodehorarios.models.Sala;

import java.util.List;

/**
 * Created by savio on 09/02/2018.
 */

public class ModeloSala {

    private boolean status;
    private String msg;
    private List<Sala> data;

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

    public List<Sala> getData() {
        return data;
    }

    public void setData(List<Sala> data) {
        this.data = data;
    }

}
