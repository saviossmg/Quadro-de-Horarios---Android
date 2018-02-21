package com.unitins.quadro.quadrodehorarios.models.modelos;

import com.unitins.quadro.quadrodehorarios.models.Semestre;

import java.util.List;

/**
 * Created by savio on 09/02/2018.
 */

public class ModeloSemestre {

    private boolean status;
    private String msg;
    private List<Semestre> data;

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

    public List<Semestre> getData() {
        return data;
    }

    public void setData(List<Semestre> data) {
        this.data = data;
    }

}
