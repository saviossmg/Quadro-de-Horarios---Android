package com.unitins.quadro.quadrodehorarios.models.modelos;

import com.unitins.quadro.quadrodehorarios.models.SemestreLetivo;

import java.util.List;

/**
 * Created by savio on 10/02/2018.
 */

public class ModeloSemestreLetivo {

    private boolean status;
    private String msg;
    private List<SemestreLetivo> data;

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

    public List<SemestreLetivo> getData() {
        return data;
    }

    public void setData(List<SemestreLetivo> data) {
        this.data = data;
    }

}
